package com.mbds.barcode_battler_android.Modele;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by PGNem on 23/10/2017.
 */

public class Equipement implements Parcelable {

    private int id;
    private String nom;
    private int bonusPV;
    private int bonusPA;
    private int bonusPB;


    public Equipement(String nom, int bonusPV, int bonusPA, int bonusPB) {
        this.setNom(nom);
        this.setBonusPV(bonusPV);
        this.setBonusPA(bonusPA);
        this.setBonusPB(bonusPB);

    }

    public int getRarete() {
        return ((bonusPV + bonusPA + bonusPB) / 10) + 1;
    }

    // Parcelling part
    public Equipement(Parcel in) {
        String[] data = new String[4];

        in.readStringArray(data);
        int i = 0;

        nom = data[i++];
        bonusPV = Integer.parseInt(data[i++]);
        bonusPA = Integer.parseInt(data[i++]);
        bonusPB = Integer.parseInt(data[i++]);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{nom, bonusPV + "", bonusPA + "", bonusPB + ""});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Equipement createFromParcel(Parcel in) {
            return new Equipement(in);
        }

        public Equipement[] newArray(int size) {
            return new Equipement[size];
        }
    };


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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Equipement{" +
                "nom='" + nom + '\'' +
                ", bonusPV=" + bonusPV +
                ", bonusPA=" + bonusPA +
                ", bonusPB=" + bonusPB +
                '}';
    }
}
