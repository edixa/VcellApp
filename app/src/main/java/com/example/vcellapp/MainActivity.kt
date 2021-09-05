package com.example.vcellapp

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var edtCodigo:EditText?=null
    var edtMarca:EditText?=null
    var edtModelo:EditText?=null
    var edtEntrada:EditText?=null
    var edtSalida:EditText?=null
    var tlCelulares:TableLayout?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtCodigo=findViewById(R.id.edtCodigo)
        edtMarca=findViewById(R.id.edtMarca)
        edtModelo=findViewById(R.id.edtModelo)
        edtEntrada=findViewById(R.id.edtEntrada)
        edtSalida=findViewById(R.id.edtSalida)
        tlCelulares=findViewById(R.id.tlCelulares)
        llenarTabla()
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
    fun eliminar(view: View){
        val con=SqlDB(this,"Tienda",null, 1)
        val baseDatos=con.writableDatabase
        val codigo=edtCodigo?.text.toString()
        if(codigo.isEmpty()==false){
            val cant=baseDatos.delete("inventario", "codigo='"+codigo+"'", null)
            if(cant>0){
                Toast.makeText(this, "El celular fue eliminado",Toast.LENGTH_LONG).show()

            }
            else{
                Toast.makeText( this,"El celular no se encontro", Toast.LENGTH_SHORT).show()
            }
            edtCodigo?.setText("")
            edtMarca?.setText("")
            edtModelo?.setText("")
            edtEntrada?.setText("")
            edtSalida?.setText("")
        }
        else{
            Toast.makeText(this,"El campo codigo debe tener texto", Toast.LENGTH_LONG).show()
        }
    }
    fun editar(view: View){
        val con=SqlDB(this,"Tienda", null,1)
        val baseDatos=con.writableDatabase

        val codigo=edtCodigo?.text.toString()
        val marca=edtMarca?.text.toString()
        val modelo=edtModelo?.text.toString()
        val entrada=edtEntrada?.text.toString()
        val salida=edtSalida?.text.toString()

        if(!codigo.isEmpty() && !marca.isEmpty() && !modelo.isEmpty() && !entrada.isEmpty() && !salida.isEmpty()){
            var inventario=ContentValues()
            inventario.put("codigo", codigo)
            inventario.put("marca", marca)
            inventario.put("modelo", modelo)
            inventario.put("entrada", entrada)
            inventario.put("salida", salida)

            val cant=baseDatos.update("inventario", inventario, "codigo='$codigo'", null)

            if(cant>0){
                Toast.makeText(this,"El registro se ha editado correctamente", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this, "El registro no fue encontrado", Toast.LENGTH_LONG).show()
            }
        }
    }
    fun llenarTabla(){
        tlCelulares?.removeAllViews()
        val con=SqlDB(this,"Tienda", null, 1)
        val baseDatos=con.writableDatabase
        val fila=baseDatos.rawQuery("select codigo, marca, modelo, entrada, salida from inventario", null)
        fila.moveToFirst()
        do{
            val registro=LayoutInflater.from(this).inflate(R.layout.item_table_layout_ed, null, false)
            val tvCodigo=registro.findViewById<View>(R.id.tvCodigo) as TextView
            val tvMarca=registro.findViewById<View>(R.id.tvMarca) as TextView
            val tvModelo=registro.findViewById<View>(R.id.tvModelo) as TextView
            val tvEntrada=registro.findViewById<View>(R.id.tvEntrada) as TextView
            val tvSalida=registro.findViewById<View>(R.id.tvSalida) as TextView
            tvCodigo.setText(fila.getString(0))
            tvMarca.setText(fila.getString(1))
            tvModelo.setText(fila.getString(2))
            tvEntrada.setText(fila.getString(3))
            tvSalida.setText(fila.getString(4))
            tlCelulares?.addView(registro)

        }while (fila.moveToNext())
    }
}