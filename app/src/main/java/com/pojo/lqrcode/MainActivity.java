package com.pojo.lqrcode;

import static com.pojo.lqrcode.utils.AppConstant.BASE_URL;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.pojo.lqrcode.model.BaseResponse;
import com.pojo.lqrcode.model.UserDetailsResponse;
import com.pojo.lqrcode.model.UserEnterRequest;
import com.pojo.lqrcode.utils.AppConstant;
import com.pojo.lqrcode.utils.AsyncTaskImageDownload;
import com.pojo.lqrcode.utils.ProgressDlg;
import com.pojo.lqrcode.webservice.ApiClient;
import com.pojo.lqrcode.webservice.ApiInterface;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static  Button btnScanQR,btnReScan,btnSubmit;
    int CAMERA_ACCESS=55;

    public static  TextView name,sports,selectSports,address,inTime,outTime;
    ImageView circleImageView;

    public static String userIdFromQrScan=null;

    static LinearLayout displayBlock;
    EditText remarks;

    boolean[] selectedDay;
    ArrayList<Integer> dayList=new ArrayList<>();
    String[] dayArray={"Cricket","Chess","Carrom","Rummy","Snooker","Vollyball","Library"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnScanQR=findViewById(R.id.btnScanQR);
        btnReScan=findViewById(R.id.btbReScan);
        btnSubmit=findViewById(R.id.btnSubmit);
        remarks=findViewById(R.id.remarks);

        name=findViewById(R.id.name);
        //sports=findViewById(R.id.sports);
        circleImageView=findViewById(R.id.image);
        displayBlock=findViewById(R.id.displayBlock);
        selectSports=findViewById(R.id.selectSports);
        address=findViewById(R.id.addressUser);

        inTime=findViewById(R.id.inTime);
        outTime=findViewById(R.id.outTime);



        btnScanQR.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {


                if (!PermissionUtils.hasPermission(MainActivity.this, Manifest.permission.CAMERA)) {

                    PermissionUtils.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_ACCESS);

                } else {
                    callActivity();
                }


            }
        });

        btnReScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userIdFromQrScan=null;

                for (int i = 0; i <selectedDay.length ; i++) {
                    selectedDay[i]=false;
                    dayList.clear();

                    selectSports.setText("Select Sports");
                }

                remarks.setText("");

                callActivity();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String spinnerSelectedSports=selectSports.getText().toString();
                List<String> sportsList=null;

               // System.out.println("UserEnteredData "+spinnerSelectedSports);
                if(spinnerSelectedSports.equalsIgnoreCase("Select Sports")){
                    Toast.makeText(MainActivity.this,"Please Select Sports",Toast.LENGTH_SHORT).show();
                }else {
                    String[] sports=spinnerSelectedSports.split(",");

                   sportsList=new ArrayList<>();

                    for (int i = 0; i <sports.length ; i++) {
                        sportsList.add(sports[i]);
                    }
                }

               doSubmitUserDetails(sportsList);
            }
        });

        selectedDay=new boolean[dayArray.length];

        selectSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Select Sports");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(dayArray, selectedDay, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        if(isChecked){
                            dayList.add(which);
                            Collections.sort(dayList);
                        }else {
                            dayList.remove(which);
                        }

                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        StringBuilder stringBuffer=new StringBuilder();

                        for (int i = 0; i <dayList.size() ; i++) {

                            stringBuffer.append(dayArray[dayList.get(i)]);

                            if(i!=dayList.size()-1){
                                stringBuffer.append(",   ");
                            }
                            
                        }

                        selectSports.setText(stringBuffer.toString());

                    }

                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (int i = 0; i <selectedDay.length ; i++) {
                            selectedDay[i]=false;
                            dayList.clear();

                            selectSports.setText("Select Sports");
                        }
                        
                    }
                });


                builder.show();

            }
        });
    }

    private void doSubmitUserDetails(List<String> sportsList) {

        Dialog dialog= ProgressDlg.showProgressBar(MainActivity.this);
        ApiInterface apiService = ApiClient.getAPIClient().create(ApiInterface.class);

        UserEnterRequest userEnterRequest=new UserEnterRequest(userIdFromQrScan,sportsList,remarks.getText().toString(),inTime.getText().toString(),outTime.getText().toString());


        Call<BaseResponse> call = apiService.doSubmitUserEnteredDetails(userEnterRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                BaseResponse baseResponse=   response.body();

                if(baseResponse.getType().equalsIgnoreCase("success")){


                    userIdFromQrScan=null;
                    sportsList.clear();
                    remarks.setText("");
                    name.setText("");
                    inTime.setText("");
                    outTime.setText("");
                    address.setText("");


                    for (int i = 0; i <selectedDay.length ; i++) {
                        selectedDay[i]=false;
                        dayList.clear();

                        selectSports.setText("Select Sports");
                    }
                }

                ProgressDlg.dismisProgressBar(MainActivity.this,dialog);

                Toast.makeText(MainActivity.this,"Submitted Successfully",Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

                ProgressDlg.dismisProgressBar(MainActivity.this,dialog);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(userIdFromQrScan!=null){
            System.out.println("GetUserDetailsUsingUserId"+userIdFromQrScan);
            
            doGetUserDetails(userIdFromQrScan);

        }

    }

    private void doGetUserDetails(String userIdFromQrScan) {

        Dialog dialog= ProgressDlg.showProgressBar(MainActivity.this);
        ApiInterface apiService = ApiClient.getAPIClient().create(ApiInterface.class);

        Call<UserDetailsResponse> call = apiService.doGetUserDetails(userIdFromQrScan);
        call.enqueue(new Callback<UserDetailsResponse>() {
            @Override
            public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {

                ProgressDlg.dismisProgressBar(MainActivity.this,dialog);

                UserDetailsResponse userDetailsResponse=response.body();

                if(userDetailsResponse.getType().equalsIgnoreCase("success")){


                    displayUserDetails(userDetailsResponse,userIdFromQrScan);




                }else {
                    Toast.makeText(MainActivity.this,"Not a valid member",Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<UserDetailsResponse> call, Throwable t) {
                ProgressDlg.dismisProgressBar(MainActivity.this,dialog);
            }
        });

    }

    private void displayUserDetails(UserDetailsResponse userDetailsResponse, String userIdFromQrScan) {

        String imageEndPoint=userDetailsResponse.getResponseDataList().get(0).getUserImage()+userIdFromQrScan+".jpeg";
        AsyncTaskImageDownload asyncTask=new AsyncTaskImageDownload(circleImageView);
        asyncTask.execute(BASE_URL+imageEndPoint);

        displayBlock.setVisibility(View.VISIBLE);
        name.setText(userDetailsResponse.getResponseDataList().get(0).getUserName());
        address.setText(userDetailsResponse.getResponseDataList().get(0).getAddress());


        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date = new Date();

        inTime.setText(formatter.format(date));
        outTime.setText("21:00");




    }


    private void callActivity() {

        Intent intent = new Intent(MainActivity.this, QRScannerActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        userIdFromQrScan=null;
    }
}