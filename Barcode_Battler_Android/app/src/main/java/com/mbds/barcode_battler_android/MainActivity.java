package com.mbds.barcode_battler_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mbds.barcode_battler_android.Service.Generation;
import com.mbds.barcode_battler_android.Service.HashService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Generation();
    }
}
