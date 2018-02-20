package com.mbds.barcode_battler_android.Modele;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.mbds.barcode_battler_android.Service.TagLog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by barnini on 23/10/2017.
 */

public class Creature implements Parcelable, Serializable {

    private int id;
    private String nom;
    private String titre;
    private String race;

    // Point de Vie
    private int PV;

    //Point d'Attaque
    private int PA;

    // Point de Bouclier
    private int PB;

    private String codeBarreUtilise;

    private ArrayList<Equipement> listEquipement;

    public Creature(Creature creatACopier) {
        this.setNom(creatACopier.nom);
        this.setTitre(creatACopier.titre);
        this.setRace(creatACopier.race);
        this.setPV(creatACopier.PV);
        this.setPA(creatACopier.PA);
        this.setPB(creatACopier.PB);
        this.setListEquipement(creatACopier.getListEquipement());
        this.setCodeBarreUtilise(creatACopier.codeBarreUtilise);
    }

    public Creature(String nom, String titre, String race, int pv, int pa, int pb, String codeBarreUtilise) {
        this.setNom(nom);
        this.setTitre(titre);
        this.setRace(race);
        this.setPV(pv);
        this.setPA(pa);
        this.setPB(pb);
        this.setListEquipement(new ArrayList<Equipement>());
        this.setCodeBarreUtilise(codeBarreUtilise);
    }

    // Parcelling part
    public Creature(Parcel in) {
        String[] data = new String[7];

        in.readStringArray(data);
        int i = 0;

        nom = data[i++];
        titre = data[i++];
        race = data[i++];
        PV = Integer.parseInt(data[i++]);
        PA = Integer.parseInt(data[i++]);
        PB = Integer.parseInt(data[i++]);
        codeBarreUtilise = data[i++];

        listEquipement = new ArrayList<>();
    }

    public String attaque(Creature c2) {

        Random rdm = new Random();

        int scoreAttaque = rdm.nextInt(this.getPA() + 1);
        int scoreDefense = rdm.nextInt(c2.getPB() + 1);
        int resultat = scoreDefense - scoreAttaque;
        Log.v(TagLog.COMBAT, "scoreAttaque : " + scoreAttaque + " // scoreDefense : " + scoreDefense + " // Resultat : " + resultat);

        if (resultat < 0) {
            Log.v(TagLog.COMBAT, "PV au départ : " + c2.getPV());

            c2.setPV(c2.getPV() - Math.abs(resultat));
            Log.v(TagLog.COMBAT, "PV aprés l'attaque : " + c2.getPV());
            return "il attaque " + c2.getNom() + " (" + resultat + " PV)";

        } else {
            return "il rate son attaque... ";
        }

    }

    public int getRarete() {
        return ((PV + PA + PB) / 10) + 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{nom, titre, race, PV + "", PA + "", PB + "", codeBarreUtilise});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Creature createFromParcel(Parcel in) {
            return new Creature(in);
        }

        public Creature[] newArray(int size) {
            return new Creature[size];
        }
    };

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
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

    public String getNomEtTitre() {
        return nom + " " + titre;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCodeBarreUtilise() {
        return codeBarreUtilise;
    }

    public void setCodeBarreUtilise(String hashUtilise) {
        this.codeBarreUtilise = hashUtilise;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Creature{" +
                "id='" + id + '\'' +
                "nom='" + nom + '\'' +
                ", titre='" + titre + '\'' +
                ", race='" + race + '\'' +
                ", PV=" + PV +
                ", PA=" + PA +
                ", PB=" + PB +
                '}';
    }


}