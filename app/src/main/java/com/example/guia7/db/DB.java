package com.example.guia7.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.guia7.db.helper.DBHelper;
import com.example.guia7.model.Image;
import com.example.guia7.model.Player;

import java.util.ArrayList;

public class DB {
    ///Propiedad de la clase DBHelper
    DBHelper dbHelper;

    //Constructor
    public DB(Context context) {
        ///Inicializamos la propiedad valor
        dbHelper = new DBHelper(context, "BD_Players", null, 1);
    }

    ///Consulta simple de imgs
    public Cursor getCursor() {
        return dbHelper.getReadableDatabase().rawQuery(
                "select * from imgs", null
        );
    }

    ///Consulta multitabla de players
    public Cursor getCursorPlayer(){
        return dbHelper.getWritableDatabase().rawQuery(
                "select p.idPlayer, p.nickNamePlayer, p.idImg, p.scorePlaye " +
                        "from players p,imgs c where p.idImg=i.idImg",null);
    }


    ///Consulta multitabla con parámetro de players
    public Cursor getCursorPlayer(String idImg) {
        return dbHelper.getReadableDatabase().rawQuery(
                "select p.idPlayer ,p.nickNamePlayer ,p.idImg ,p.scorePlayer " +
                        "from players p,imgs i where p.idImg=i.idImg and " +
                        "p.idImg=?"
                , new String[]{idImg});
    }
    ///Obteniendo todos los jugadores
    public ArrayList<Player> getArrayPlayers(Cursor cursor){
        cursor.moveToFirst();
        ArrayList<Player> lstPlayer = new ArrayList<>();
        if(cursor != null && cursor.getCount() > 0){
            do {
                lstPlayer.add(new Player(
                        cursor.getString(0), ///idPlayer
                        cursor.getString(1), ///nickNamePlayer
                        cursor.getString(2), ///idImg
                        cursor.getString(3) ///scorePlayer
                ));
            }while (cursor.moveToNext());
            return lstPlayer;
        }
        return null;
    }

    ///Guardar o Actualizar Jugador
    public boolean guardar_O_ActualizarJUgador(Player player){
        ContentValues initialValues = new ContentValues();
        if(!player.getIdPlayer().isEmpty()){
            initialValues.put("idPlayer", Integer.parseInt(player.getIdPlayer()));
        }
        initialValues.put("nickNamePlayer", player.getNickNamePlayer());
        initialValues.put("idImg", Integer.parseInt(player.getIdImg()));
        initialValues.put("scorePlayer", player.getScorePlayer());

        int id = (int) dbHelper.getWritableDatabase().insertWithOnConflict(
                "players",
                null,
                initialValues,
                SQLiteDatabase.CONFLICT_REPLACE
        );
        return id>0;
    }

    ///Obteniendo todas las imagenes
    public ArrayList<Image> getArrayImages(Cursor cursor) {
        cursor.moveToFirst();///Moverse al principio
        ArrayList<Image> lstImage = new ArrayList<>();
        ///Si la lista no está vacía y el cursor el conteo de los registros es mayor a cero
        ///Si hay datos
        if (cursor != null && cursor.getCount() > 0) {
            do {
                lstImage.add(new Image(
                        cursor.getString(0), //idImg
                        cursor.getString(2) //URL
                ));
                ///Si el elemento en el siguiente es verdadero
            } while (cursor.moveToNext());
            return lstImage;
        }
        return null;///Si no entró en el if
    }

    ///Guardar o Actualizar Imagen
    public boolean guardar_O_ActualizarImagen(Image image) {
        ContentValues initialValues = new ContentValues();
        ///Si el id del modelo Image está vacío
        if (!image.getIdImg().isEmpty()) {
            initialValues.put("idImg", Integer.parseInt(image.getIdImg()));
        }
            initialValues.put("URL", image.getURL());
            int id = (int) dbHelper.getWritableDatabase().insertWithOnConflict(
                    "imgs",
                    null,
                    initialValues,
                    SQLiteDatabase.CONFLICT_REPLACE);
            return id > 0;
        }
        ///Borrar Imagen
        public void borrarImagen(String id){
            dbHelper.getWritableDatabase().execSQL(String.format("delete from imgs where idImg='%s'", id));
        }
        ///Borrar Jugador
        public void borrarJugador(String id){
            dbHelper.getWritableDatabase().execSQL(String.format("delete from players where idPlayer='%s'", id));
        }
    }
