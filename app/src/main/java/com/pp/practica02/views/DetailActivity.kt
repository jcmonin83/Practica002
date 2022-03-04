package com.pp.practica02.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pp.practica02.R
import com.pp.practica02.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var detailBinding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)
    }
}