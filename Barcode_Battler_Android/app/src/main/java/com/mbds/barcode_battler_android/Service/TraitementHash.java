package com.mbds.barcode_battler_android.Service;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.mbds.barcode_battler_android.MainActivity;
import com.mbds.barcode_battler_android.R;

/**
 * Created by Karl on 23/10/2017.
 */

public class TraitementHash {

    /** A 1 minimum pour éviter qu'un monstre ait 0 PV */
    private static final int BONUS_PV = 1;

    public static TypeButin getTypeOfHash(String value) {
        // 1ère valeur
        String firstVal = value.substring(0, 1);

        // Passage en int
        int intFirstVal = Integer.parseInt(firstVal, 16);

        if (intFirstVal % 2 == 0) {
            Log.i(TagLog.HASH, "Type du butin : " + TypeButin.CREATURE);
            return TypeButin.CREATURE;
        } else {
            Log.i(TagLog.HASH, "Type du butin : " + TypeButin.EQUIPEMENT);
            return TypeButin.EQUIPEMENT;
        }
    }

    public static String getCreature(String hash) {
        // *******************************
        // -- TRAITEMENT DU NOM/TITRE/RACE

        String smallHash = hash.substring(1, 4);

        // On découpe le hash
        String hashNom = smallHash.substring(0, 1);
        String hashTitre = smallHash.substring(1, 2);
        String hashRace = smallHash.substring(2, 3);

        // Passage en int
        int intNom = Integer.parseInt(hashNom, 16);
        int intTitre = Integer.parseInt(hashTitre, 16);
        int intRace = Integer.parseInt(hashRace, 16);

        // On récupère la String correspond au int
        String nom = getStringResourcesFromArray(intNom, R.array.nom_creature_array);
        String titre = getStringResourcesFromArray(intTitre, R.array.titre_creature_array);
        String race = getStringResourcesFromArray(intRace, R.array.race_creature_array);

        Log.i(TagLog.HASH, "Nom : " + nom + " (" + hashNom + ")");
        Log.i(TagLog.HASH, "Titre : " + titre + " (" + hashTitre + ")");
        Log.i(TagLog.HASH, "Race : " + race + " (" + hashRace + ")");

        // ****************************************
        // -- 2 : TRAITEMENT DES CARACTERISTIQUES :

        smallHash = hash.substring(4, 7);

        // On découpe le hash
        String hashPV = smallHash.substring(0, 1);
        String hashPA = smallHash.substring(1, 2);
        String hashPB = smallHash.substring(2, 3);

        // Passage en int
        int intPV = Integer.parseInt(hashPV, 16) + BONUS_PV;
        int intPA = Integer.parseInt(hashPA, 16);
        int intPB = Integer.parseInt(hashPB, 16);

        Log.i(TagLog.HASH, "PV : " + intPV + " (" + hashPV + ")");
        Log.i(TagLog.HASH, "PA : " + intPA + " (" + hashPA + ")");
        Log.i(TagLog.HASH, "PB : " + intPB + " (" + hashPB + ")");

        return nom + " - " + titre + " - " + race;
    }

    public static String getStringResourcesFromArray(int idValue, int arrayId) {
        Resources res = MainActivity.getAppContext().getResources();
        String[] array = res.getStringArray(arrayId);

        if (idValue < array.length) {
            return array[idValue];
        }
        return "Not implemented yet !";
    }
}
