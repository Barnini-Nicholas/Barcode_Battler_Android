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
import com.mbds.barcode_battler_android.Modele.TypeButin;

import java.util.ArrayList;

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


        // bdd.deleteAllTables();
        // bdd.createTablesAgain();
        /*
        Equipement e1 = new Equipement("aa", 10, 5 ,1);
        Equipement e2 = new Equipement("bb", 9, 4 ,8);
        Equipement e3 = new Equipement("cc", 8, 3 ,9);
        Equipement e4 = new Equipement("dd", 7, 2 ,6);
        bdd.addEquipement(e1);
        bdd.addEquipement(e2);
        bdd.addEquipement(e3);
        bdd.addEquipement(e4);
        Log.v("BDDDDDDDDDDDDDDDDD", bdd.getEquipement().toString() + " ALLO STP MARCHE");
        */


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

                Intent intent = new Intent(MainActivity.this, AffichageCreatureActivity.class);
                intent.putExtra("creature", creature);

                startActivity(intent);
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
