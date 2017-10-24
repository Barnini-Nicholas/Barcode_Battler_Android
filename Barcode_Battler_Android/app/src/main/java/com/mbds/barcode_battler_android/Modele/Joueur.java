package com.mbds.barcode_battler_android.Modele;

import java.util.ArrayList;

/**
 * Created by Karl on 24/10/2017.
 */

public class Joueur {
    private static final Joueur ourInstance = new Joueur();

    private String nom;
    private ArrayList<Creature> listCreatures;
    private ArrayList<Equipement> listEquipements;

    public static Joueur getInstance() {
        return ourInstance;
    }

    private Joueur() {
        listCreatures = new ArrayList<>();
        listCreatures.add(new Creature("Pierre","Le Paysan","",10, 5, 5));
        listCreatures.add(new Creature("Karl","","",10, 7, 3));
        listCreatures.add(new Creature("Nicholas","","",10, 3, 7));

        listEquipements = new ArrayList<>();
    }

    public void addCreature(Creature creature) {
        listCreatures.add(creature);
    }

    public ArrayList<Creature> getListCreatures() {
        return listCreatures;
    }
}
