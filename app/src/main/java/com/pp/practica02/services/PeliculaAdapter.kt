package com.pp.practica02.services

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import com.pp.practica02.models.Pelicula
import com.pp.practica02.databinding.ListaMainBinding


class PeliculaAdapter(contexto: Context, listaPeliculas: ArrayList<Pelicula>): BaseAdapter() {
    private val listaPeliculas = listaPeliculas
    private val layoutInflater = LayoutInflater.from(contexto)

    override fun getCount(): Int {
        return  listaPeliculas.size
    }

    override fun getItem(p0: Int): Any {
        return  listaPeliculas[p0]
    }

    override fun getItemId(p0: Int): Long {
        return listaPeliculas[p0].Id.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        //val binding = ActivityMainBinding.inflate(layoutInflater).root.lv
        val binding = ListaMainBinding.inflate(layoutInflater)

        with(binding){
            txtvTitulo.text = listaPeliculas[p0].Titulo
            txtvDirector.text = listaPeliculas[p0].Director
            ratbCalificacion.rating = listaPeliculas[p0].Calificacion.toFloat()
            txtvDuracion.text = listaPeliculas[p0].Duracion.toString() +"mins."
        }

        return  binding.root
    }
}
//class PeliculaAdapter(context: Context,listaPeliculas: ArrayList<Pelicula>) : ArrayAdapter<Pelicula>(context,0, listaPeliculas) {
//}