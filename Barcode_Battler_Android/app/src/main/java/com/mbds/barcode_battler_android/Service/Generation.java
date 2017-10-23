package com.mbds.barcode_battler_android.Service;

import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.mbds.barcode_battler_android.MainActivity;
import com.mbds.barcode_battler_android.R;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

/**
 * Created by Karl on 23/10/2017.
 */

public class Generation {

    private Context context;

    public Generation(Context context) {

        this.context = context;

        String valeur = "8002270014901";
    }

    public String generate(String value) {

        return genererNomTitreEtRace(HashService.hash(value));
    }

    private String genererNomTitreEtRace(String hash) {

        // Hash que l'on souhaite utiliser pour le nom
        String smallHash = hash.substring(0, 3);

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

        Log.i("Hash", smallHash + " : " + nom + " - " + titre + " - " + race);

        return nom + " - " + titre + " - " + race;
    }

    public String getStringResourcesFromArray(int idValue, int arrayId) {
        Resources res = context.getResources();
        String[] array = res.getStringArray(arrayId);

        if (idValue < array.length) {
            return array[idValue];
        }
        return "Not implemented yet !";
    }
}
