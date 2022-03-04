package com.pp.practica02.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.pp.practica02.MainActivity
import com.pp.practica02.R
import com.pp.practica02.data.dbPeliculas
import com.pp.practica02.databinding.ActivityInsertBinding

class InsertActivity : AppCompatActivity() {
    private lateinit var insertBinding: ActivityInsertBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        insertBinding = ActivityInsertBinding.inflate(layoutInflater)

        val modeBundle=intent.extras
        val mode=modeBundle?.getInt("Mode")
        Toast.makeText(this@InsertActivity,"Modo $mode",Toast.LENGTH_SHORT)

        setContentView(insertBinding.root)
    }

    fun btnGuardar_click(view: View) {
        val dbpelis = dbPeliculas(this@InsertActivity)
        //val extras=intent.extras
        val modeBundle=intent.extras

        with(insertBinding){
            if( modeBundle?.getInt("Mode") == 0){  //.getInt("operacion"))
                Toast.makeText(this@InsertActivity,"Nuevo",Toast.LENGTH_SHORT)
                if( !iTxtTitulo.text.isNullOrEmpty() && !iTxtDirector.text.isNullOrEmpty() &&
                    !iTxtGenero.text.isNullOrEmpty() && !iTxtDuracion.text.isNullOrEmpty() ){
                    val Id = dbpelis.insertPelicula(iTxtTitulo.text.toString(),iTxtDirector.text.toString(),iTxtGenero.text.toString(),iTxtDuracion.text.toString().toInt(),rBarCalificacion.rating)
                    if(Id > 0){
                        Toast.makeText(this@InsertActivity,"Registro agregado correctamente!!",Toast.LENGTH_SHORT).show()
                        limpia_view()
                    }else{
                        Toast.makeText(this@InsertActivity,"Error al intentar guardar el registro. Verifica la informacion",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this@InsertActivity,"Informacion incompleta. Proporciones todos los datos",Toast.LENGTH_SHORT).show()
                }
            }else{ Toast.makeText(this@InsertActivity,"Edicion",Toast.LENGTH_SHORT) }

        }

        // Para finalizar vista
        // finish()
    }

    fun limpia_view(){
        with(insertBinding){
            iTxtTitulo.setText("")
            iTxtDirector.setText("")
            iTxtGenero.setText("")
            iTxtDuracion.setText("")
            rBarCalificacion.rating = 0.0f
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@InsertActivity,MainActivity::class.java))
    }
}