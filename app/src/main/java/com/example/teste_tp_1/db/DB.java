package com.example.teste_tp_1.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.teste_tp_1.Utils;

public class DB extends SQLiteOpenHelper {

public static final int DATABASE_VERSION=10;
public static final String DATABASE_NAME="person.db";

   public DB(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }





    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Contrato.User.SQL_CREATE_ENTRIES);

        db.execSQL(Contrato.Person.SQL_CREATE_ENTRIES);





    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

       db.execSQL(Contrato.Person.SQL_DROP_ENTRIES);
       db.execSQL(Contrato.User.SQL_DROP_ENTRIES);
       onCreate(db);

    }

    public void onDowngrade(SQLiteDatabase db,int oldVersion,int newVersion){


       onUpgrade(db,oldVersion,newVersion);
    }
}
