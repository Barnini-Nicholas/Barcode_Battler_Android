package com.mbds.barcode_battler_android.fragment;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mbds.barcode_battler_android.MainActivity;
import com.mbds.barcode_battler_android.Modele.Creature;
import com.mbds.barcode_battler_android.R;
import com.mbds.barcode_battler_android.Service.TagLog;

/**
 * Created by Karl on 27/10/2017.
 */

public class CombatFragment extends Fragment {

    private static Creature creature1;
    private static Creature creature2;

    public static CHOIX_CREATURE choix_creature;

    public enum CHOIX_CREATURE {
        CREATURE_1, CREATURE_2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.affichage_combat, container, false);

        view.findViewById(R.id.image_creature_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TagLog.CHOOSE_CREATURE, "Choix créature 1");
                choix_creature = CHOIX_CREATURE.CREATURE_1;

                CreaturesFragment creaturesFragment = new CreaturesFragment();
                creaturesFragment.setChoixCreature(true);
                ((MainActivity) getActivity()).lancerFragment(creaturesFragment, false);

            }
        });

        view.findViewById(R.id.image_creature_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TagLog.CHOOSE_CREATURE, "Choix créature 2");

                choix_creature = CHOIX_CREATURE.CREATURE_2;

                CreaturesFragment creaturesFragment = new CreaturesFragment();
                creaturesFragment.setChoixCreature(true);
                ((MainActivity) getActivity()).lancerFragment(creaturesFragment, false);
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        creature1 = null;
        creature2 = null;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (creature1 != null) {
            ImageView imageRace = (ImageView) getView().findViewById(R.id.image_creature_1);
            imageRace.setImageResource(getContext().getResources().getIdentifier(creature1.getRace().toLowerCase().replaceAll("é", "e"), "drawable",
                    MainActivity.getAppContext().getPackageName()));

            TextView txNom = (TextView) getView().findViewById(R.id.nom_creature_1);
            txNom.setText(creature1.getNomEtTitre());

            TextView txPV = (TextView) getView().findViewById(R.id.pv_creature_1);
            txPV.setText("" + creature1.getPV());

        }

        if (creature2 != null) {
            ImageView imageRace = (ImageView) getView().findViewById(R.id.image_creature_2);
            imageRace.setImageResource(getContext().getResources().getIdentifier(creature2.getRace().toLowerCase().replaceAll("é", "e"), "drawable",
                    MainActivity.getAppContext().getPackageName()));

            TextView txNom = (TextView) getView().findViewById(R.id.nom_creature_2);
            txNom.setText(creature2.getNomEtTitre());

            TextView txPV = (TextView) getView().findViewById(R.id.pv_creature_2);
            txPV.setText("" + creature2.getPV());
        }
    }

    public Creature getCreature1() {
        return creature1;
    }

    public static void setCreature1(Creature creature1) {
        CombatFragment.creature1 = creature1;

        Log.i(TagLog.CHOOSE_CREATURE, creature1.toString());

    }

    public Creature getCreature2() {
        return creature2;
    }

    public static void setCreature2(Creature creature2) {
        CombatFragment.creature2 = creature2;
    }

}
