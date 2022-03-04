package com.pp.practica02.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.pp.practica02.models.Pelicula

class dbPeliculas(context: Context?) : dbHelper(context)  {
    val context = context

    fun getPeliculas():ArrayList<Pelicula>{
        val dbHelper = dbHelper(context)
        val db = dbHelper.writableDatabase

        var lstPeliculas = ArrayList<Pelicula>()
        var peliculaTmp: Pelicula? = null
        var cursorPelicula: Cursor? = null

        cursorPelicula = db.rawQuery("select * from $DB_Tables", null)

        if(cursorPelicula.moveToFirst()){
            do{
                peliculaTmp = Pelicula(cursorPelicula.getInt(0),cursorPelicula.getString(1),cursorPelicula.getString(2),cursorPelicula.getString(3),cursorPelicula.getInt(4),cursorPelicula.getFloat(5))
                //var Id: Int,var Titulo: String,var Director: String,var Genero: String, var Duracion: Int, var Calificacion: Int
                lstPeliculas.add(peliculaTmp)
            }while(cursorPelicula.moveToNext())
        }
        return  lstPeliculas
    }

    fun insertPelicula(Titulo: String,Director: String,Genero: String,Duracion: Int,Calificacion: Float):Long{
        val dbHelper = dbHelper(context)
        val db = dbHelper.writableDatabase
        var Id:Long = 0

        try {
            val values = ContentValues()
            values.put("Titulo",Titulo)
            values.put("Director",Director)
            values.put("Genero",Genero)
            values.put("Duracion", Duracion)
            values.put("Calificacion",Calificacion)

            Id = db.insert(DB_Tables,null, values)

        }catch (e: Exception){
            Id=-1
        }finally {
            db.close()
        }
        return Id
    }

    fun updatePelicula(Id: Int,Titulo: String,Director: String,Genero: String,Duracion: Int,Calificacion: Float):Boolean{
        var modified:Boolean = false
        val dbHelper = dbHelper(context)
        val db = dbHelper.writableDatabase

        try {
            val values = ContentValues()
            values.put("Id",Id)
            values.put("Titulo",Titulo)
            values.put("Director",Director)
            values.put("Genero",Genero)
            values.put("Duracion", Duracion)
            values.put("Calificacion",Calificacion)

            db.execSQL("UPDATE $DB_Tables set Titulo='$Titulo', Director='$Director', Genero='$Genero', Duracion= '$Duracion', Calificacion= '$Calificacion' WHERE Id=$Id")
            modified=true

        }catch (e: Exception){
            modified=false
        }finally {
            db.close()
        }
        return modified
    }

}