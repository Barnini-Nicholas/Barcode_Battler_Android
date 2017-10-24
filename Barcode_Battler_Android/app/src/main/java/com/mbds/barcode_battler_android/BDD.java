package com.mbds.barcode_battler_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

import com.mbds.barcode_battler_android.Modele.Equipement;
import com.mbds.barcode_battler_android.Service.TagLog;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by PGNem on 24/10/2017.
 */

public class BDD extends SQLiteOpenHelper {

    private static final String TABLE_EQUIPEMENT = "table_EQUIPEMENT";
    private static final String COL_ID = "ID";
    private static final String NOM = "NOM";
    private static final String bonusPV = "BONUSPV";
    private static final String bonusPA = "BONUSPA";
    private static final String bonusPB = "BONUSPB";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_EQUIPEMENT + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOM + " TEXT NOT NULL, "
            + bonusPV + " INTEGER NOT NULL, " + bonusPA + " INTEGER NOT NULL, " + bonusPB + " INTEGER NOT NULL);";

    public BDD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_EQUIPEMENT + ";");
        onCreate(db);
    }

    public void addEquipement(Equipement e) {
        Log.v(TagLog.BD_EQUIPEMENT, "Insert d'un équipement : " + e);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOM, e.getNom());
        values.put(bonusPV, e.getBonusPV());
        values.put(bonusPA, e.getBonusPA());
        values.put(bonusPB, e.getBonusPB());
        db.insert(TABLE_EQUIPEMENT, null, values);
        db.close();
    }

    public ArrayList<Equipement> getEquipement() {
        SQLiteDatabase db = this.getWritableDatabase();
        //Récupère dans un Cursor les valeurs correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = db.query(TABLE_EQUIPEMENT, new String[]{COL_ID, NOM, bonusPV, bonusPA, bonusPB}, null, null, null, null, null);
        return cursorToEquipement(c);
    }

    //Cette méthode permet de convertir un cursor en un livre
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

        //On retourne le livre
        return listEquip;
    }

}
