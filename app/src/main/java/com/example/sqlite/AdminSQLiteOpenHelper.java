package com.example.sqlite;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//Objetivo de la clase es administrar la base de datos, Se heredan los atributos y métodos de la clase SQLiteOpenHelper
public class AdminSQLiteOpenHelper extends  SQLiteOpenHelper{
    //Agregar el constructor
    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    //Se sobreescribe los dos méodos Oncreate y onUpgrade
    @Override
    //Nombre de la base del string de creación de la base de  datos BaseDatos - Instancia de la base de datos
    public void onCreate(SQLiteDatabase BaseDatos) {
        //creación de la tabla pagos para la base de datos formada por codigo, motivo y valor
        BaseDatos.execSQL("create table pagos(codigo int primary key, motivo text, valor real,materias int,nivel int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

}
