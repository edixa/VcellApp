package com.example.vcellapp

import android.content.ContentValues
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    var edtCodigo:EditText?=null
    var edtMarca:EditText?=null
    var edtModelo:EditText?=null
    var edtEntrada:EditText?=null
    var edtSalida:EditText?=null
    var edtBuscarPor:EditText?=null
    var tlCelulares:TableLayout?=null
    var spBuscarPor:Spinner?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtCodigo=findViewById(R.id.edtCodigo)
        edtMarca=findViewById(R.id.edtMarca)
        edtModelo=findViewById(R.id.edtModelo)
        edtEntrada=findViewById(R.id.edtEntrada)
        edtSalida=findViewById(R.id.edtSalida)
        edtBuscarPor=findViewById(R.id.edtBuscarPor)
        tlCelulares=findViewById(R.id.tlCelulares)
        spBuscarPor=findViewById(R.id.spBuscarPor)

        var listaCampos= arrayOf("Selecione el campo a buscar", "codigo", "marca", "modelo", "entrada", "salida")
        var adaptador:ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item,listaCampos)
        spBuscarPor?.adapter=adaptador
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
        llenarTabla()
    }

    fun eliminar(view: View){
        val con=SqlDB(this,"Tienda",null, 1)
        val baseDatos=con.writableDatabase
        val codigo=edtCodigo?.text.toString()
        if(codigo.isEmpty()==false){
            val cant=baseDatos.delete("inventario", "codigo='"+codigo+"'", null)
            if(cant>0){
                Toast.makeText(this, "El registro fue eliminado",Toast.LENGTH_LONG).show()

            }
            else{
                Toast.makeText( this,"El egistro no fue encontrado", Toast.LENGTH_SHORT).show()
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
        llenarTabla()

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
        llenarTabla()
    }
    fun llenarTabla() {
        tlCelulares?.removeAllViews()
        val con = SqlDB(this, "Tienda", null, 1)
        val baseDatos = con.writableDatabase
        val BuscarPor = edtBuscarPor?.text.toString()
        val listaBuscarPor = spBuscarPor?.selectedItem.toString()
        var sql = ""
        if (!BuscarPor.isEmpty()) {
            if (listaBuscarPor == "codigo"){
                sql = "select codigo,marca,modelo,entrada,salida from inventario where codigo='$BuscarPor'"

        }else if (listaBuscarPor=="marca"){
            sql="select codigo,marca,modelo,entrada,salida from inventario where marca like'%$BuscarPor%'"

        }else if (listaBuscarPor=="modelo"){
                sql="select codigo,marca,modelo,entrada,salida from inventario where modelo like'%$BuscarPor%'"

        }else if (listaBuscarPor=="entrada"){
                sql="select codigo,marca,modelo,entrada,salida from inventario where entrada='$BuscarPor'"

        }else if (listaBuscarPor=="salida"){
                sql="select codigo, marca,modelo,entrada,salida from inventario where salida ='$BuscarPor'"

        }else{
            sql="select codigo,marca,modelo,entrada,salida from inventario"
        }
    }
        else{
            sql="select codigo,marca,modelo,entrada,salida from inventario"

        }

        val fila=baseDatos.rawQuery(sql,null)
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
    fun clickRegistro(view: View){
        resetColorRegistros()
        view.setBackgroundColor(Color.GRAY)
        val registro=view as TableRow
        val controlCodigo=registro.getChildAt(0) as TextView
        val codigo=controlCodigo.text.toString()
        val con=SqlDB(this, "Tienda", null, 1)
        val baseDatos=con.writableDatabase
        if(!codigo.isEmpty()){
            val fila=baseDatos.rawQuery("select codigo,marca, modelo, entrada, salida from inventario where codigo='$codigo'", null )
            if(fila.moveToFirst()){
                edtCodigo?.setText(fila.getString(0))
                edtMarca?.setText(fila.getString(1))
                edtModelo?.setText(fila.getString(2))
                edtEntrada?.setText(fila.getString(3))
                edtSalida?.setText(fila.getString(4))

            }
            else{
                edtCodigo?.setText("")
                edtMarca?.setText("")
                edtModelo?.setText("")
                edtEntrada?.setText("")
                edtSalida?.setText("")
                Toast.makeText(this, "No se ha encontrado ningun registro", Toast.LENGTH_SHORT).show()

            }
        }
    }
    fun resetColorRegistros() {
        for (i in 0 .. tlCelulares!!.childCount){
            val registro=tlCelulares?.getChildAt(i)
            registro?.setBackgroundColor(Color.TRANSPARENT)

        }
    }
    fun clickBotonBuscar(view: View){
        llenarTabla()
    }

    fun refrescar(view: View){

        llenarTabla()

    }
}