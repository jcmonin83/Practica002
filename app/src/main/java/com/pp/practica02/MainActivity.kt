package com.pp.practica02

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.pp.practica02.data.dbPeliculas
import com.pp.practica02.databinding.ActivityMainBinding
import com.pp.practica02.models.Pelicula
import com.pp.practica02.services.PeliculaAdapter
import com.pp.practica02.views.DetailActivity
import com.pp.practica02.views.InsertActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var listaPelicula: ArrayList<Pelicula>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtiene datos
        val dbPeliculas = dbPeliculas(this)
        listaPelicula =dbPeliculas.getPeliculas()
        if(listaPelicula.size > 0){
            binding.lvLista.visibility = View.VISIBLE
            binding.txtVwNoRegistros.visibility = View.INVISIBLE
        }else{
            binding.txtVwNoRegistros.visibility = View.VISIBLE
            binding.lvLista.visibility = View.INVISIBLE
        }

        // Instancia Adapter
        val peliculaAdapter = PeliculaAdapter(this, listaPelicula)


        binding.lvLista.setOnItemClickListener { adapterView: AdapterView<*>?, view: View?, i, l ->
            //l es el id
            //i es la posición
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("Id", l.toInt())

            startActivity(intent)
            finish()
        }
        binding.lvLista.adapter = peliculaAdapter

    }

    fun btnAgregarClick(view: View) {
        val intent = Intent(this@MainActivity,InsertActivity::class.java)
        intent.putExtra("Mode",0)
        startActivity(intent)
        finish()
    }
}