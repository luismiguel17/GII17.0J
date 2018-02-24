package com.tfg_gii14b.mario.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Esta clase gestiona la creacion de la base de datos
 * @author: Mario López Jiménez
 * @version: 1.0
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    //Nombre de nuestra BD
    private static final String DB_NAME = "dataBase.sqlite";
    //Version de nuestra BD
    private static final int DB_SCHEMA_VERSION = 1;


    /**
     * DataBaseHelper. Constructor.
     * @param context Contexto actual.
     */
    public DataBaseHelper(Context context) {

        super(context, DB_NAME, null, DB_SCHEMA_VERSION);
    }

    /**
     * onCreate. Metodo que realiza la creacion de las tablas
     * de nuestra Base de Datos.
     * @param db Base de Datos SQLite.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBaseManager.CREATE_ALIMENTOS);
        db.execSQL(DataBaseManager.CREATE_GLUCEMIAS);
        db.execSQL(DataBaseManager.CREATE_INCIDENCIAS);

    }

    /**
     * onUpgrade. Metodo que actualizara nuestra Base de datos.
     * Si el usuario reinstala la app y encuentra una version antigua,elimina las tablas anteriores
     * y las vuelve a crear.
     * @param db Base de datos.
     * @param oldVersion Version antigua de nuestra Base de datos.
     * @param newVersion Nueva version de nuestra Base de datos.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS glucemias");
        db.execSQL("DROP TABLE IF EXISTS incidencias");
        db.execSQL("DROP TABLE IF EXISTS alimentos");

        onCreate(db);
    }
}
