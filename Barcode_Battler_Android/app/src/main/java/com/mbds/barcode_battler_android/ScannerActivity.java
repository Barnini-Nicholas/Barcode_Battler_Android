package com.mbds.barcode_battler_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.zxing.Result;
import com.mbds.barcode_battler_android.Service.TagLog;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {

        String barcode = rawResult.getText().toString();
        String barcodeFormat = rawResult.getBarcodeFormat().toString();

        Log.i(TagLog.SCAN, "Barcode : " + barcode);
        Log.i(TagLog.SCAN, "BarcodeFormat : " + barcodeFormat);

        Intent intent = new Intent();
        intent.putExtra("barcode", barcode);
        setResult(1, intent);
        finish();


        // implements Parcelable
        // writeToParcel(Parcel d, int flag){
        //  d.writeStringArray([Nom, Prenom, ...]);
        //}

        //
        //
        //

    }


}
