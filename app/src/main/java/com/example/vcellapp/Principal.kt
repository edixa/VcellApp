package com.example.vcellapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton


class Principal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

    }



    fun agregarProducto(view: View){

        var Entrar = findViewById<ImageButton>(R.id.Agregar)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)



    }

    fun lista(view: View){

        var Entrar = findViewById<ImageButton>(R.id.inventario)
        val intent = Intent(this, Lista::class.java)
        startActivity(intent)



    }



}