package com.example.sistemabancario;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class Registration extends AppCompatActivity{


    DataBase sohClients = new DataBase(this,"dbclientes",null,1);
    String oldidclient;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        EditText ident = findViewById(R.id.etidclient);
        EditText fullname = findViewById(R.id.etfullname);
        EditText email = findViewById(R.id.etemail);
        EditText password = findViewById(R.id.etpassword);
        ImageButton btnsave = findViewById(R.id.btnsave);
        ImageButton btnsearch = findViewById(R.id.btnsearch);
        ImageButton btndelete = findViewById(R.id.btndelete);
        ImageButton btnedit = findViewById(R.id.btnedit);
        Button createbtn = findViewById(R.id.btncreateaccount);
        Button transactionbtn = findViewById(R.id.btntransaction);

        btnedit.setEnabled(false);
        btndelete.setEnabled(false);

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase dbr = sohClients.getReadableDatabase();
                String query = "SELECT ident FROM clients WHERE ident = '"+ident.getText().toString() + "'";
                Cursor cursorSearch = dbr.rawQuery(query,null);
                if(cursorSearch.moveToFirst()) {
                        SQLiteDatabase dbw = sohClients.getWritableDatabase();
                        dbw.execSQL("DELETE FROM clients WHERE ident = '" + ident.getText().toString() + "'");
                        Toast.makeText(getApplicationContext(), "Cliente eliminado exitosamente ...", Toast.LENGTH_SHORT).show();
                        ident.setText("");
                        fullname.setText("");
                        email.setText("");
                        password.setText("");
                        ident.requestFocus();
                        dbw.close();

                }else{
                    Toast.makeText(getApplicationContext(),"Id del cliente no existe ...",Toast.LENGTH_SHORT).show();
                }
                dbr.close();
            }
        });


        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase dbw = sohClients.getWritableDatabase();
                if(oldidclient.equals(ident.getText().toString())){
                    dbw.execSQL("UPDATE clients SET fullname = '"+fullname.getText().toString()+"',email='"+email.getText().toString()+"',password = '"+password.getText().toString()+"' WHERE ident = '"+oldidclient+"'");
                    Toast.makeText(getApplicationContext(),"Cliente actualizado exitosamente ...",Toast.LENGTH_SHORT).show();
                }else{
                    SQLiteDatabase dbr = sohClients.getReadableDatabase();
                    String query = "SELECT ident FROM clients WHERE ident= '"+ident.getText().toString() + "'";
                    Cursor cursorSearch = dbr.rawQuery(query,null);
                    if(!cursorSearch.moveToFirst()) {
                        dbw.execSQL("UPDATE clients SET ident ='" + ident.getText().toString() + "',fullname = '" + fullname.getText().toString() + "',email='" + email.getText().toString() + "',password = '" + password.getText().toString() + "' WHERE ident = '" + oldidclient + "'");
                        Toast.makeText(getApplicationContext(),"Cliente actualizado exitosamente ...",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Id de cliente ya asignado ...",Toast.LENGTH_SHORT).show();
                    }
                    dbr.close();
                }
                    dbw.close();
                }
        });




        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase dbr = sohClients.getReadableDatabase();
                String query = "SELECT fullname, email FROM clients WHERE ident= '"+ident.getText().toString() + "'";
                Cursor cursorSearch = dbr.rawQuery(query,null);

                if(cursorSearch.moveToFirst()){
                    fullname.setText(cursorSearch.getString(0));
                    email.setText(cursorSearch.getString(1));
                    oldidclient = ident.getText().toString();
                    btnedit.setEnabled(true);
                    btndelete.setEnabled(true);

                }else{
                    Toast.makeText(getApplicationContext(),
                            "Id de cliente inexistente",Toast.LENGTH_SHORT).show();
                }



            }
        });



        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!ident.getText().toString().isEmpty() && !fullname.getText().toString().isEmpty()
                        && !email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){

                    SQLiteDatabase dbr = sohClients.getReadableDatabase();
                    String query = "SELECT ident FROM clients WHERE ident= '"+ident.getText().toString() + "'";
                    Cursor cursorSearch = dbr.rawQuery(query,null);

                    if(!cursorSearch.moveToFirst()){

                        SQLiteDatabase dbw = sohClients.getWritableDatabase();
                        ContentValues cvclient = new ContentValues();
                        cvclient.put("ident",ident.getText().toString());
                        cvclient.put("fullname",fullname.getText().toString());
                        cvclient.put("email",email.getText().toString());
                        cvclient.put("password",password.getText().toString());

                        dbw.insert("clients",null,cvclient);
                        dbw.close();

                        Toast.makeText(getApplicationContext(),
                                "Cliente creado",Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(getApplicationContext(),
                                "Identificación esta asignada a otro cliente, Intente con otro",Toast.LENGTH_SHORT).show();
                    }
                    dbr.close();
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Debe ingresar todos los datos",Toast.LENGTH_SHORT).show();
                }


            }
        });




        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button btnrcreateaccount = findViewById(R.id.btncreateaccount);

        btnrcreateaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase dbr = sohClients.getReadableDatabase();
                String query = "SELECT ident FROM clients WHERE ident = '"+ident.getText().toString() + "'";
                Cursor cursorSearch = dbr.rawQuery(query,null);
                if(cursorSearch.moveToFirst()){
                    Intent iAccount = new Intent(getApplicationContext(),CreateAccount.class);
                    iAccount.putExtra("midclient",ident.getText().toString());
                    startActivity(iAccount);
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Id cliente no existe",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

}
