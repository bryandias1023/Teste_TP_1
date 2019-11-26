package com.example.teste_tp_1;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    private static MySingleton mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private MySingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();


    }




    public static synchronized MySingleton getInstance(Context context){

        if(mInstance==null){

            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue() {

        if(mInstance == null){

            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req){ getRequestQueue().add(req);}

}
