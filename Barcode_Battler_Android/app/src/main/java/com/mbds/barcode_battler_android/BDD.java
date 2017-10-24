package com.mbds.barcode_battler_android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by PGNem on 24/10/2017.
 */

public class BDD  extends SQLiteOpenHelper {

    private static final String TABLE_EQUIPEMENT = "table_EQUIPEMENT";
    private static final String COL_ID = "ID";
    private static final String NOM = "NOM";
    private static final String bonusPV = "BONUSPV";
    private static final String bonusPA = "BONUSPA";
    private static final String bonusPB = "BONUSPB";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_EQUIPEMENT + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOM + " TEXT NOT NULL, "
            + bonusPV + " INTEGER NOT NULL, "+ bonusPA + " INTEGER NOT NULL, "+ bonusPB + " INTEGER NOT NULL);";

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
}
