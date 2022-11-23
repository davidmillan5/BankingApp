package com.example.sistemabancario;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class MainActivity extends AppCompatActivity {


    DataBase sohClients = new DataBase(this,"dbclientes",null,1);





    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText idclient = findViewById(R.id.etidclient);
        EditText passwordclient = findViewById(R.id.etclientpassword);
        Button btncontinue = findViewById(R.id.btncontinue);
        Button btnregist = findViewById(R.id.btnregister);




        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button btnregister = findViewById(R.id.btnregister);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Activity change
                Intent i = new Intent(MainActivity.this,Registration.class);
                startActivity(i);

            }
        });


        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!idclient.getText().toString().isEmpty() && !passwordclient.getText().toString().isEmpty()){

            SQLiteDatabase dbr = sohClients.getReadableDatabase();
            String query = "SELECT ident FROM clients WHERE ident= '"+idclient.getText().toString() + "'";
            Cursor cursorSearch = dbr.rawQuery(query,null);
            if(cursorSearch.moveToFirst()){
                Intent iclients = new Intent(getApplicationContext(),CreateAccount.class);
                iclients.putExtra("midclient",idclient.getText().toString());
                startActivity(iclients);

            }else{
                Toast.makeText(getApplicationContext(),"Id Cliente no existe, por favor registrarse",Toast.LENGTH_SHORT).show();
                }
                dbr.close();

                }else{
                    Toast.makeText(getApplicationContext(),
                            "Debe ingresar los datos para realizar la consulta",Toast.LENGTH_SHORT).show();
                }


            }
        });



    }
}