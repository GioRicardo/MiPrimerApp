package com.giordanricardo.miprimerapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLite extends SQLiteOpenHelper {

    //Nombre de la Tabla:
    public static final String TABLE_NAME = "Usuarios";
    //Nombre de BD y version:
    public static final String DB_NAME = "Clientes";
    public static final int DB_VERSION = 1;
    public AdminSQLite(@Nullable Context context) {
        super(context,DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_NAME +"(" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Nombre TEXT NOT NULL," +
                "Correo TEXT NOT NULL," +
                "Contrasena TEXT NOT NULL);"
                );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }
}
