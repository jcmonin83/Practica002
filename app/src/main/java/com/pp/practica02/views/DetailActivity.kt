package com.pp.practica02.views

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.pp.practica02.MainActivity
import com.pp.practica02.R
import com.pp.practica02.data.dbPeliculas
import com.pp.practica02.databinding.ActivityDetailBinding
import com.pp.practica02.models.Pelicula

class DetailActivity : AppCompatActivity() {

    private lateinit var detailBinding: ActivityDetailBinding
    private lateinit var dbPeliculas: dbPeliculas
    private var pelicula: Pelicula? = null
    var Id: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        if(savedInstanceState == null){
            val bundle = intent.extras
            if(bundle != null){
                Id = bundle.getInt("Id",0)
                cargaPelicula(this, Id!!)
            }
        }else{ Id = savedInstanceState.getSerializable("Id") as Int }
    }
    fun cargaPelicula(context: Context, Id: Int){
        dbPeliculas= dbPeliculas(context)

        pelicula = dbPeliculas.getPelicula(Id)
        if(pelicula != null){
            with(detailBinding){
                txtvTitulo.text=pelicula?.Titulo
                txtvDirector.text = pelicula?.Director

                ratbCalificacion.rating = pelicula?.Calificacion!!
                txtvDuracion.text = "${pelicula?.Duracion} mins."
            }
        }
        detailBinding.btnEditar.setOnClickListener{ view: View ->
            val intent = Intent(this@DetailActivity,InsertActivity::class.java)
            intent.putExtra("Mode",1)
            intent.putExtra("Id",Id)
            startActivity(intent)

        }
        detailBinding.btnBorrar.setOnClickListener { view: View ->
            AlertDialog.Builder(this)
                .setTitle("Confirmacion")
                .setMessage("Realmente deseas eliminar ${pelicula?.Titulo.toString()} ?")
                .setPositiveButton("Si",DialogInterface.OnClickListener { dialogInterface, i ->
                    if(dbPeliculas.deletePelicula(Id!!)){
                        Toast.makeText(this,"Pelicula ${pelicula?.Titulo} elimada !!",Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                })
                .setNegativeButton("No",DialogInterface.OnClickListener { dialogInterface, i ->

                }).show()
        }

    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,MainActivity::class.java))
    }
}