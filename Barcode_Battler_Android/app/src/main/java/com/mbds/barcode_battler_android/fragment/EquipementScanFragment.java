package com.mbds.barcode_battler_android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mbds.barcode_battler_android.MainActivity;
import com.mbds.barcode_battler_android.Modele.Equipement;
import com.mbds.barcode_battler_android.Modele.Joueur;
import com.mbds.barcode_battler_android.R;

/**
 * Created by Karl on 26/10/2017.
 */

public class EquipementScanFragment extends Fragment {

    private Equipement equipement;

    private TextView textNom;
    private LinearLayout layoutRarete;
    private TextView textPV;
    private TextView textPA;
    private TextView textPB;

    private Button btnAddEquipement;
    private Button btnJeterEquipement;

    private boolean readOnly = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.affichage_scan_equipement, container, false);

        Log.i("DEBUG", equipement.toString());

        textNom = (TextView) view.findViewById(R.id.nom_equipement);
        textNom.setText(equipement.getNom());

        layoutRarete = (LinearLayout) view.findViewById(R.id.rarity);
        for (int i = 0; i < equipement.getRarete(); i++) {

            View to_add = LayoutInflater.from(getContext()).inflate(R.layout.rarety_star,
                    layoutRarete, false);

            layoutRarete.addView(to_add);
        }

        textPV = (TextView) view.findViewById(R.id.pv_equipement);
        textPV.setText(equipement.getBonusPV() + "");

        textPA = (TextView) view.findViewById(R.id.pa_equipement);
        textPA.setText(equipement.getBonusPA() + "");

        textPB = (TextView) view.findViewById(R.id.pb_equipement);
        textPB.setText(equipement.getBonusPB() + "");

        btnAddEquipement = (Button) view.findViewById(R.id.garder_equipement);
        btnJeterEquipement = (Button) view.findViewById(R.id.jeter_equipement);

        if (readOnly = false) {
            btnAddEquipement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Joueur.getInstance().addEquipement(equipement);
                    Toast.makeText(getContext(), "'" + equipement.getNom() + "' ajouté à la collection !", Toast.LENGTH_SHORT).show();
                    ((MainActivity) getActivity()).lancerFragment(EquipementsFragment.class, true);
                }
            });

            btnJeterEquipement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Adieu '" + equipement.getNom() + "' !", Toast.LENGTH_SHORT).show();
                    ((MainActivity) getActivity()).lancerFragment(EquipementsFragment.class, true);
                }
            });
        } else {
            btnAddEquipement.setVisibility(View.INVISIBLE);
            btnJeterEquipement.setVisibility(View.INVISIBLE);

            ((TextView) view.findViewById(R.id.textEquipement)).setText("Votre équipement : ");
        }

        return view;
    }

    public Equipement getEquipement() {
        return equipement;
    }

    public void setEquipement(Equipement equipement) {
        this.equipement = equipement;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }
}
