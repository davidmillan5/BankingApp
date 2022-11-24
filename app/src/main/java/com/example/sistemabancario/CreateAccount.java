package com.example.sistemabancario;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class CreateAccount extends AppCompatActivity {


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        TextView ident = findViewById(R.id.tvidclient);
        EditText accountnumber = findViewById(R.id.etaccountnumber);
        EditText date = findViewById(R.id.etdatecreation);
        EditText deposit = findViewById(R.id.etinitialdeposit);
        Button btnsave = findViewById(R.id.btnsaveaccount);

        ident.setText(getIntent().getStringExtra("midclient"));


        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer depositvalue = Integer.parseInt(deposit.getText().toString());

                if(!date.getText().toString().isEmpty() && !deposit.getText().toString().isEmpty()
                        && !accountnumber.getText().toString().isEmpty()){

                if(depositvalue>= 1000000 && depositvalue <= 50000000){
                    DataBase sohcreation = new DataBase(getApplicationContext(),"dbclientes",null,1);
                    SQLiteDatabase dbw = sohcreation.getWritableDatabase();
                    ContentValues cvaccount = new ContentValues();
                    cvaccount.put("accountnr",accountnumber.getText().toString());
                    cvaccount.put("date",date.getText().toString());
                    cvaccount.put("statement",deposit.getText().toString());
                    dbw.insert("accounts",null,cvaccount);
                    dbw.execSQL("UPDATE accounts SET statement = statement + '"+depositvalue+"' WHERE ident = '"+ident.getText().toString()+"'");
                    dbw.close();
                    Toast.makeText(getApplicationContext(),"Consignacion registrada correctament...",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),
                            "El valor de la transaccion debe estar entre 2 millones y 50 millones ...",Toast.LENGTH_SHORT).show();
                }

                }else{
                    Toast.makeText(getApplicationContext(),"Ingrese fecha, hora y valor de consignacion ...",Toast.LENGTH_SHORT).show();
                }


            }
        });


        Button btntransactions = findViewById(R.id.btntransactions);

        btntransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateAccount.this,Transaction.class);
                startActivity(i);
            }
        });




    }


}
