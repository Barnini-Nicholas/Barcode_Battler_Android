package com.mbds.barcode_battler_android;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mbds.barcode_battler_android.Modele.Creature;
import com.mbds.barcode_battler_android.Modele.Equipement;
import com.mbds.barcode_battler_android.Modele.Joueur;
import com.mbds.barcode_battler_android.Service.TagLog;
import com.mbds.barcode_battler_android.Modele.TypeButin;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnScan;
    Button btnListCreature;
    Button btnCombat;

    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.context = getApplicationContext();


        BDD bd = BDD.getInstance();
        Creature c1 = new Creature("nom", "titre", "race", 10, 5, 5, "cb");
        Equipement e1 = new Equipement("aa", 10, 5 ,1, "");
        bd.addCreature(c1);
        bd.addEquipement(e1);
        ArrayList<Equipement> le = bd.getEquipement();
        ArrayList<Creature> lc = bd.getCreature();
        Log.v("TESSSSSSSSSSSSSSSST", "STP MARCHE le : "+ le );
        Log.v("TESSSSSSSSSSSSSSSST", "STP MARCHE lc : "+ lc );
        bd.addEquipementToCreature(lc.get(lc.size()-1), le.get(le.size()-1));
        Log.v("TESSSSSSSSSSSSSSSST", "STP MARCHE fin : " + bd.getEquipementsOfCreature(lc.get(lc.size() -1 ).getId()));

        // Creature c2 = new Creature(10, 5, 5, "c2");

        /*
        Equipement e1 = new Equipement("aa", 10, 5 ,1);
        Equipement e2 = new Equipement("bb", 9, 4 ,8);
        Equipement e3 = new Equipement("cc", 8, 3 ,9);
        Equipement e4 = new Equipement("dd", 7, 2 ,6);

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


        btnCombat = (Button) findViewById(R.id.btnCombat);
        btnCombat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CombatActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK) {
            return;
        }

        TypeButin typeButin = TypeButin.valueOf(data.getExtras().getString("typeButin"));
        ((TextView) findViewById(R.id.textInfo)).setText(data.getExtras().getParcelable("butin").toString());

        switch (typeButin) {
            case CREATURE:
                Creature creature = (Creature) data.getExtras().getParcelable("butin");
                Log.i(TagLog.HASH_CREATURE, creature.toString());

                // Controle pour voir si la créature a déja été scanné :
                for (Creature c : Joueur.getInstance().getListCreatures()) {
                    if (creature.getCodeBarreUtilise().equals(c.getCodeBarreUtilise())) {
                        Toast.makeText(getApplicationContext(), "Déja scanné tricheur", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                Intent intent = new Intent(MainActivity.this, AffichageCreatureActivity.class);
                intent.putExtra("creature", creature);
                startActivity(intent);

                break;

            case EQUIPEMENT:
                Equipement equipement = (Equipement) data.getExtras().getParcelable("butin");
                Log.i(TagLog.HASH_EQUIPEMENT, equipement.toString());

                Intent intent2 = new Intent(MainActivity.this, AffichageEquipementActivity.class);
                intent2.putExtra("equipement", equipement);
                startActivity(intent2);

                break;
        }
    }

    public static Context getAppContext() {
        return MainActivity.context;
    }
}
