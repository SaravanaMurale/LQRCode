package com.pojo.lqrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView zXingScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        zXingScannerView = new ZXingScannerView(QRScannerActivity.this);

        setContentView(zXingScannerView);
    }

    @Override
    public void handleResult(Result result) {

        String scannedResult = result.getText();

        //QRDisplayActivity.displayQR.setText(result.getText());
        MainActivity.displayBlock.setVisibility(View.VISIBLE);

        MainActivity.btnScanQR.setVisibility(View.GONE);
        MainActivity.btnReScan.setVisibility(View.VISIBLE);
        MainActivity.btnSubmit.setVisibility(View.VISIBLE);

        //MainActivity.name.setVisibility(View.VISIBLE);
        //MainActivity.sports.setVisibility(View.VISIBLE);
        //QRDisplayActivity.btnSubmit.setVisibility(View.VISIBLE);

        System.out.println("RegularRest "+scannedResult);
        String[] words = scannedResult.split("//");

        System.out.println("OkDAta"+words[0]);
        MainActivity.userIdFromQrScan=words[0];


        onBackPressed();


    }

    @Override
    protected void onResume() {
        super.onResume();

        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();

        zXingScannerView.stopCamera();
    }

}