package com.mbds.barcode_battler_android.Controleur;

import android.util.Log;

import com.mbds.barcode_battler_android.Modele.Creature;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static android.R.attr.max;

/**
 * Created by PGNem on 23/10/2017.
 */

public class GestionCombat {

    Creature c1;
    Creature c2;

    public GestionCombat(Creature c1, Creature c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    public Creature commencerLaBagarre() {

        // Chiffre random entre 0 et 1 pour déterminer qui commence le combat
        Random rn = new Random();
        int randomNum = rn.nextInt(2);

        Log.v("COMBAT", "/////////////////////////");
        Log.v("COMBAT", "Début du combat !");


        Log.v("COMBAT", (randomNum == 0) ? "C1 commence" : "C2 commence");

        int i = 1;

        // Tant qu'aucun est mort on attaque
        while (c1.getPV() > 0 && c2.getPV() > 0) {

            Log.v("COMBAT", "////////// TOUR " + i);

            // Si on a 0 c'est c1 qui commence
            if (randomNum == 0) {

                Log.v("COMBAT", "C1 ATTAQUE");
                c1.attaque(c2);
                // Si c2 n'est pas mort il attaque
                if (c2.getPV() > 0) {
                    Log.v("COMBAT", "C2 ATTAQUE");
                    c2.attaque(c1);
                }

            } else {    // Si on a 1 c'est c2 qui commence

                Log.v("COMBAT", "C2 ATTAQUE");
                c2.attaque(c1);
                // Si c1 n'est pas mort il attaque
                if (c1.getPV() > 0) {
                    Log.v("COMBAT", "C1 ATTAQUE");
                    c1.attaque(c2);
                }

            }
            i++;
        }

        Log.v("COMBAT", "Le gagnant est : " + ((c1.getPV()<=0) ? c2.getNom() : c1.getNom()));

        return (c1.getPV()<=0) ? c2 : c1;
    }
}
