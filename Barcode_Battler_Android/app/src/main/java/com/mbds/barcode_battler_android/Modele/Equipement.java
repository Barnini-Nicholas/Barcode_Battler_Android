package com.mbds.barcode_battler_android.Modele;

/**
 * Created by PGNem on 23/10/2017.
 */

public class Equipement {

    private int bonusPA;
    private int bonusPV;
    private int bonusPB;
    private String nom;

    public Equipement(int bonusPA, int bonusPV, int bonusPB, String nom) {
        this.setBonusPA(bonusPA);
        this.setBonusPV(bonusPV);
        this.setBonusPB(bonusPB);
        this.setNom(nom);
    }

    public int getBonusPA() {
        return bonusPA;
    }

    public void setBonusPA(int bonusPA) {
        this.bonusPA = bonusPA;
    }

    public int getBonusPV() {
        return bonusPV;
    }

    public void setBonusPV(int bonusPV) {
        this.bonusPV = bonusPV;
    }

    public int getBonusPB() {
        return bonusPB;
    }

    public void setBonusPB(int bonusPB) {
        this.bonusPB = bonusPB;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
