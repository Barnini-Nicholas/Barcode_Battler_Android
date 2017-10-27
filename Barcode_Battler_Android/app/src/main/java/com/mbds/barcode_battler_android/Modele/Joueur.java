package com.mbds.barcode_battler_android.Modele;

import com.mbds.barcode_battler_android.BDD;

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
        listCreatures = BDD.getInstance().getCreature();
        if (listCreatures == null) {
            listCreatures = new ArrayList<>();
        }
        // listCreatures.add(new Creature("Pierre","Le Paysan","",10, 5, 5));
        // listCreatures.add(new Creature("Karl","","",10, 7, 3));
        // listCreatures.add(new Creature("Nicholas","","",10, 3, 7));

        listEquipements = BDD.getInstance().getEquipement();
        if (listEquipements == null) {
            listEquipements = new ArrayList<>();
        }
    }

    public void addCreature(Creature creature) {
        BDD.getInstance().addCreature(creature);
        listCreatures = BDD.getInstance().getCreature();
    }

    public ArrayList<Creature> getListCreatures() {
        return listCreatures;
    }

    public void addEquipement(Equipement equipement) {
        BDD.getInstance().addEquipement(equipement);
        listEquipements = BDD.getInstance().getEquipement();
    }

    public ArrayList<Equipement> getListEquipement() {
        return listEquipements;
    }

    public void resetListCreatures() {
        listCreatures = BDD.getInstance().getCreature();
    }
}
