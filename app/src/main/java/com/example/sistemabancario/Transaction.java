package com.example.sistemabancario;

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
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Transaction extends AppCompatActivity {

    DataBase sohtransactions = new DataBase(this,"dbclientes",null,1);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        EditText accountNumber = findViewById(R.id.etaccountnumber);
        EditText datetransaction = findViewById(R.id.etdatetransaction);
        EditText time = findViewById(R.id.ettimetransaction);
        EditText transaction = findViewById(R.id.ettransactionvalue);
        EditText transactiontype = findViewById(R.id.ettransactiontype);
        Button save = findViewById(R.id.btnsaveaccount);
        Button home = findViewById(R.id.btnback);



        Button btnback = findViewById(R.id.btnback);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transaction.this,MainActivity.class);
                startActivity(i);
            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer transactiont = Integer.parseInt(transaction.getText().toString());

                if(!accountNumber.getText().toString().isEmpty() && !datetransaction.getText().toString().isEmpty()
                        && !time.getText().toString().isEmpty() && !transaction.getText().toString().isEmpty()
                    && !transactiontype.getText().toString().isEmpty()){



                    SQLiteDatabase dbr = sohtransactions.getReadableDatabase();
                    String query = "SELECT accountnr FROM accounts WHERE accountnr= '"+ accountNumber.getText().toString() +"'";
                    Cursor cursorSearch = dbr.rawQuery(query,null);

                    if(cursorSearch.moveToFirst()){

                        SQLiteDatabase dbw = sohtransactions.getWritableDatabase();
                        ContentValues cvtransaction = new ContentValues();
                        cvtransaction.put("accountnr",accountNumber.getText().toString());
                        cvtransaction.put("date",datetransaction.getText().toString());
                        cvtransaction.put("time",time.getText().toString());
                        cvtransaction.put("value",transaction.getText().toString());
                        cvtransaction.put("transtype",transactiontype.getText().toString());

                        dbw.insert("transactions",null,cvtransaction);

                        String retiro = "retiro";
                        String consignacion = "consignacion";

                        if(retiro.equals(transactiontype.getText().toString().toLowerCase())){
                        dbw.execSQL("UPDATE accounts SET statement = statement - '"+transactiont+"' WHERE accountnr ='"+accountNumber.getText().toString()+"'");
                        dbw.close();
                        Toast.makeText(getApplicationContext(),
                                "Transaccion exitosa",Toast.LENGTH_SHORT).show();

                        }else if(consignacion.equals(transactiontype.getText().toString().toLowerCase())){
                            dbw.execSQL("UPDATE accounts SET statement = statement + '"+transactiont+"' WHERE accountnr ='"+accountNumber.getText().toString()+"'");
                            dbw.close();
                            Toast.makeText(getApplicationContext(),
                                    "Transaccion exitosa",Toast.LENGTH_SHORT).show();
                        }






                    }else{
                        Toast.makeText(getApplicationContext(),
                                "Intente nuevamente con la transacción",Toast.LENGTH_SHORT).show();
                    }
                    dbr.close();
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Debe ingresar todos los datos",Toast.LENGTH_SHORT).show();
                }


            }
        });





    }


}
