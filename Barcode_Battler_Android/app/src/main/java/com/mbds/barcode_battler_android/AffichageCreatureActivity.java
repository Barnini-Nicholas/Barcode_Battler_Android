package com.mbds.barcode_battler_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mbds.barcode_battler_android.Modele.Creature;
import com.mbds.barcode_battler_android.Service.TagLog;

/**
 * Created by Karl on 25/10/2017.
 */

public class AffichageCreatureActivity extends AppCompatActivity {

    private TextView textNom;
    private TextView textRace;
    private ImageView imageRace;
    private LinearLayout layoutRarete;
    private TextView textPV;
    private TextView textPA;
    private TextView textPB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affichage_creature);

        Creature creature = getIntent().getExtras().getParcelable("creature");
        Log.i("DEBUG", creature.toString());

        textNom = (TextView) findViewById(R.id.nom_creature);
        textNom.setText(creature.getNomEtTitre());

        textRace = (TextView) findViewById(R.id.race_creature);
        textRace.setText(creature.getRace());

        imageRace = (ImageView) findViewById(R.id.image_race);
        imageRace.setImageResource(MainActivity.getAppContext().getResources().getIdentifier(creature.getRace().toLowerCase(), "drawable",
                MainActivity.getAppContext().getPackageName()));

        layoutRarete = (LinearLayout) findViewById(R.id.rarity);
        for (int i = 0; i < creature.getRarete(); i++) {

            View to_add = getLayoutInflater().inflate(R.layout.rarety_star,
                    layoutRarete, false);

            layoutRarete.addView(to_add);
        }
        textPV = (TextView) findViewById(R.id.pv_creature);
        textPV.setText(creature.getPV() + "");

        textPA = (TextView) findViewById(R.id.pa_creature);
        textPA.setText(creature.getPA() + "");

        textPB = (TextView) findViewById(R.id.pb_creature);
        textPB.setText(creature.getPB() + "");

    }
}