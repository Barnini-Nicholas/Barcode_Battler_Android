package com.mbds.barcode_battler_android;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mbds.barcode_battler_android.Controleur.GestionCombat;
import com.mbds.barcode_battler_android.Modele.Creature;
import com.mbds.barcode_battler_android.Service.HashService;
import com.mbds.barcode_battler_android.Service.TagLog;
import com.mbds.barcode_battler_android.Service.TraitementHash;
import com.mbds.barcode_battler_android.Service.TypeButin;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    Button btnScan;
    Button btnListCreature;

    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.context = getApplicationContext();


        Creature c1 = new Creature(10, 5, 5, "c1");
        Creature c2 = new Creature(10, 5, 5, "c2");

        new GestionCombat(c1, c2).commencerLaBagarre();


        btnScan = (Button) findViewById(R.id.btnScan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        btnListCreature = (Button) findViewById(R.id.btnListCreature);
        btnListCreature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListCreatureActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Récupération de la valeur du barcode scanné
        String barcode = data.getStringExtra("barcode");

        // On hash le barcode en SHA1
        String hash = HashService.hash(barcode);

        // Récupération du type de butin
        TypeButin typeButin = TraitementHash.getTypeOfHash(hash);

        String result = "";

        switch (typeButin) {

            case CREATURE:
                result = TraitementHash.getCreature(hash);
                break;

            case EQUIPEMENT:
                result = "Not implemented";
                break;
        }

        Snackbar mySnackbar = Snackbar.make(findViewById(android.R.id.content), result, LENGTH_LONG);
        mySnackbar.show();
    }

    public static Context getAppContext() {
        return MainActivity.context;
    }
}
