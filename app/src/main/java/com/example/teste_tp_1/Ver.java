package com.example.teste_tp_1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teste_tp_1.db.Contrato;
import com.example.teste_tp_1.db.DB;
import com.example.teste_tp_1.entities.Person;

import java.io.Serializable;

public class Ver extends AppCompatActivity implements Serializable {
    int id;
    DB mDbHelper;
    Cursor cursor;
    SQLiteDatabase db;


    TextView tx1, tx2, tx3, tx4, tx5, tx6, tx7, tx8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_contato);
        mDbHelper=new DB(this);
        db= mDbHelper.getReadableDatabase();
        //decleração das texview
        tx1=findViewById(R.id.name);
        tx2=findViewById(R.id.surname);
        tx3=findViewById(R.id.address);
        tx4=findViewById(R.id.profession);
        tx5=findViewById(R.id.gender);
        tx6=findViewById(R.id.code);
        tx7=findViewById(R.id.age);
        tx8=findViewById(R.id.numberphone);
        //mediante o id passado mostra o contacto
        id=getIntent().getExtras().getInt("ver");
        cursor=db.query(false, Contrato.Person.TABLE_NAME,Contrato.Person.PROJECTION,Contrato.Person._ID + " = ?", new String[]{id+""},null,null,null,null);

        cursor.moveToFirst();
        tx1.setText(cursor.getString(cursor.getColumnIndexOrThrow(Contrato.Person.COLUMN_NAME)));
        tx2.setText(cursor.getString(cursor.getColumnIndexOrThrow(Contrato.Person.COLUMN_SURNAME)));
        tx3.setText(cursor.getString(cursor.getColumnIndexOrThrow(Contrato.Person.COLUMN_ADDRESS)));
        tx4.setText(cursor.getString(cursor.getColumnIndexOrThrow(Contrato.Person.COLUMN_PROFESSION)));
        tx5.setText(cursor.getString(cursor.getColumnIndexOrThrow(Contrato.Person.COLUMN_GENDER)));
        tx6.setText(cursor.getString(cursor.getColumnIndexOrThrow(Contrato.Person.COLUMN_POSTALCODE)));
        tx7.setText(cursor.getString(cursor.getColumnIndexOrThrow(Contrato.Person.COLUMN_AGE)));
        tx8.setText(cursor.getString(cursor.getColumnIndexOrThrow(Contrato.Person.COLUMN_NUMBER)));

        cursor.close();


    }

}