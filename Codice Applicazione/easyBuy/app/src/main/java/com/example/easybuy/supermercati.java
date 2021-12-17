package com.example.easybuy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.List;

public class supermercati extends AppCompatActivity {

    private GestisciDB db;
    private ListView lista_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //costruttore della classe AppCompatActivity
        setContentView(R.layout.layout_supermercati); //carica l'interfaccia grafica

        db = new GestisciDB(this);
        lista_activity = (ListView) findViewById(R.id.lista_supermercati);

        List lista = new LinkedList();
        lista=db.getSupermercati();
        supermercati_adapter adapter = new supermercati_adapter(this, R.layout.riga_supermercati,lista);
        lista_activity.setAdapter(adapter);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(supermercati.this, home.class);
        startActivity(intent);
        finish();
    }



}
