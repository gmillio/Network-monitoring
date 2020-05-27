//*********************************************************************
//
//     Mace Alexy    Etudiant 3
//     11/03/2020
//     Projet: Gestion intégrée Open Source d'un réseau informatique
//     Code valeurs
//
//*********************************************************************


package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyDBHandler extends SQLiteOpenHelper {
    //information de la base de donnée

    private static final int BDD_VERSION           = 1;
    private static final String BDD_NOM            = "switchDB.db";
    private static final String TABLE_NAME         = "bddTest";
    private static final String COLUMN_ID          = "SwitchID";
    private static final String COLUMN_NAME        = "SwitchNom";
    private static final String COLUMN_MARQUE      = "SwitchMarque";
    private static final String COLUMN_MAC         = "SwitchMac";
    private static final String COLUMN_IP          = "SwitchIp";
    private static final String COLUMN_MASQUE      = "SwitchMasque";
    private static final String COLUMN_PASSERELLE  = "SwitchPasserelle";
    private static final String COLUMN_EMPLACEMENT = "SwitchEmplacement";


    //initialisation la base de donnée

     MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, BDD_NOM, factory, BDD_VERSION);
    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME + " TEXT," + COLUMN_MARQUE + " TEXT," + COLUMN_MAC + " TEXT," + COLUMN_IP + " TEXT,"
                + COLUMN_MASQUE + " TEXT," + COLUMN_PASSERELLE + " TEXT," + COLUMN_EMPLACEMENT + " TEXT )";

        db.execSQL(CREATE_TABLE);

        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES('" + "1" + "','" + "parent" + "','" + "HP" + "','" + "12:34:56:78:9a:bc" + "','" + "192.168.15.1" + "','255.255.255.0','" + "192.168.15.254" + "','" + "A" + "')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES('" + "2" + "','" + "enfant1" + "','" + "HP" + "','" + "21:34:56:78:9a:bc" + "','" + "192.168.15.2" + "','255.255.255.0','" + "192.168.15.254" + "','" + "A" + "')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES('" + "3" + "','" + "enfant2" + "','" + "CISCO" + "','" + "36:34:56:78:9a:bc" + "','" + "192.168.15.3" + "','255.255.255.0','" + "192.168.15.254" + "','" + "A" + "')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES('" + "4" + "','" + "enfant3" + "','" + "HP" + "','" + "48:34:56:78:9a:bc" + "','" + "192.168.15.4" + "','255.255.255.0','" + "192.168.15.254" + "','" + "A" + "')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES('" + "5" + "','" + "enfant4" + "','" + "DLINK" + "','" + "25:34:56:78:9a:bc" + "','" + "192.168.15.5" + "','255.255.255.0','" + "192.168.15.254" + "','" + "A" + "')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES('" + "6" + "','" + "enfant5" + "','" + "HP" + "','" + "98:34:56:78:9a:bc" + "','" + "192.168.15.6" + "','255.255.255.0','" + "192.168.15.254" + "','" + "A" + "')");
        //db.close();

    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }


     void addHandler(String id, String marque, String mac, String nom, String ip, String gateway, String salle) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES('" + id + "','" + nom + "','" + marque + "','" + mac + "','" + ip + "','255.255.255.0','" + gateway + "','" + salle + "')");
        db.close();
    }


     List<Switch> findHandlermarque(String switchmarque) {

        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN_MARQUE + " = " + "'" + switchmarque + "'";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Switch switchs = new Switch();
        List<Switch> switches = new CopyOnWriteArrayList<>();

        while (cursor.moveToNext()) {
            switches.add(new Switch(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7)));

            switchs.SetId(Integer.parseInt(cursor.getString(0)));
            switchs.SetNom(cursor.getString(1));
            switchs.SetMarque(cursor.getString(2));
            switchs.SetMac(cursor.getString(3));
            switchs.SetIp(cursor.getString(4));
            switchs.SetMasque(cursor.getString(5));
            switchs.SetPasserelle(cursor.getString(6));
            switchs.SetBatiment(cursor.getString(7));

        }
        cursor.close();
        db.close();
        return switches;
    }

     Switch findHandlerId(String id) {

        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + "'" + id + "'";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Switch switchs = new Switch();


        if (cursor.moveToFirst()) {
            switchs.SetId(Integer.parseInt(cursor.getString(0)));
            switchs.SetNom(cursor.getString(1));
            switchs.SetMarque(cursor.getString(2));
            switchs.SetMac(cursor.getString(3));
            switchs.SetIp(cursor.getString(4));
            switchs.SetMasque(cursor.getString(5));
            switchs.SetPasserelle(cursor.getString(6));
            switchs.SetBatiment(cursor.getString(7));
        } else {
            switchs.SetId(0);
        }


        cursor.close();
        db.close();
        return switchs;
    }

     List<Switch> findHandlerBatiment(String batiment) {

        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN_MAC + " = " + "'" + batiment + "'";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Switch switchs = new Switch();
        List<Switch> switches = new CopyOnWriteArrayList<>();

        while (cursor.moveToNext()) {
            switches.add(new Switch(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7)));

            switchs.SetId(Integer.parseInt(cursor.getString(0)));
            switchs.SetNom(cursor.getString(1));
            switchs.SetMarque(cursor.getString(2));
            switchs.SetMac(cursor.getString(3));
            switchs.SetIp(cursor.getString(4));
            switchs.SetMasque(cursor.getString(5));
            switchs.SetPasserelle(cursor.getString(6));
            switchs.SetBatiment(cursor.getString(7));

        }
        cursor.close();
        db.close();
        return switches;
    }




    Switch findHandlerMac(String mac) {
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN_MAC + " = " + "'" + mac + "'";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Switch switchs = new Switch();


        if (cursor.moveToFirst()) {
            switchs.SetId(Integer.parseInt(cursor.getString(0)));
            switchs.SetNom(cursor.getString(1));
            switchs.SetMarque(cursor.getString(2));
            switchs.SetMac(cursor.getString(3));
            switchs.SetIp(cursor.getString(4));
            switchs.SetMasque(cursor.getString(5));
            switchs.SetPasserelle(cursor.getString(6));
            switchs.SetBatiment(cursor.getString(7));
        } else {
            switchs.SetId(0);
        }


        cursor.close();
        db.close();
        return switchs;
     }
}
