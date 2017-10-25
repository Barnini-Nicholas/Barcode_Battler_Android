package com.mbds.barcode_battler_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

import com.mbds.barcode_battler_android.Modele.Creature;
import com.mbds.barcode_battler_android.Modele.Equipement;
import com.mbds.barcode_battler_android.Service.TagLog;

import java.util.ArrayList;

/**
 * Created by PGNem on 24/10/2017.
 */

public class BDD extends SQLiteOpenHelper {

    // TABLE EQUIPEMENT

    private static final String TABLE_EQUIPEMENT = "table_EQUIPEMENT";
    private static final String COL_ID_EQUIPEMENT = "ID";
    private static final String NOM_EQUIPEMENT = "NOM";
    private static final String bonusPV = "BONUSPV";
    private static final String bonusPA = "BONUSPA";
    private static final String bonusPB = "BONUSPB";

    private static final String CREATE_EQUIPEMENT = "CREATE TABLE " + TABLE_EQUIPEMENT + " ("
            + COL_ID_EQUIPEMENT + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOM_EQUIPEMENT + " TEXT NOT NULL, "
            + bonusPV + " INTEGER NOT NULL, " + bonusPA + " INTEGER NOT NULL, " + bonusPB + " INTEGER NOT NULL);";

    // TABLE CREATURE

    private static final String TABLE_CREATURE = "table_CREATURE";
    private static final String COL_ID_CREATURE = "ID";
    private static final String NOM_CREATURE = "NOM";
    private static final String TITRE = "TITRE";
    private static final String RACE = "RACE";
    private static final String PV = "PV";
    private static final String PA = "PA";
    private static final String PB = "PB";

    private static final String CREATE_CREATURE = "CREATE TABLE " + TABLE_CREATURE + " ("
            + COL_ID_CREATURE + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOM_CREATURE + " TEXT NOT NULL, " + TITRE + " TEXT NOT NULL, " + RACE + " TEXT NOT NULL, "
            + PV + " INTEGER NOT NULL, " + PA + " INTEGER NOT NULL, " + PB + " INTEGER NOT NULL);";


    public BDD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CREATURE);
        db.execSQL(CREATE_EQUIPEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_EQUIPEMENT + ";");
        db.execSQL("DROP TABLE " + TABLE_CREATURE + ";");
        onCreate(db);
    }

    public void addCreature(Creature c){
        Log.v(TagLog.BD_CREATURE, "Insert d'un équipement : " + c);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOM_CREATURE, c.getNom());
        values.put(TITRE, c.getTitre());
        values.put(RACE, c.getRace());
        values.put(bonusPV, c.getPV());
        values.put(bonusPA, c.getPA());
        values.put(bonusPB, c.getPB());
        db.insert(TABLE_CREATURE, null, values);
        db.close();
    }

    public ArrayList<Creature> getCreature() {
        SQLiteDatabase db = this.getWritableDatabase();
        //Récupère dans un Cursor les valeurs correspondant à une créature contenu dans la BDD
        Cursor c = db.query(TABLE_CREATURE, new String[]{COL_ID_CREATURE, NOM_CREATURE, TITRE, RACE, PV,PA, PB}, null, null, null, null, null);
        return cursorToCreature(c);
    }

    //Cette méthode permet de convertir un cursor sur une créature
    private ArrayList<Creature> cursorToCreature(Cursor c) {

        Log.v(TagLog.BD_CREATURE, "Début d'un getAll sur Equipement");

        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        ArrayList<Creature> listCret = new ArrayList<Creature>();

        //Sinon on se place sur le premier élément
        c.moveToFirst();

        for (int i = 0; i < c.getCount(); i++) {

            listCret.add(new Creature(c.getString(1), c.getString(2), c.getString(3), c.getInt(4), c.getInt(5), c.getInt(6)));

            c.moveToNext();
        }
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor

        //On ferme le cursor
        c.close();

        Log.v(TagLog.BD_CREATURE, "Fin d'un getAll : " + listCret);

        //On retourne la créature
        return listCret;
    }

    public void addEquipement(Equipement e) {
        Log.v(TagLog.BD_EQUIPEMENT, "Insert d'un équipement : " + e);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOM_EQUIPEMENT, e.getNom());
        values.put(bonusPV, e.getBonusPV());
        values.put(bonusPA, e.getBonusPA());
        values.put(bonusPB, e.getBonusPB());
        db.insert(TABLE_EQUIPEMENT, null, values);
        db.close();
    }

    public ArrayList<Equipement> getEquipement() {
        SQLiteDatabase db = this.getWritableDatabase();
        //Récupère dans un Cursor les valeurs correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = db.query(TABLE_EQUIPEMENT, new String[]{COL_ID_EQUIPEMENT, NOM_EQUIPEMENT, bonusPV, bonusPA, bonusPB}, null, null, null, null, null);
        return cursorToEquipement(c);
    }

    //Cette méthode permet de convertir un cursor sur un équipement
    private ArrayList<Equipement> cursorToEquipement(Cursor c) {

        Log.v(TagLog.BD_EQUIPEMENT, "Début d'un getAll sur Equipement");

        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        ArrayList<Equipement> listEquip = new ArrayList<Equipement>();

        //Sinon on se place sur le premier élément
        c.moveToFirst();

        for (int i = 0; i < c.getCount(); i++) {

            listEquip.add(new Equipement(c.getString(1), c.getInt(2), c.getInt(3), c.getInt(4)));

            c.moveToNext();
        }
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor

        //On ferme le cursor
        c.close();

        Log.v(TagLog.BD_EQUIPEMENT, "Fin d'un getAll : " + listEquip);

        //On retourne le l'equipement
        return listEquip;
    }

}
