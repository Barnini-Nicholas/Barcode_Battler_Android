package com.mbds.barcode_battler_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.zxing.Result;
import com.mbds.barcode_battler_android.Modele.Creature;
import com.mbds.barcode_battler_android.Modele.Equipement;
import com.mbds.barcode_battler_android.Service.HashService;
import com.mbds.barcode_battler_android.Service.TagLog;
import com.mbds.barcode_battler_android.Service.TraitementHash;
import com.mbds.barcode_battler_android.Service.TypeButin;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.support.design.widget.Snackbar.LENGTH_LONG;


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

        // On hash le barcode en SHA1
        String hash = HashService.hash(barcode);

        // Récupération du type de butin
        TypeButin typeButin = TraitementHash.getTypeOfHash(hash);


        Intent intent = new Intent();
        intent.putExtra("typeButin", typeButin.toString());

        switch (typeButin) {

            case CREATURE:
                Creature creature = TraitementHash.getCreature(hash);
                intent.putExtra("butin", creature);
                break;

            case EQUIPEMENT:
                Equipement equipement = TraitementHash.getEquipement(hash);
                intent.putExtra("butin", equipement);
                break;
        }

        setResult(1, intent);
        finish();


    }


}
