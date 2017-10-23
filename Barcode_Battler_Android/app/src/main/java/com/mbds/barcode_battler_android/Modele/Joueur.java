package com.mbds.barcode_battler_android.Modele;

import java.util.ArrayList;

/**
 * Created by PGNem on 23/10/2017.
 */

public class Joueur {

    private String nom;
    private ArrayList<Creature> listCreature;
    private ArrayList<Equipement> listEquipement;

    public Joueur(String nom) {
        this.setNom(nom);
        this.setListEquipement(new ArrayList<Equipement>());
        this.setListCreature(new ArrayList<Creature>());
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<Creature> getListCreature() {
        return listCreature;
    }

    public void setListCreature(ArrayList<Creature> listCreature) {
        this.listCreature = listCreature;
    }

    public ArrayList<Equipement> getListEquipement() {
        return listEquipement;
    }

    public void setListEquipement(ArrayList<Equipement> listEquipement) {
        this.listEquipement = listEquipement;
    }
}
