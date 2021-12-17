package com.example.easybuy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class caricamento extends AppCompatActivity {

    private GestisciDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_caricamento);
        db = new GestisciDB(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!db.ifLogin()) {
                    Intent i = new Intent(caricamento.this, login.class); //Apre l'activity login
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(caricamento.this, home.class); //Apre l'activity home
                    startActivity(i);
                    finish();
                }


                finish();

            }
        }, 1500);
    }
}
