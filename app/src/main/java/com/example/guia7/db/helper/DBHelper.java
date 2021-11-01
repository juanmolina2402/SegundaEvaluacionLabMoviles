package com.example.guia7.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private String crearJugador = "create table players" +
        "(" +
            "idPlayer INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nickNamePlayer VARCHAR(30)," +
            "idImg INTEGER," +
            "scorePlayer INTEGER," +
            "foreign key(idImg) references imgs(idImg)"+
       ")";

    private String crearImagen =  "create table imgs" +
            "(" +
                "idImg INTEGER PRIMARY KEY AUTOINCREMENT," +
                "URL VARCHAR(200)" +
            ")";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(crearJugador);
        sqLiteDatabase.execSQL(crearImagen);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS players");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS imgs");
    }
}
