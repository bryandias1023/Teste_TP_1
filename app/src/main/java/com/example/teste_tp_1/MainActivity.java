package com.example.teste_tp_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.teste_tp_1.db.Contrato;
import com.example.teste_tp_1.db.DB;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    DB mDbHelper;
    Cursor c;

    String genero;
    //declaração de todas as edit text existentes no layout
    EditText Name ;
    EditText Surname;
    EditText Address;
    EditText Profession;
    EditText Code;
    EditText Age;
    EditText Number;
    Spinner gendrop;

int id_user;
String user_name;
SharedPreferences sharedPreferences;
//declaração do layout e das edittext

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);

        //Inicialização do:spinner,base de dados...
        setContentView(R.layout.activity_main);
        gendrop = findViewById(R.id.genero);
        mDbHelper=new DB(this);
        db= mDbHelper.getReadableDatabase();

        String[] items = new String[]{getResources().getString(R.string.gender),getResources().getString(R.string.male), getResources().getString(R.string.fem)};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, items);
        gendrop.setAdapter(adapter);
        sharedPreferences=getSharedPreferences("USER_CREDENTIALS",MODE_PRIVATE);
        user_name=sharedPreferences.getString("NAME","DEFAULT_NAME");
        id_user=sharedPreferences.getInt("IDUSER",-1);



        //declaração das variaveis
        Name=(EditText) findViewById(R.id.nome);
        Surname=(EditText) findViewById(R.id.sobrenome);
        Address=(EditText) findViewById(R.id.morada);
        Profession=(EditText) findViewById(R.id.profissao);
        Code=(EditText) findViewById(R.id.codigo);
        Age=(EditText) findViewById(R.id.idade);
        Number=(EditText) findViewById(R.id.phone);

        //Spinner do gênero

        gendrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (parent.getItemAtPosition(pos).equals(getResources().getString(R.string.gender))){

                }
                else {
                    genero = (String) parent.getItemAtPosition(pos);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }


//metodo que mediante um click no botão envia a informação introduzida para a base de dados

    public void button1(View v) {

        if (Name.getText().toString().equals("") || Number.getText().toString().equals("")) {
            Toast.makeText(MainActivity.this, getString(R.string.warning1), Toast.LENGTH_SHORT).show(); // se o  campo nome e o numero não forem preenchidos não permite a criação do contacto
        } else {
                Integer idade=null;
                if(!Age.getText().toString().isEmpty())
                {

                    idade=Integer.parseInt(Age.getText().toString());
                }

            ContentValues cv= new ContentValues();
            cv.put(Contrato.Person.COLUMN_NAME,Name.getText().toString());
            cv.put(Contrato.Person.COLUMN_SURNAME,Surname.getText().toString());
            cv.put(Contrato.Person.COLUMN_ADDRESS,Address.getText().toString());
            cv.put(Contrato.Person.COLUMN_PROFESSION,Profession.getText().toString());
            cv.put(Contrato.Person.COLUMN_GENDER,genero);
            cv.put(Contrato.Person.COLUMN_POSTALCODE,Code.getText().toString());
            cv.put(Contrato.Person.COLUMN_AGE,idade);
            cv.put(Contrato.Person.COLUMN_NUMBER,Number.getText().toString());
            cv.put(Contrato.Person.COLUMN_ID_USER,id_user);
            db.insert(Contrato.Person.TABLE_NAME,null,cv);



            finish();

        }
    }



}
