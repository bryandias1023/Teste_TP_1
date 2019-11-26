package com.example.teste_tp_1.db;

import android.provider.BaseColumns;

public class Contrato {

    public static final String TEXT_TYPE=" TEXT ";
    public static final String INT_TYPE=" INTEGER ";

    public static abstract class Person implements BaseColumns{


        public static final String TABLE_NAME="Person";
        public static final String COLUMN_NAME="nome";
        public static final String COLUMN_SURNAME="apelido";
        public static final String COLUMN_ADDRESS="morada";
        public static final String COLUMN_PROFESSION="profissao";
        public static final String COLUMN_GENDER="genero";
        public static final String COLUMN_POSTALCODE="codigopostal";
        public static final String COLUMN_AGE="idade";
        public static final String COLUMN_NUMBER="numero";
        public static final String COLUMN_ID_USER="id_user";

        public static final String[] PROJECTION={Person._ID,Person.COLUMN_NAME,Person.COLUMN_SURNAME,Person.COLUMN_ADDRESS,Person.COLUMN_PROFESSION,Person.COLUMN_GENDER,Person.COLUMN_POSTALCODE,
        Person.COLUMN_AGE,Person.COLUMN_NUMBER,Person.COLUMN_ID_USER};


        public static final String SQL_CREATE_ENTRIES=
                "CREATE TABLE " + Person.TABLE_NAME + "(" + Person._ID + INT_TYPE + " PRIMARY KEY," + Person.COLUMN_NAME + TEXT_TYPE + "," +
                        Person.COLUMN_SURNAME+TEXT_TYPE + "," + Person.COLUMN_ADDRESS + TEXT_TYPE + "," + Person.COLUMN_PROFESSION + TEXT_TYPE + "," + Person.COLUMN_GENDER + TEXT_TYPE + "," +
                        Person.COLUMN_POSTALCODE + TEXT_TYPE + "," + Person.COLUMN_AGE + INT_TYPE + "," + Person.COLUMN_NUMBER + TEXT_TYPE + "," + Person.COLUMN_ID_USER + INT_TYPE + " REFERENCES " + User.TABLE_NAME + "("+ User._ID + "));";


        public static final String SQL_DROP_ENTRIES = " DROP TABLE " + Person.TABLE_NAME + ";";


    }


    //public static abstract class fazer tabela de user

    public static abstract class User implements BaseColumns{

        public static final String TABLE_NAME="User";
        public static final String COLUMN_EMAIL="email";
        public static final String COLUMN_PASSWD="passwd";

        public static final String[] PROJECTION={User._ID,User.COLUMN_EMAIL,User.COLUMN_PASSWD};


        public static final String SQL_CREATE_ENTRIES=
                "CREATE TABLE " + User.TABLE_NAME + "(" + User._ID + INT_TYPE + " PRIMARY KEY," + User.COLUMN_EMAIL + TEXT_TYPE + "," +
                        User.COLUMN_PASSWD+TEXT_TYPE + ");";



        public static final String SQL_DROP_ENTRIES = " DROP TABLE " + User.TABLE_NAME + ";";

    }





}
