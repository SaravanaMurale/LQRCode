package com.pojo.lqrcode;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.pojo.lqrcode.utils.ProgressDlg;
import com.pojo.lqrcode.webservice.ApiClient;
import com.pojo.lqrcode.webservice.ApiInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static  Button btnScanQR,btnReScan,btnSubmit;
    int CAMERA_ACCESS=55;

    public static  TextView name,sports,selectSports;

    public static String userIdFromQrScan=null;

    static LinearLayout displayBlock;
    EditText remarks;

    boolean[] selectedDay;
    ArrayList<Integer> dayList=new ArrayList<>();
    String[] dayArray={"VolleyBall-100","Chess-200","FootBall-300","Cricket-400"};

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
        displayBlock=findViewById(R.id.displayBlock);
        selectSports=findViewById(R.id.selectSports);



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

                    selectSports.setText("Selecct Sports");
                }

                remarks.setText("");

                callActivity();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String nameStr=name.getText().toString();
                //String sportsStr=sports.getText().toString();
                //String remarksStr=remarks.getText().toString();
                String spinnerSelectedSports=selectSports.getText().toString();

                //Toast.makeText(MainActivity.this,"UserEnteredData "+nameStr+" "+sportsStr+" "+remarksStr+" "+spinnerSelectedSports,Toast.LENGTH_LONG).show();

                //System.out.println("UserEnteredData "+nameStr+" "+sportsStr+" "+remarksStr+" "+spinnerSelectedSports);

                System.out.println("UserEnteredData "+spinnerSelectedSports);
                if(spinnerSelectedSports.equalsIgnoreCase("Select Sports")){
                    Toast.makeText(MainActivity.this,"Please Select Sports",Toast.LENGTH_SHORT).show();
                }else {
                    String[] sports=spinnerSelectedSports.split(",");

                    List<String> sportsList=new ArrayList<>();

                    for (int i = 0; i <sports.length ; i++) {
                        sportsList.add(sports[i]);
                    }
                }

                //API call
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
                                stringBuffer.append(", ");
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

    @Override
    protected void onResume() {
        super.onResume();

        if(userIdFromQrScan!=null){
            System.out.println("GetUserDetailsUsingUserId");
            
            //doGetUserDetails(userIdFromQrScan);

        }

    }

    private void doGetUserDetails(String userIdFromQrScan) {

        Dialog dialog= ProgressDlg.showProgressBar(MainActivity.this);
        ApiInterface apiService = ApiClient.getAPIClient().create(ApiInterface.class);

        //Call<BaseRe> call = apiService.doGetUserDetails(userIdFromQrScan);

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