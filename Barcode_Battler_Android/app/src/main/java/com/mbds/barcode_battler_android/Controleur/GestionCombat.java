package com.mbds.barcode_battler_android.Controleur;

import com.mbds.barcode_battler_android.Modele.Creature;

/**
 * Created by PGNem on 23/10/2017.
 */

public class GestionCombat {

    Creature c1;
    Creature c2;

    public GestionCombat(Creature c1, Creature c2){
        this.c1 = c1;
        this.c2 = c2;
    }
    public Creature commencerLaBagarre(){

        while (c1.getPV() > 0 || c2.getPV() > 0 ){

        }

        return c1;
    }
}
