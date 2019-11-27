package com.example.teste_tp_1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.teste_tp_1.db.Contrato;
import com.example.teste_tp_1.db.DB;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
Encrypt encry=new Encrypt();
    DB mDbHelper;
    SQLiteDatabase db;
    Cursor c,c_passwd;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mDbHelper=new DB(this);
        db= mDbHelper.getReadableDatabase();
        sharedPreferences=getSharedPreferences("USER_CREDENTIALS",MODE_PRIVATE);//invocar credencias do user logado
        final Boolean isloggedin=sharedPreferences.getBoolean("ISLOGGEDIN",false);
        if(isloggedin)//se estiver logado
        {
            Intent main = new Intent(LoginActivity.this, second.class);// passa desta ativiade para a second
            startActivity(main);
        }
        final String required_email=sharedPreferences.getString("EMAIL","DEFAULT_EMAIL");//passar o email para sharedPreferences
        final String required_password=sharedPreferences.getString("PASSWORD","DEFAULT_PASSWORD");//passar a password para sharedPreferences
        final EditText email_field=(EditText)findViewById(R.id.login_email);// declaração de editText email
        final EditText password_field=(EditText)findViewById(R.id.login_password);// declaração de editText password
        Button login=(Button)findViewById(R.id.login_button);//botão login
        Button register=(Button)findViewById(R.id.register_button);//botão registo



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //guarda valores introduzidos pelo o user em variaveis do tipo string
                final String email = email_field.getText().toString();
                final String password = password_field.getText().toString();
                String passwd=null;
                try {
                    passwd = encry.encryptar(password);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String url = "http://bdias.000webhostapp.com/myslim/api/user/" + email + "&" + passwd;


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            sharedPreferences=getSharedPreferences("USER_CREDENTIALS",MODE_PRIVATE);//invocar credencias do user logado
                            SharedPreferences.Editor editor = sharedPreferences.edit();//inicialização
                            editor.putInt("IDUSER", response.getInt("id"));
                            editor.putString("EMAIL", email);//passar dados por sharedpreferences
                            editor.putString("PASSWD", password);
                            editor.putBoolean("ISLOGGEDIN", true);
                            editor.commit();

                            Intent main = new Intent(LoginActivity.this, second.class);
                            startActivity(main);


                        } catch (JSONException e) {
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "login errado", Toast.LENGTH_SHORT).show();
                    }
                });
                MySingleton.getInstance(LoginActivity.this).addToRequestQueue(jsonObjectRequest);

            }

            });


/*

                //se existir guarda nas strings o email e a password nas strings
                if(c!=null&&c.getCount() > 0){
                    c.moveToFirst();
                    r_email=c.getString(c.getColumnIndexOrThrow(Contrato.User.COLUMN_EMAIL));
                    r_passwd=c.getString(c.getColumnIndexOrThrow(Contrato.User.COLUMN_PASSWD));
                }
                // compara a password introduzido com a passowrd guardada na base de dados se for igual guarda em sharedPreferences e passa para a ativadade second


                if(email.equals(r_email)&&password.equals(r_passwd)) {
                    SharedPreferences sharedPreferences=getSharedPreferences("USER_CREDENTIALS",MODE_PRIVATE);//"guarda"as credenciais do user
                    SharedPreferences.Editor editor=sharedPreferences.edit();//inicialização
                    int idu = c.getInt(c.getColumnIndexOrThrow(Contrato.User._ID));
                    editor.putInt("IDUSER",idu);
                    editor.putString("EMAIL",email);//passar dados por sharedpreferences
                    editor.putString("PASSWD",password);
                    editor.putBoolean("ISLOGGEDIN",true);
                    editor.commit();
                    Intent main = new Intent(LoginActivity.this, second.class);
                    startActivity(main);
                }
                //caso contrario lança um aviso
                else
                {
                    Toast.makeText(LoginActivity.this,getResources().getString(R.string.warning3),Toast.LENGTH_LONG).show();
                }
            }
        });

 */
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(register);
                finish();
            }
        });








    }









}