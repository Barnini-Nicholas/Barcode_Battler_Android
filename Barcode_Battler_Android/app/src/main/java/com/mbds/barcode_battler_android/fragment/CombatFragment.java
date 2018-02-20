package com.mbds.barcode_battler_android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.mbds.barcode_battler_android.MainActivity;
import com.mbds.barcode_battler_android.Modele.Creature;
import com.mbds.barcode_battler_android.Modele.Equipement;
import com.mbds.barcode_battler_android.Modele.Joueur;
import com.mbds.barcode_battler_android.R;
import com.mbds.barcode_battler_android.Service.CombatChange;
import com.mbds.barcode_battler_android.Service.TagLog;

import java.util.Random;

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


        view.findViewById(R.id.image_combat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (creature1 != null && creature2 != null) {

                    Creature cUNavecEquip = new Creature(creature1);
                    Creature cDEUXavecEquip = new Creature(creature2);

                    /*
                        ArrayList<Equipement> ae = new ArrayList<Equipement>( );
                        ae. add(new Equipement("TEST", 20, 20, 20 , "koko"));
                        cUNavecEquip.setListEquipement(ae);
                    */

                    for (Equipement e : cUNavecEquip.getListEquipement()) {
                        cUNavecEquip.setPV(cUNavecEquip.getPV() + e.getBonusPV());
                        cUNavecEquip.setPA(cUNavecEquip.getPA() + e.getBonusPA());
                        cUNavecEquip.setPB(cUNavecEquip.getPB() + e.getBonusPB());
                    }

                    for (Equipement e : cDEUXavecEquip.getListEquipement()) {
                        cDEUXavecEquip.setPV(cDEUXavecEquip.getPV() + e.getBonusPV());
                        cDEUXavecEquip.setPA(cDEUXavecEquip.getPA() + e.getBonusPA());
                        cDEUXavecEquip.setPB(cDEUXavecEquip.getPB() + e.getBonusPB());
                    }

                    setCreature1(cUNavecEquip);
                    setCreature2(cDEUXavecEquip);

                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            commencerLaBagarre();

                        }
                    });
                    t.start();
                }
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
            txNom.setText(creature1.getNom());

            TextSwitcher txPV = (TextSwitcher) getView().findViewById(R.id.pv_creature_1);
            txPV.setFactory(mFactory);
            txPV.setText("" + creature1.getPV());

        }

        if (creature2 != null) {
            ImageView imageRace = (ImageView) getView().findViewById(R.id.image_creature_2);
            imageRace.setImageResource(getContext().getResources().getIdentifier(creature2.getRace().toLowerCase().replaceAll("é", "e"), "drawable",
                    MainActivity.getAppContext().getPackageName()));

            TextView txNom = (TextView) getView().findViewById(R.id.nom_creature_2);
            txNom.setText(creature2.getNom());

            TextSwitcher txPV = (TextSwitcher) getView().findViewById(R.id.pv_creature_2);
            txPV.setFactory(mFactory);
            txPV.setText("" + creature2.getPV());
        }


    }

    private void commencerLaBagarre() {
        // Elément qui vont se mettre à jour durant le combat
        EditText editText = (EditText) getView().findViewById(R.id.logs_combat);
        TextSwitcher pvCreature1 = ((TextSwitcher) getView().findViewById(R.id.pv_creature_1));
        TextSwitcher pvCreature2 = ((TextSwitcher) getView().findViewById(R.id.pv_creature_2));

        // Service qui va gérer les MAJ UI
        CombatChange clt = new CombatChange(editText, pvCreature1, pvCreature2);

        // Chiffre random entre 0 et 1 pour déterminer qui commence le combat
        Random rn = new Random();
        int randomNum = rn.nextInt(2);

        // Annonce de la créature qui commence :
        String creatureCommence = "";
        if (randomNum == 0) {
            creatureCommence = creature1.getNom() + " commence !";
        } else {
            creatureCommence = creature2.getNom() + " commence !";
        }
        clt.addCombatMsg(creatureCommence);

        // Compteur de tour
        int tour = 1;

        String attaque = "";

        // Tant qu'aucun est mort on attaque
        while (creature1.getPV() > 0 && creature2.getPV() > 0) {

            clt.addCombatMsg("\n");

            // Si on a 0 c'est c1 qui commence
            if (randomNum == 0) {

                Log.i(TagLog.COMBAT, "C1 ATTAQUE");

                attaque = creature1.attaque(creature2);
                clt.addCombatMsg("\n[ " + tour + " ] - " + creature1.getNom() + " : " + attaque);

                // Maj PV
                clt.changePvCreature(2, creature2.getPV());

                // Si c2 n'est pas mort il attaque
                if (creature2.getPV() > 0) {

                    attaque = creature2.attaque(creature1);
                    clt.addCombatMsg("\n[ " + tour + " ] - " + creature2.getNom() + " : " + attaque);

                    // Maj PV
                    clt.changePvCreature(1, creature1.getPV());
                }

            } else {    // Si on a 1 c'est c2 qui commence

                attaque = creature2.attaque(creature1);
                clt.addCombatMsg("\n[ " + tour + " ] - " + creature2.getNom() + " : " + attaque);

                // Maj PV
                clt.changePvCreature(1, creature1.getPV());

                // Si c1 n'est pas mort il attaque
                if (creature1.getPV() > 0) {

                    attaque = creature1.attaque(creature2);
                    clt.addCombatMsg("\n[ " + tour + " ] - " + creature1.getNom() + " : " + attaque);

                    // Maj PV
                    clt.changePvCreature(2, creature2.getPV());
                }
            }
            tour++;
        }

        clt.addCombatMsg("\n\n  -->  Le gagnant est : " + ((creature1.getPV() <= 0) ? creature2.getNom() : creature1.getNom()) + "  <--");

        Joueur.getInstance().resetListCreatures();

        // return (creature1.getPV() <= 0) ? creature2 : creature1;
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
    /**
     * The {@link android.widget.ViewSwitcher.ViewFactory} used to create {@link android.widget.TextView}s that the
     * {@link android.widget.TextSwitcher} will switch between.
     */
    private ViewSwitcher.ViewFactory mFactory = new ViewSwitcher.ViewFactory() {

        @Override
        public View makeView() {

            // Create a new TextView
            TextView t = new TextView(MainActivity.activity);
            t.setGravity(Gravity.CENTER);
            t.setTextAppearance(MainActivity.activity, android.R.style.TextAppearance_Large);
            return t;
        }
    };
}
