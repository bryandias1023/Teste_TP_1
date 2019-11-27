package com.example.teste_tp_1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.teste_tp_1.db.Contrato;
import com.example.teste_tp_1.db.DB;
import com.example.teste_tp_1.entities.Person;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Ver extends AppCompatActivity implements Serializable {
    int id;
    DB mDbHelper;
    Cursor cursor;
    SQLiteDatabase db;


    TextView tx1, tx2, tx3, tx4, tx5, tx6, tx7, tx8,tx9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_contato);
        mDbHelper=new DB(this);
        db= mDbHelper.getReadableDatabase();

        //Toast.makeText(Ver.this, id, Toast.LENGTH_SHORT).show();

        //decleração das texview
        tx1=findViewById(R.id.name);
        tx2=findViewById(R.id.surname);
        tx3=findViewById(R.id.address);
        tx4=findViewById(R.id.profession);
        tx5=findViewById(R.id.gender);
        tx6=findViewById(R.id.code);
        tx7=findViewById(R.id.age);
        tx8=findViewById(R.id.numberphone);
        tx9=findViewById(R.id.pais);
        //mediante o id passado mostra o contacto
        id=getIntent().getExtras().getInt("ver");
        String pri= String.valueOf(id);
        Toast.makeText(Ver.this, pri, Toast.LENGTH_SHORT).show();


        String url="http://bdias.000webhostapp.com/myslim/api/contacto/" +id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    tx1.setText(response.getString("nome"));
                    tx2.setText(response.getString("apelido"));
                    tx3.setText(response.getString("morada"));
                    tx4.setText(response.getString("profissao"));
                    tx5.setText(response.getString("genero"));
                    tx6.setText(response.getString("codigopostal"));
                    tx7.setText(response.getString("idade"));
                    tx8.setText(response.getString("telemovel"));
                    tx9.setText(response.getString("pais_id"));


                } catch (JSONException e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Ver.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Erro", error.toString());
            }
        });
            MySingleton.getInstance(Ver.this).addToRequestQueue(jsonObjectRequest);

    }


    }

