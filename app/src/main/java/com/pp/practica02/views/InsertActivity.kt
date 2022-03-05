package com.pp.practica02.views

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.pp.practica02.MainActivity
import com.pp.practica02.R
import com.pp.practica02.data.dbPeliculas
import com.pp.practica02.databinding.ActivityInsertBinding
import kotlin.concurrent.thread

class InsertActivity : AppCompatActivity() {
    private lateinit var insertBinding: ActivityInsertBinding
    private var Id: Int = 0
    private var Mode: Int = 0
    private lateinit var modeBundle: Bundle
    private lateinit var spinnerAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        insertBinding = ActivityInsertBinding.inflate(layoutInflater)
        setContentView(insertBinding.root)

        if(savedInstanceState == null){
            val modeBundle=intent.extras
            Id= modeBundle!!.getInt("Id")
            Mode = modeBundle!!.getInt("Mode")
        }else{
            Id = savedInstanceState.getSerializable("Id") as Int
            Mode = savedInstanceState.getSerializable("Mode") as Int
        }
        cargaSpinner(insertBinding.spnGenero)

        if(Mode == 1){
            cargaPelicula(this,Id)
        }
    }

    fun cargaSpinner(spinner: Spinner){
        val generoOption: Array<out String> = resources.getStringArray(R.array.generoOptions)
        if(spinner != null){
            spinnerAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,generoOption)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = spinnerAdapter

        }
    }

    fun cargaPelicula(context: Context, Id: Int){
        var dbPeliculas= dbPeliculas(context)

        var pelicula = dbPeliculas.getPelicula(Id)
        if(pelicula != null){
            with(insertBinding){

                iTxtTitulo.setText(pelicula?.Titulo)
                iTxtDirector.setText ( pelicula?.Director)
                //iTxtGenero.setText(pelicula?.Genero)
                spnGenero.setSelection(spinnerAdapter.getPosition(pelicula?.Genero))
                rBarCalificacion.rating = pelicula?.Calificacion
                iTxtDuracion.setText("${pelicula?.Duracion}")
            }
        }
    }

    fun btnGuardar_click(view: View) {
        val dbpelis = dbPeliculas(this@InsertActivity)
        //val extras=intent.extras
        val modeBundle=intent.extras

        with(insertBinding){
            val spnTxt = spnGenero.getItemAtPosition(spnGenero.selectedItemPosition).toString()
                if( !iTxtTitulo.text.isNullOrEmpty() && !iTxtDirector.text.isNullOrEmpty() &&
                    //!iTxtGenero.text.isNullOrEmpty()
                    !spnTxt.isNullOrEmpty()
                    && !iTxtDuracion.text.isNullOrEmpty() ){
                    when(Mode ){
                        0->{
                            //val id = dbpelis.insertPelicula(iTxtTitulo.text.toString(),iTxtDirector.text.toString(),iTxtGenero.text.toString(),iTxtDuracion.text.toString().toInt(),rBarCalificacion.rating)
                            val id = dbpelis.insertPelicula(iTxtTitulo.text.toString(),iTxtDirector.text.toString(),spnTxt,iTxtDuracion.text.toString().toInt(),rBarCalificacion.rating)

                            if(id > 0){
                                Toast.makeText(this@InsertActivity,"Registro agregado correctamente!!",Toast.LENGTH_SHORT).show()
                                limpia_view()
                            }else{
                                Toast.makeText(this@InsertActivity,"Error al intentar guardar el registro. Verifica la informacion",Toast.LENGTH_SHORT).show()
                            }
                        }
                        1->{
                            if(dbpelis.updatePelicula(Id,iTxtTitulo.text.toString(),iTxtDirector.text.toString(),spnTxt,iTxtDuracion.text.toString().toInt(),rBarCalificacion.rating)){
                                Toast.makeText(this@InsertActivity,"Registro agregado correctamente!!",Toast.LENGTH_SHORT).show()
                                limpia_view()
                            }else{ Toast.makeText(this@InsertActivity,"Error al intentar guardar el registro. Verifica la informacion",Toast.LENGTH_LONG).show() }
                        }
                    }
                }else{
                    Toast.makeText(this@InsertActivity,"Informacion incompleta. Proporciones todos los datos",Toast.LENGTH_SHORT).show()
                }
        }

//        // Para finalizar vista
//        thread {
//            Thread.sleep(3000)
//            startActivity(Intent(this@InsertActivity,MainActivity::class.java))
//            finish()
//        }
    }

    fun limpia_view(){
            with(insertBinding){
                iTxtTitulo.setText("")
                iTxtDirector.setText("")
                //iTxtGenero.setText("")
                cargaSpinner(insertBinding.spnGenero)
                iTxtDuracion.setText("")
                rBarCalificacion.rating = 0.0f
            }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@InsertActivity,MainActivity::class.java))
    }
}