package com.example.easybuy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.List;

public class scegli extends AppCompatActivity {

    private GestisciDB db;
    private ListView lista_activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //costruttore della classe AppCompatActivity
        setContentView(R.layout.layout_scegli); //carica l'interfaccia grafica
        lista_activity = (ListView) findViewById(R.id.lista_scelte);
        final Bundle bundle = getIntent().getExtras();
        db = new GestisciDB(this);

        String ean = bundle.getString("ean");
        String piva = bundle.getString("piva");

        List lista;
        if (bundle.getString("tipologia").equals("lista")) {

            lista = db.getListeFromUtente(db.getEmail());
            if (lista.size() != 0) {

                scegli_adapter adapter = new scegli_adapter(this, R.layout.riga_scelta_lista, lista, ean);
                lista_activity.setAdapter(adapter);

            } else {

                Toast.makeText(scegli.this, "Non hai ancora creato una lista...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, liste.class);
                startActivity(intent);
                finish();
            }


        } else {

            lista = db.getCarrelliFromUtente(db.getEmail());
            if (lista.size() != 0) {

                scegli_adapter adapter = new scegli_adapter(this, R.layout.riga_scelta_lista, lista, ean, piva);
                lista_activity.setAdapter(adapter);

            } else {

                Toast.makeText(scegli.this, "Non hai ancora creato un carrello...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, carrelli.class);
                startActivity(intent);
                finish();
            }
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(scegli.this, home.class);
        Toast.makeText(scegli.this, "Il prodotto non è stato aggiunto...", Toast.LENGTH_LONG).show();
        startActivity(intent);
        finish();
    }
}
