package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et_codigo;
    private EditText et_motivo;
    private EditText et_valor;
    private EditText et_materias;
    private EditText et_nivel;
    AdminSQLiteOpenHelper manejabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_codigo = (EditText)findViewById(R.id.txt_codigo);
        et_motivo = (EditText)findViewById(R.id.txt_motivo);
        et_valor = (EditText)findViewById(R.id.txt_valor);
        et_materias = (EditText)findViewById(R.id.txt_materias);
        et_nivel = (EditText)findViewById(R.id.txt_nivel);
        this.manejabase = new AdminSQLiteOpenHelper(this, "basepagos.db", null,1);
    }
    public void Guardar(View view){
        // ESTABLECE CONEXION Se define objeto de la clase AdminSQLiteOpenHelper manejabase, el nombre de la base de datos es basepagos
       // tiene que abrir la base de datos en modo lectura escritura
        SQLiteDatabase BaseDatos = this.manejabase.getWritableDatabase();
        //Datos ingresados  ´Se guardan en las variables creadas los valores ingresados por el usuario
        String codigo = et_codigo.getText().toString();
        String motivo = et_motivo.getText().toString();
        String valor = et_valor.getText().toString();
        String materias = et_materias.getText().toString();
        String nivel = et_nivel.getText().toString();
        //Valida los campos donde el usuario registra los datos, los campos deben ser diferentes de nulo
        if (!codigo.isEmpty() && !motivo.isEmpty() && !valor.isEmpty()){
            //Objeto de la clase ContentValues para colocar los campos en la base de datos, utiliza el metodo put con un par clave valor
            ContentValues registro = new ContentValues();

            //Se pasan los datos (referencia, dato)
            registro.put("codigo", codigo);
            registro.put("motivo", motivo);
            registro.put("valor", valor);
            registro.put("materias", materias);
            registro.put("nivel", nivel);

            BaseDatos.insert("pagos", null, registro);
            BaseDatos.close();
            //Limpiar los campos en los cuales el usuario ingreso
            et_codigo.setText("");
            et_motivo.setText("");
            et_valor.setText("");
            et_materias.setText("");
            et_nivel.setText("");

            Toast.makeText(this, "El pago se registro con exito ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
        }}

    public void Buscar(View view){
        // tiene que abrir la base de datos en modo lectura escritura utilizando la clase SQLiteDatabase
        SQLiteDatabase BaseDatos = this.manejabase.getWritableDatabase();
       //En base al código se recupera
        String codigo = et_codigo.getText().toString();
        //validar que el código ses diferente de nulo
        if (!codigo.isEmpty()){
            //Clase cursor para seleccionar el pago de un estudiante
            //Cursor fila = BaseDatos.rawQuery("select motivo, valor from pagos where codigo =" + codigo, null);
            //La clase cursor: cualquier consulta a la base de datos en Android devolverá un objeto de clase curso
                      Cursor fila = BaseDatos.rawQuery
                    ("select motivo, valor from pagos where codigo = ?",new String[]{codigo});
            //Si la consulta tiene valores. moveToFirst desplaza el cursor a la primera fila de los resultados de la consulta
            if(fila.getCount() > 0){
                fila.moveToFirst();
                et_motivo.setText(fila.getString(0));
                et_valor.setText(fila.getString(1));
                BaseDatos.close();
            } else {
                Toast.makeText(this, "El registro de pago no existe", Toast.LENGTH_SHORT).show();
                BaseDatos.close();
            }
        } else {
            Toast.makeText(this, "Debe ingresar el codigo del estudiante", Toast.LENGTH_SHORT).show();
        }
    }
}
