package com.mbds.barcode_battler_android.Modele;

import android.util.Log;

import com.mbds.barcode_battler_android.Service.TagLog;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by barnini on 23/10/2017.
 */

public class Creature {

    private int PV; // Vie
    private int PA; // Attaque
    private int PB; // Bouclier, pas défense héhé
    private ArrayList<Equipement> listEquipement;
    private String nom;

    public Creature(int pv, int pa, int pb, String nom) {
        this.setPV(pv);
        this.setPA(pa);
        this.setPB(pb);
        this.setListEquipement(new ArrayList<Equipement>());
        this.setNom(nom);
    }

    public int getPV() {
        return PV;
    }

    public void setPV(int PV) {
        this.PV = PV;
    }

    public int getPA() {
        return PA;
    }

    public void setPA(int PA) {
        this.PA = PA;
    }

    public int getPB() {
        return PB;
    }

    public void setPB(int PB) {
        this.PB = PB;
    }

    public ArrayList<Equipement> getListEquipement() {
        return listEquipement;
    }

    public void setListEquipement(ArrayList<Equipement> listEquipement) {
        this.listEquipement = listEquipement;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void attaque(Creature c2) {

        Random rdm = new Random();

        int scoreAttaque = rdm.nextInt(this.getPA() + 1);
        int scoreDefense = rdm.nextInt(c2.getPB() + 1);
        int resultat = scoreDefense - scoreAttaque;
        Log.v(TagLog.COMBAT, "scoreAttaque : " + scoreAttaque + " // scoreDefense : " + scoreDefense + " // Resultat : " + resultat);
        if (resultat < 0) {
            Log.v(TagLog.COMBAT, "PV au départ : " + c2.getPV());
            c2.setPV(c2.getPV() - Math.abs(resultat));
            Log.v(TagLog.COMBAT, "PV aprés l'attaque : " + c2.getPV());
        }

    }
}