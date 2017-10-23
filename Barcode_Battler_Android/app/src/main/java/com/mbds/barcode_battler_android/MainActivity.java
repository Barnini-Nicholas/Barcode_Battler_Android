package com.mbds.barcode_battler_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mbds.barcode_battler_android.Controleur.GestionCombat;
import com.mbds.barcode_battler_android.Modele.Creature;
import com.mbds.barcode_battler_android.Service.Generation;
import com.mbds.barcode_battler_android.Service.HashService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Creature c1 = new Creature(10,5,5,"c1");
        Creature c2 = new Creature(10,5,5,"c2");
        new GestionCombat(c1, c2).commencerLaBagarre();
        //new Generation();

    }
}
