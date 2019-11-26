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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.teste_tp_1.db.Contrato;
import com.example.teste_tp_1.db.DB;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    DB mDbHelper;
    Cursor c;

    String genero;
    int paisd;
    String paiss;
    //declaração de todas as edit text existentes no layout
    EditText Name ;
    EditText Surname;
    EditText Address;
    EditText Profession;
    EditText Code;
    EditText Age;
    EditText Number;
    Spinner gendrop;
    Spinner paisdrop;
    String nome;

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
        paisdrop = findViewById(R.id.pais);
        mDbHelper=new DB(this);
        db= mDbHelper.getReadableDatabase();
        String [] pais= new  String[]{getResources().getString(R.string.pais),getResources().getString(R.string.pais1),getResources().getString(R.string.pais2),getResources().getString(R.string.pais3)};
        String[] items = new String[]{getResources().getString(R.string.gender),getResources().getString(R.string.male), getResources().getString(R.string.fem)};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, items);
        ArrayAdapter<String> adapterp = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, pais);

        gendrop.setAdapter(adapter);
        paisdrop.setAdapter(adapterp);

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

                    genero= "";
                }
                else {
                    genero = (String) parent.getItemAtPosition(pos);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });





        paisdrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int posi, long id) {


                if(parent.getItemAtPosition(posi).equals(getResources().getString(R.string.pais1))){

                    paisd=1;
                }

               else if(parent.getItemAtPosition(posi).equals(getResources().getString(R.string.pais2))){

                    paisd=2;
                }


               else
                {

                    paisd=3;
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });







    }






//metodo que mediante um click no botão envia a informação introduzida para a base de dados

    public void button1(View v) {

        if (Name.getText().toString().equals("") || Surname.getText().toString().equals("")) {
            Toast.makeText(MainActivity.this, getString(R.string.warning1), Toast.LENGTH_SHORT).show(); // se o  campo nome e o numero não forem preenchidos não permite a criação do contacto
        } else {
            Integer idade = null;
            if (!Age.getText().toString().isEmpty()) {

                idade = Integer.parseInt(Age.getText().toString());
            }

            String nome=Name.getText().toString();
            String apelido=Surname.getText().toString();
            Integer iduser=id_user;





            String url = "http://bdias.000webhostapp.com/myslim/api/contactosu";
            Map<String, String> jsonParams = new HashMap<String, String>();
            jsonParams.put("nome", nome);
            jsonParams.put("apelido", apelido);
            jsonParams.put("morada", Address.getText().toString());
            jsonParams.put("profissao", Profession.getText().toString());
            jsonParams.put("genero",genero);
            jsonParams.put("codigopostal",Code.getText().toString());
            jsonParams.put("idade",idade.toString());
            jsonParams.put("telemovel", Number.getText().toString());
            jsonParams.put("user_id",iduser.toString());
            jsonParams.put("pais_id",paisd+"");








            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        if (response.getBoolean("status")) {

                            Toast.makeText(MainActivity.this, response.getString("MSG"), Toast.LENGTH_LONG).show();


                        } else {
                            Toast.makeText(MainActivity.this, response.getString("MSG"), Toast.LENGTH_LONG).show();

                        }

                    } catch (JSONException ex) {
                    }


                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    headers.put("User-agent", System.getProperty("http.agent"));
                    return headers;
                }


            };


            MySingleton.getInstance(MainActivity.this).addToRequestQueue(postRequest);
            finish();
        }

    }







            /*
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





             */






}
