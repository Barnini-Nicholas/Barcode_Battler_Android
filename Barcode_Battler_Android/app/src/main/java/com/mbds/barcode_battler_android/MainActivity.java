package com.mbds.barcode_battler_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mbds.barcode_battler_android.Service.HashService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HashService.hash("3068320055008");
        HashService.hash("8002270014901");
        HashService.hash("59060659");

        Log.i("Test", Long.parseLong("c0f52", 16)+"");
        Log.i("Test", Long.parseLong("7f9cf", 16)+"");
        Log.i("Test", Long.parseLong("215a1", 16)+"");
        Log.i("Test", Long.parseLong("b37ec", 16)+"");
        Log.i("Test", Long.parseLong("af0e0", 16)+"");
        Log.i("Test", Long.parseLong("90840", 16)+"");
        Log.i("Test", Long.parseLong("78c3d", 16)+"");
        Log.i("Test", Long.parseLong("834e3", 16)+"");
    }
}
