package com.mbds.barcode_battler_android.Controleur;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mbds.barcode_battler_android.Modele.Creature;
import com.mbds.barcode_battler_android.Modele.Joueur;
import com.mbds.barcode_battler_android.R;
import com.mbds.barcode_battler_android.Service.TagLog;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by PGNem on 23/10/2017.
 */

public class GestionCombat {

    Creature c1;
    Creature c2;
    View view;

    public GestionCombat(Creature c1, Creature c2, View view) {
        this.c1 = c1;
        this.c2 = c2;
        this.view = view;
    }

    public Creature commencerLaBagarre() {

        // Chiffre random entre 0 et 1 pour déterminer qui commence le combat
        Random rn = new Random();
        int randomNum = rn.nextInt(2);

        Log.v(TagLog.COMBAT, "/////////////////////////");
        Log.v(TagLog.COMBAT, "Début du combat !");


        Log.v(TagLog.COMBAT, (randomNum == 0) ? "C1 commence" : "C2 commence");

        int i = 1;

        // Tant qu'aucun est mort on attaque
        while (c1.getPV() > 0 && c2.getPV() > 0) {

            Log.v(TagLog.COMBAT, "////////// TOUR " + i);

            // Si on a 0 c'est c1 qui commence
            if (randomNum == 0) {

                Log.v(TagLog.COMBAT, "C1 ATTAQUE");
                c1.attaque(c2);
                ((TextView) view.findViewById(R.id.pv_creature_2)).setText(c2.getPV()+"");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Si c2 n'est pas mort il attaque
                if (c2.getPV() > 0) {
                    Log.v(TagLog.COMBAT, "C2 ATTAQUE");
                    c2.attaque(c1);

                    ((TextView) view.findViewById(R.id.pv_creature_1)).setText(c1.getPV()+"");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            } else {    // Si on a 1 c'est c2 qui commence

                Log.v(TagLog.COMBAT, "C2 ATTAQUE");
                c2.attaque(c1);
                ((TextView) view.findViewById(R.id.pv_creature_1)).setText(c1.getPV()+"");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Si c1 n'est pas mort il attaque
                if (c1.getPV() > 0) {
                    Log.v(TagLog.COMBAT, "C1 ATTAQUE");
                    c1.attaque(c2);

                    ((TextView) view.findViewById(R.id.pv_creature_2)).setText(c2.getPV()+"");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
            i++;
        }

        Log.v(TagLog.COMBAT, "Le gagnant est : " + ((c1.getPV() <= 0) ? c2.getNom() : c1.getNom()));
        ((EditText) view.findViewById(R.id.logs_combat)).setText("Le gagnant est : " + ((c1.getPV() <= 0) ? c2.getNom() : c1.getNom()));

        Joueur.getInstance().resetListCreatures();

        return (c1.getPV() <= 0) ? c2 : c1;


    }
}