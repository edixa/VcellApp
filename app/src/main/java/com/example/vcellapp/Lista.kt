package com.example.vcellapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*



class Lista : AppCompatActivity() {
    var edtCodigo: EditText?=null
    var edtMarca: EditText?=null
    var edtModelo: EditText?=null
    var edtEntrada: EditText?=null
    var edtSalida: EditText?=null



    var edtFecha: EditText?=null

    var tlLista: TableLayout?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)
        tlLista=findViewById(R.id.tlLista)
        edtCodigo=findViewById(R.id.edtCodigo)
        edtMarca=findViewById(R.id.edtMarca)
        edtModelo=findViewById(R.id.edtModelo)
        edtEntrada=findViewById(R.id.edtEntrada)
        edtSalida=findViewById(R.id.edtSalida)

        edtFecha=findViewById(R.id.edtFecha)



      llenarTabla()
    }





    fun llenarTabla() {

        val con = SqlDB(this, "Tienda", null, 1)
        val baseDatos = con.writableDatabase
        var sql = ""


        val fila=baseDatos.rawQuery(sql,null)
        fila.moveToFirst()
        do{
            val registro= LayoutInflater.from(this).inflate(R.layout.item_table_layout_ed, null, false)
            val tvCodigo=registro.findViewById<View>(R.id.tvCodigo) as TextView
            val tvMarca=registro.findViewById<View>(R.id.tvMarca) as TextView
            val tvModelo=registro.findViewById<View>(R.id.tvModelo) as TextView
            val tvEntrada=registro.findViewById<View>(R.id.tvEntrada) as TextView
            val tvSalida=registro.findViewById<View>(R.id.tvSalida) as TextView
            val tvFecha=registro.findViewById<View>(R.id.tvFecha) as TextView

            tvCodigo.setText(fila.getString(0))
            tvMarca.setText(fila.getString(1))
            tvModelo.setText(fila.getString(2))
            tvEntrada.setText(fila.getString(3))
            tvSalida.setText(fila.getString(4))
            tvFecha.setText(fila.getString(5))
            tlLista?.addView(registro)



        }while (fila.moveToNext())

    }


    fun clickRegistro(view: View){
        resetColorRegistros()
        view.setBackgroundColor(Color.GRAY)
        val registro=view as TableRow
        val controlCodigo=registro.getChildAt(0) as TextView
        val codigo=controlCodigo.text.toString()
        val con=SqlDB(this, "Tienda", null, 1)
        val baseDatos=con.writableDatabase
        if(!codigo.isEmpty()){
            val fila=baseDatos.rawQuery("select codigo,marca, modelo, entrada, salida, fecha from inventario where codigo='$codigo'", null )
            if(fila.moveToFirst()){
                edtCodigo?.setText(fila.getString(0))
                edtMarca?.setText(fila.getString(1))
                edtModelo?.setText(fila.getString(2))
                edtEntrada?.setText(fila.getString(3))
                edtSalida?.setText(fila.getString(4))
                edtFecha?.setText(fila.getString(5))

            }
            else{
                edtCodigo?.setText("")
                edtMarca?.setText("")
                edtModelo?.setText("")
                edtEntrada?.setText("")
                edtSalida?.setText("")
                edtFecha?.setText("")
                Toast.makeText(this, "No se ha encontrado ningun registro", Toast.LENGTH_SHORT).show()

            }
        }
    }
    fun resetColorRegistros() {
        for (i in 0..tlLista!!.childCount) {
            val registro = tlLista?.getChildAt(i)
            registro?.setBackgroundColor(Color.TRANSPARENT)

        }

    }
}