package com.mbds.barcode_battler_android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.Result;
import com.mbds.barcode_battler_android.Service.TagLog;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class ScannerFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    OnScanEffectue mCallback;

    // Container Activity must implement this interface
    public interface OnScanEffectue {
        void scanEffectue(String codeBarre);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mScannerView = new ZXingScannerView(getActivity());   // Programmatically initialize the scanner view
        // getActivity().setContentView(mScannerView);                // Set the scanner view as the content view

        mCallback = (OnScanEffectue) getActivity();
        return mScannerView;
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


        mCallback.scanEffectue(barcode);


    }

}
