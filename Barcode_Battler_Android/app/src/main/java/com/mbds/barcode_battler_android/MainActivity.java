package com.mbds.barcode_battler_android;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mbds.barcode_battler_android.Modele.Creature;
import com.mbds.barcode_battler_android.Modele.Equipement;
import com.mbds.barcode_battler_android.Modele.Joueur;
import com.mbds.barcode_battler_android.Service.TagLog;
import com.mbds.barcode_battler_android.Service.TypeButin;

public class MainActivity extends AppCompatActivity {

    Button btnScan;
    Button btnListCreature;

    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.context = getApplicationContext();


        //Creature c1 = new Creature(10, 5, 5, "c1");
        // Creature c2 = new Creature(10, 5, 5, "c2");

        // new GestionCombat(c1, c2).commencerLaBagarre();


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

        TypeButin typeButin = TypeButin.valueOf(data.getExtras().getString("typeButin"));
        ((TextView) findViewById(R.id.textInfo)).setText(data.getExtras().getParcelable("butin").toString());

        switch (typeButin) {
            case CREATURE:
                Creature creature = (Creature) data.getExtras().getParcelable("butin");
                Log.i(TagLog.HASH_CREATURE, creature.toString());
                Joueur.getInstance().addCreature(creature);
                break;

            case EQUIPEMENT:
                Equipement equipement = (Equipement) data.getExtras().getParcelable("butin");
                Log.i(TagLog.HASH_EQUIPEMENT, equipement.toString());
                break;
        }
    }

    public static Context getAppContext() {
        return MainActivity.context;
    }
}
