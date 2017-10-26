package com.mbds.barcode_battler_android.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mbds.barcode_battler_android.MainActivity;
import com.mbds.barcode_battler_android.Modele.Creature;
import com.mbds.barcode_battler_android.Modele.Joueur;
import com.mbds.barcode_battler_android.R;

/**
 * Created by Karl on 25/10/2017.
 */

public class CreatureScanFragment extends Fragment {

    private Creature creature;

    private TextView textNom;
    private TextView textRace;
    private ImageView imageRace;
    private LinearLayout layoutRarete;
    private TextView textPV;
    private TextView textPA;
    private TextView textPB;

    private Button btnAddCreature;
    private Button btnJeterCreature;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.affichage_creature, container, false);

        Bundle args = getArguments();

        creature = args.getParcelable("creature");
        Log.i("DEBUG", creature.toString());

        textNom = (TextView) view.findViewById(R.id.nom_creature);
        textNom.setText(creature.getNomEtTitre());

        textRace = (TextView) view.findViewById(R.id.race_creature);
        textRace.setText(creature.getRace());

        imageRace = (ImageView) view.findViewById(R.id.image_race);
        imageRace.setImageResource(MainActivity.getAppContext().getResources().getIdentifier(creature.getRace().toLowerCase().replaceAll("é", "e"), "drawable",
                MainActivity.getAppContext().getPackageName()));

        layoutRarete = (LinearLayout) view.findViewById(R.id.rarity);
        for (int i = 0; i < creature.getRarete(); i++) {

            View to_add = LayoutInflater.from(getContext()).inflate(R.layout.rarety_star,
                    layoutRarete, false);

            layoutRarete.addView(to_add);
        }
        textPV = (TextView) view.findViewById(R.id.pv_creature);
        textPV.setText(creature.getPV() + "");

        textPA = (TextView) view.findViewById(R.id.pa_creature);
        textPA.setText(creature.getPA() + "");

        textPB = (TextView) view.findViewById(R.id.pb_creature);
        textPB.setText(creature.getPB() + "");

        btnAddCreature = (Button) view.findViewById(R.id.garder_creature);
        btnAddCreature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Joueur.getInstance().addCreature(creature);
                Toast.makeText(getContext(), "'" + creature.getNomEtTitre() + "' ajouté à la collection !", Toast.LENGTH_SHORT).show();

            }
        });

        btnJeterCreature = (Button) view.findViewById(R.id.jeter_creature);
        btnJeterCreature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Adieu '" + creature.getNomEtTitre() + "' !", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }



}
