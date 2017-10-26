package com.mbds.barcode_battler_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mbds.barcode_battler_android.Modele.Equipement;

/**
 * Created by Karl on 26/10/2017.
 */

public class AffichageEquipementActivity extends AppCompatActivity {

    private TextView textNom;
    private LinearLayout layoutRarete;
    private TextView textPV;
    private TextView textPA;
    private TextView textPB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affichage_equipement);

        Equipement equipement = getIntent().getExtras().getParcelable("equipement");
        Log.i("DEBUG", equipement.toString());

        textNom = (TextView) findViewById(R.id.nom_equipement);
        textNom.setText(equipement.getNom());

        //imageRace = (ImageView) findViewById(R.id.image_equipement);
        //imageRace.setImageResource(MainActivity.getAppContext().getResources().getIdentifier(creature.getRace().toLowerCase(), "drawable",
        //        MainActivity.getAppContext().getPackageName()));

        layoutRarete = (LinearLayout) findViewById(R.id.rarity);
        for (int i = 0; i < equipement.getRarete(); i++) {

            View to_add = getLayoutInflater().inflate(R.layout.rarety_star,
                    layoutRarete, false);

            layoutRarete.addView(to_add);
        }

        textPV = (TextView) findViewById(R.id.pv_equipement);
        textPV.setText(equipement.getBonusPV() + "");

        textPA = (TextView) findViewById(R.id.pa_equipement);
        textPA.setText(equipement.getBonusPA() + "");

        textPB = (TextView) findViewById(R.id.pb_equipement);
        textPB.setText(equipement.getBonusPB() + "");

    }
}
