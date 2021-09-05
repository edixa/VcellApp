package com.example.vcellapp

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var edtCodigo:EditText?=null
    var edtMarca:EditText?=null
    var edtModelo:EditText?=null
    var edtEntrada:EditText?=null
    var edtSalida:EditText?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtCodigo=findViewById(R.id.edtCodigo)
        edtMarca=findViewById(R.id.edtMarca)
        edtModelo=findViewById(R.id.edtModelo)
        edtEntrada=findViewById(R.id.edtEntrada)
        edtSalida=findViewById(R.id.edtSalida)
    }
    fun insertar(view:View) {
        var con = SqlDB(this, "Tienda", null, 1)
        var baseDatos = con.writableDatabase

        var codigo = edtCodigo?.text.toString()
        var marca = edtMarca?.text.toString()
        var modelo = edtModelo?.text.toString()
        var entrada = edtEntrada?.text.toString()
        var salida = edtSalida?.text.toString()

        if (codigo.isEmpty() == false && marca.isEmpty() == false && modelo.isEmpty() == false && entrada.isEmpty() == false && salida.isEmpty() == false) {
            var inventario=ContentValues()
            inventario.put("codigo", codigo)
            inventario.put("marca", marca)
            inventario.put("modelo", modelo)
            inventario.put("entrada", entrada)
            inventario.put("salida", salida)
            baseDatos.insert("inventario", null, inventario)
            edtCodigo?.setText("")
            edtMarca?.setText("")
            edtModelo?.setText("")
            edtEntrada?.setText("")
            edtSalida?.setText("")
            Toast.makeText(this,"Se ha ingresado exitosamente", Toast.LENGTH_LONG).show()
        } else{
            Toast.makeText(this,"Los campos deden ser llenados", Toast.LENGTH_LONG).show()
        }
        baseDatos.close()
    }
    fun buscar(view:View){
        val con=SqlDB(this, "Tienda", null, 1)
        val baseDatos=con.writableDatabase
        val codigo=edtCodigo?.text.toString()
        if(codigo.isEmpty()==false){
            val fila=baseDatos.rawQuery("select marca, modelo, entrada, salida from inventario where codigo='$codigo'", null)
            if(fila.moveToFirst()==true){
                edtMarca?.setText(fila.getString(0))
                edtModelo?.setText(fila.getString(1))
                edtEntrada?.setText(fila.getString(2))
                edtSalida?.setText(fila.getString(3))
                baseDatos.close()

            }
            else{
                edtMarca?.setText("")
                edtModelo?.setText("")
                edtEntrada?.setText("")
                edtSalida?.setText("")
                Toast.makeText(this, "No se encontraron registros", Toast.LENGTH_LONG).show()

            }
        }
    }
}