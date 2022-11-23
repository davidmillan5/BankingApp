package com.example.sistemabancario;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;


public class DataBase extends SQLiteOpenHelper{

    String tblClients = "CREATE TABLE clients(ident integer primary key,fullname text, email text,password text)";
    String tblAccount = "CREATE TABLE accounts(accountnr integer primary key, ident integer, date text, statement integer)";
    String tblTransaction ="CREATE TABLE transactions(accountnr integer, date text, time text, transtype text, value integer)";



    public DataBase(Context context,String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tblClients);
        db.execSQL(tblAccount);
        db.execSQL(tblTransaction);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE clients");
        db.execSQL(tblClients);
        db.execSQL("DROP TABLE accounts");
        db.execSQL(tblAccount);
        db.execSQL("DROP TABLE transactions");
        db.execSQL(tblTransaction);
    }
}
