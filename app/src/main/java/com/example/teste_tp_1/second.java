package com.example.teste_tp_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.teste_tp_1.adapters.CustomArrayAdapter;
import com.example.teste_tp_1.db.Contrato;
import com.example.teste_tp_1.db.DB;
import com.example.teste_tp_1.entities.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class second extends AppCompatActivity implements SensorEventListener {

    DB mDbHelper;
    SQLiteDatabase db;
    Cursor c, cursor;
    ListView listView;
    SimpleCursorAdapter adapter;
    SharedPreferences sharedPreferences;
    String user_name;
    Intent login;
    int id_user;
    Integer idperson;
    //Iniciação do array e do request code
    private int REQUEST_CODE_OP = 1;

    private SensorManager mSensorManager;
    private Sensor mProximity;


    private static final int SENSOR_SENSITIVITY = 4;

    ArrayList<Person> arrayPerson = new ArrayList<>(); //inicialização do arrayPerson

    //Defenir o layout da lista e da activity_Second e ativação de menu contextual
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_second);
        registerForContextMenu(findViewById(R.id.lista));
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mDbHelper = new DB(this);
        db = mDbHelper.getReadableDatabase();
        listView = (ListView) findViewById(R.id.lista);


        sharedPreferences = getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
        user_name = sharedPreferences.getString("NAME", "DEFAULT_NAME");
        id_user = sharedPreferences.getInt("IDUSER", -1);

        //Passar da classe second para a class ver para ver os contatos
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/*
                c.moveToPosition(position);
                int id_person = c.getInt(c.getColumnIndex(Contrato.Person._ID));


 */
                Person p = arrayPerson.get(position);
                idperson = p.getId();
                Intent intent = new Intent(second.this, Ver.class);
                intent.putExtra("ver", idperson);
                startActivity(intent);


            }
        });

    }


    //Atualizar a lista de contactos

    @Override
    protected void onResume() {


        super.onResume();


        if (!arrayPerson.isEmpty()) {
            arrayPerson.clear();
        }
/*
        mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);//registo do sensor
        c = db.rawQuery("select * from " + Contrato.Person.TABLE_NAME + " where " + Contrato.Person.COLUMN_ID_USER + " = ?", new String[]{id_user + ""});//query que retira da tabla person todos os contactos introduzidos por aquele id
        //c=db.query(false, Contrato.Person.TABLE_NAME,Contrato.Person.PROJECTION,null,null,null,null,null,null);
        adapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item, c, new String[]{Contrato.Person.COLUMN_NAME, Contrato.Person.COLUMN_SURNAME}, new int[]{android.R.id.text1, android.R.id.text2}, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);//imprime nome e apelido
        listView.setAdapter(adapter);//lista os contactos


 */
        String url = "http://bdias.000webhostapp.com/myslim/api/contactos/" + id_user;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray arr = response.getJSONArray("DATA");
                    for (int i = 0; i < arr.length(); i++) {

                        JSONObject obj = arr.getJSONObject(i);

                        Person p = new Person(obj.getInt("id"), obj.getString("nome"), obj.getString("apelido"), obj.getString("morada"), obj.getString("profissao"),
                                obj.getString("genero"), obj.getString("codigopostal"), obj.getInt("idade"), obj.getInt("telemovel"), obj.getInt("user_id"), obj.getInt("pais_id"));
                        arrayPerson.add(p);

                    }
                    CustomArrayAdapter itemsAdapter = new CustomArrayAdapter(second.this, arrayPerson);
                    ((ListView) findViewById(R.id.lista)).setAdapter(itemsAdapter);
                } catch (JSONException e) {
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(second.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });


        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
/*
    public void click (View v){



           String url = "https://bdias.000webhostapp.com/myslim/api/contacto";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               try {


                   String nome = response.getString("nome");
                   String apelido=response.getString("apelido");
                   String morada=response.getString("morada");
                   String profissao=data.getStringExtra(Utils.PARAM_PROFESSION);
                   String genero=data.getStringExtra(Utils.PARAM_GENDER);
                   String codigo=data.getStringExtra(Utils.PARAM_NUMBER);
                   String idade=data.getStringExtra(Utils.PARAM_AGE);
                   String numero=data.getStringExtra(Utils.PARAM_PHONE);
               } catch (JSONException ex) { }


            }
        } {
        })


    }

 */


    //Criação de um menu contextual que irá permitir um long click para editar e remover contacto
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, view, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cont_1, menu);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_opt, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.Logout_button:
                login = new Intent(this, LoginActivity.class);
                final SharedPreferences sharedPreferences = getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
                sharedPreferences.edit().putBoolean("ISLOGGEDIN", false).commit();
                startActivity(login);
                finish();
                break;

            case R.id.femC:
                filtroFem(id_user);
                break;

            case R.id.malC:

                filtroMasc(id_user);

                break;

            case R.id.allC:
               allCon(id_user);

                break;

            case R.id.btn1:
                Intent i = new Intent(second.this, MainActivity.class);
                startActivityForResult(i, REQUEST_CODE_OP);
                break;

            case R.id.idade:
                c = db.rawQuery("select * from " + Contrato.Person.TABLE_NAME + " where " + Contrato.Person.COLUMN_ID_USER + " = ?" + " and " + Contrato.Person.COLUMN_AGE + " < 20", new String[]{id_user + ""});
                //Atualizar lista
                adapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item, c, new String[]{Contrato.Person.COLUMN_NAME, Contrato.Person.COLUMN_SURNAME}, new int[]{android.R.id.text1, android.R.id.text2}, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                listView.setAdapter(adapter);
                break;
            case R.id.nomes:
                EditBoxs(id_user);

            default:
                return super.onOptionsItemSelected(item);


        }

        return true;


    }

 public void allCon(int id_user) {


     if (!arrayPerson.isEmpty()) {
         arrayPerson.clear();
     }


     String urlallc = "http://bdias.000webhostapp.com/myslim/api/contactos/" + id_user;

     JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlallc, null, new Response.Listener<JSONObject>() {
         @Override
         public void onResponse(JSONObject response) {
             try {

                 JSONArray arr = response.getJSONArray("DATA");
                 for (int i = 0; i < arr.length(); i++) {

                     JSONObject obj = arr.getJSONObject(i);

                     Person p = new Person(obj.getInt("id"), obj.getString("nome"), obj.getString("apelido"), obj.getString("morada"), obj.getString("profissao"),
                             obj.getString("genero"), obj.getString("codigopostal"), obj.getInt("idade"), obj.getInt("telemovel"), obj.getInt("user_id"), obj.getInt("pais_id"));
                     arrayPerson.add(p);

                 }
                 CustomArrayAdapter itemsAdapter = new CustomArrayAdapter(second.this, arrayPerson);
                 ((ListView) findViewById(R.id.lista)).setAdapter(itemsAdapter);
             } catch (JSONException e) {
             }

         }
     }, new Response.ErrorListener() {
         @Override
         public void onErrorResponse(VolleyError error) {
             Toast.makeText(second.this, error.getMessage(), Toast.LENGTH_SHORT).show();
         }


     });


     MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
 }



public void filtroMasc(int id_user){


    if (!arrayPerson.isEmpty()) {
        arrayPerson.clear();
    }
    String urlmasc = "http://bdias.000webhostapp.com/myslim/api/ordemmasc/" + id_user;

    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlmasc, null, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            try {

                JSONArray arr = response.getJSONArray("DATA");
                for (int i = 0; i < arr.length(); i++) {

                    JSONObject obj = arr.getJSONObject(i);

                    Person p = new Person(obj.getInt("id"), obj.getString("nome"), obj.getString("apelido"), obj.getString("morada"), obj.getString("profissao"),
                            obj.getString("genero"), obj.getString("codigopostal"), obj.getInt("idade"), obj.getInt("telemovel"), obj.getInt("user_id"), obj.getInt("pais_id"));
                    arrayPerson.add(p);

                }
                CustomArrayAdapter itemsAdapter = new CustomArrayAdapter(second.this, arrayPerson);
                ((ListView) findViewById(R.id.lista)).setAdapter(itemsAdapter);
            } catch (JSONException e) {
            }

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(second.this, error.getMessage(), Toast.LENGTH_SHORT).show();
        }


    });


    MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

}




    public void filtroFem(int id_user){


        if (!arrayPerson.isEmpty()) {
            arrayPerson.clear();
        }



        String url = "http://bdias.000webhostapp.com/myslim/api/ordemfem/" + id_user;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray arr = response.getJSONArray("DATA");
                    for (int i = 0; i < arr.length(); i++) {

                        JSONObject obj = arr.getJSONObject(i);

                        Person p = new Person(obj.getInt("id"), obj.getString("nome"), obj.getString("apelido"), obj.getString("morada"), obj.getString("profissao"),
                                obj.getString("genero"), obj.getString("codigopostal"), obj.getInt("idade"), obj.getInt("telemovel"), obj.getInt("user_id"), obj.getInt("pais_id"));
                        arrayPerson.add(p);

                    }
                    CustomArrayAdapter itemsAdapter = new CustomArrayAdapter(second.this, arrayPerson);
                    ((ListView) findViewById(R.id.lista)).setAdapter(itemsAdapter);
                } catch (JSONException e) {
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(second.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });


        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);



    }

    //tomada de decisões mediante a opção selecionada pelo o user
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        final Person p = arrayPerson.get(index);
         final int id_P = p.getId();
//        final int id_person = c.getInt(c.getColumnIndex(Contrato.Person._ID));
        switch (item.getItemId()) {

            //Se o user pretender editar invoca o metodo EditBox
            case R.id.edit:
                EditBox(id_P);
                return true;
            //Caso o user queira remover cria um popup a perguntar se pretende mesmo remover o contacto
            case R.id.remove:
                removeUser(id_P);

            default:
                return super.onContextItemSelected(item);

        }
    }

    //metodo que permite editar um contacto

    public void removeUser( final int id_P) {

        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.warning2))
                .setMessage(getString(R.string.alert))

                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(second.this, getString(R.string.removed), Toast.LENGTH_SHORT).show();

                        String url = "http://bdias.000webhostapp.com/myslim/api/contactor/" + id_P;


                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            Toast.makeText(second.this, "xau laura", Toast.LENGTH_SHORT).show();//imprime um toas a dizer que deve preencher todos os campos


                                        } catch (Exception e) {
                                        }

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(second.this, "não deu primo", Toast.LENGTH_SHORT).show();//imprime um toas a dizer que deve preencher todos os campos

                                    }
                                });

                        MySingleton.getInstance(second.this).addToRequestQueue(jsonObjectRequest);
                        onResume();

                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();

    }


    public void EditBox(final int id_P) {
        final Dialog dialog = new Dialog(second.this);//criação de um caixa de texto
        dialog.setContentView(R.layout.edit_box);//a caixa de dialogo vai buscar o conteudo do layout da edit box
        TextView txtMessage = (TextView) dialog.findViewById(R.id.txtmessage);//invoca a uma text view que irá imprimir uma mensgem
        txtMessage.setText(getResources().getString(R.string.editcontact));//Imprime uma string declarada no ficheiro string.xml
        //declaração de todas as EditText presentes no layout
        final EditText Nome = (EditText) dialog.findViewById(R.id.nome);
        final EditText Apelido = (EditText) dialog.findViewById(R.id.apelido);
        final EditText Morada = (EditText) dialog.findViewById(R.id.morada);
        final EditText Profissao = (EditText) dialog.findViewById(R.id.profissao);
        final EditText Genero = (EditText) dialog.findViewById(R.id.genero);
        final EditText Codigo = (EditText) dialog.findViewById(R.id.postaladdress);
        final EditText Idade = (EditText) dialog.findViewById(R.id.idade);
        final EditText Numero = (EditText) dialog.findViewById(R.id.numero);

        //recebe da classe person os parametros a baixo e imprime nas edit text


        String url="http://bdias.000webhostapp.com/myslim/api/contacto/"+id_P;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    Nome.setText(response.getString("nome"));
                    Apelido.setText(response.getString("apelido"));
                    Morada.setText(response.getString("morada"));
                    Profissao.setText(response.getString("profissao"));
                    Genero.setText(response.getString("genero"));
                    Codigo.setText(response.getString("codigopostal"));
                    Idade.setText(response.getString("idade"));
                    Numero.setText(response.getString("telemovel"));


                } catch (JSONException e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(second.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Erro", error.toString());
            }
        });
        MySingleton.getInstance(second.this).addToRequestQueue(jsonObjectRequest);


          /*
        cursor.moveToFirst();
        Nome.setText(cursor.getString(cursor.getColumnIndexOrThrow(Contrato.Person.COLUMN_NAME)));
        Apelido.setText(cursor.getString(cursor.getColumnIndexOrThrow(Contrato.Person.COLUMN_SURNAME)));
        Morada.setText(cursor.getString(cursor.getColumnIndexOrThrow(Contrato.Person.COLUMN_ADDRESS)));
        Profissao.setText(cursor.getString(cursor.getColumnIndexOrThrow(Contrato.Person.COLUMN_PROFESSION)));
        Genero.setText(cursor.getString(cursor.getColumnIndexOrThrow(Contrato.Person.COLUMN_GENDER)));
        Codigo.setText(cursor.getString(cursor.getColumnIndexOrThrow(Contrato.Person.COLUMN_POSTALCODE)));
        Idade.setText(cursor.getString(cursor.getColumnIndexOrThrow(Contrato.Person.COLUMN_AGE)));
        Numero.setText(cursor.getString(cursor.getColumnIndexOrThrow(Contrato.Person.COLUMN_NUMBER)));
        cursor.close();


        */




        Button bt = (Button) dialog.findViewById(R.id.btdone);//declaração de um para guardar as alterações
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Nome.getText().toString().equals("") || Apelido.getText().toString().equals("") || Profissao.getText().toString().equals("")) //se os parametos estiverem vazios
                {
                    Toast.makeText(second.this, getResources().getString(R.string.introduce), Toast.LENGTH_SHORT).show();//imprime um toas a dizer que deve preencher todos os campos
                } else {

                    Integer idade = null;
                    if (!Idade.getText().toString().isEmpty()) {

                        idade = Integer.parseInt(Idade.getText().toString());
                    }

                    String url = "http://bdias.000webhostapp.com/myslim/api/contactosu/"+ id_P;
                    Map<String, String> jsonParams = new HashMap<String, String>();

                    jsonParams.put("nome", Nome.getText().toString());
                    jsonParams.put("apelido", Apelido.getText().toString());
                    jsonParams.put("morada", Morada.getText().toString());
                    jsonParams.put("profissao", Profissao.getText().toString());
                    jsonParams.put("genero",Genero.getText().toString());
                    jsonParams.put("codigopostal",Codigo.getText().toString());
                    jsonParams.put("idade",idade.toString());
                    //jsonParams.put("pais_id","2");






                    JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                if (response.getBoolean("status")) {

                                    Toast.makeText(second.this, response.getString("MSG"), Toast.LENGTH_LONG).show();


                                } else {
                                    Toast.makeText(second.this, response.getString("MSG"), Toast.LENGTH_LONG).show();

                                }

                            } catch (JSONException ex) {
                            }


                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(second.this, error.getMessage(), Toast.LENGTH_LONG).show();

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


                    MySingleton.getInstance(second.this).addToRequestQueue(postRequest);

                }

                    onResume();// atualiza a lista
                    dialog.dismiss();
                    Toast.makeText(second.this, getResources().getString(R.string.contacedit), Toast.LENGTH_SHORT).show();//imprime toast a dizer que o contacto foi editado com sucesso

            }

        });
        dialog.show();
    }






    @Override
    protected void onPause() {
        super.onPause();

        mSensorManager.unregisterListener((SensorEventListener) this);
    }

    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            //Toast.makeText(this, ""+event.values[0],Toast.LENGTH_SHORT).show();
            if (event.values[0] >= -SENSOR_SENSITIVITY && event.values[0] <= SENSOR_SENSITIVITY) {
                //Toast.makeText(this, "near", Toast.LENGTH_SHORT).show();

                //Listar os ultimos 10 contactos inseridos

                c = db.query(false, Contrato.Person.TABLE_NAME, Contrato.Person.PROJECTION, Contrato.Person.COLUMN_ID_USER + " = ?", new String[]{id_user + ""}, null, null,
                        Contrato.Person._ID + " DESC", "10");

                adapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item, c, new String[]{Contrato.Person.COLUMN_NAME, Contrato.Person.COLUMN_SURNAME}, new int[]{android.R.id.text1, android.R.id.text2}, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

                listView.setAdapter(adapter);


            } else {
                //Toast.makeText(this, "far", Toast.LENGTH_SHORT).show();
            }
        }


    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    public void EditBoxs(final int id_user) {
        final Dialog dialog = new Dialog(second.this);//criação de um caixa de texto
        dialog.setContentView(R.layout.editsbox);//a caixa de dialogo vai buscar o conteudo do layout da edit box
        TextView txtMessage = (TextView) dialog.findViewById(R.id.txtmessage);//invoca a uma text view que irá imprimir uma mensgem
        txtMessage.setText(getResources().getString(R.string.findS));//Imprime uma string declarada no ficheiro string.xml
        //declaração de todas as EditText presentes no layout


        final EditText Nome = (EditText) dialog.findViewById(R.id.nome);


        Button bt1 = (Button) dialog.findViewById(R.id.btdone1);//declaração de um para guardar as alterações

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nome=Nome.getText().toString();

                if (!arrayPerson.isEmpty()) {
                    arrayPerson.clear();
                }
                String url = "http://bdias.000webhostapp.com/myslim/api/procura/" + id_user +"&"+ nome ;

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray arr = response.getJSONArray("DATA");
                            for (int i = 0; i < arr.length(); i++) {

                                JSONObject obj = arr.getJSONObject(i);

                                Person p = new Person(obj.getInt("id"), obj.getString("nome"), obj.getString("apelido"), obj.getString("morada"), obj.getString("profissao"),
                                        obj.getString("genero"), obj.getString("codigopostal"), obj.getInt("idade"), obj.getInt("telemovel"), obj.getInt("user_id"), obj.getInt("pais_id"));
                                arrayPerson.add(p);

                            }
                            CustomArrayAdapter itemsAdapter = new CustomArrayAdapter(second.this, arrayPerson);
                            ((ListView) findViewById(R.id.lista)).setAdapter(itemsAdapter);
                        } catch (JSONException e) {
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(second.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                });


                MySingleton.getInstance(second.this).addToRequestQueue(jsonObjectRequest);




                dialog.dismiss();

            }
        });
        dialog.show();

    }


    }
