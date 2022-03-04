package com.pp.practica02.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class dbHelper (context: Context?) : SQLiteOpenHelper(context, DB_Name, null, DB_Version) {
    // private static final -> java
    companion object{
        private const val DB_Version = 1
        private const val DB_Name = "peliculas.db"
        public const val DB_Tables = "pelicula"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("CREATE TABLE $DB_Tables (Id INTEGER PRIMARY KEY AUTOINCREMENT, Titulo TEXT NOT NULL, Director TEXT NOT NULL, Genero TEXT NOT NULL,Duracion INT not null, Calificacion FLOAT )")
        //var Id: Int,var Titulo: String,var Director: String,var Genero: String, var Duracion: Int, var Calificacion: Int
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE $DB_Tables")
        onCreate(p0)
    }
}