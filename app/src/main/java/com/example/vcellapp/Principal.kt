package com.example.vcellapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import kotlin.concurrent.thread
import kotlin.concurrent.timer

class Principal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
    }



    fun agregarProducto(view: View){

        var Entrar = findViewById<ImageButton>(R.id.Entrar)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)




    }



}