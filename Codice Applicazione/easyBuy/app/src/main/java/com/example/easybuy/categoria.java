package com.example.easybuy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class categoria extends AppCompatActivity {

    private TextView titolo;
    private ListView lista;
    private GestisciDB db;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //costruttore della classe AppCompatActivity
        setContentView(R.layout.layout_supermercati); //carica l'interfaccia grafica

        db= new GestisciDB(this);
        bundle =getIntent().getExtras();
        String piva=bundle.getString("piva");

        titolo=(TextView)findViewById(R.id.titolo_supermercati);
        titolo.setText("Categorie");

        lista=(ListView)findViewById(R.id.lista_supermercati);

        ArrayList<String> categorie = db.getCategorieSupermercato(piva);
        categorie.add(0,"Tutti i prodotti");

        categoria_adapter adapter = new categoria_adapter(this, R.layout.riga_categoria, categorie,piva);
        lista.setAdapter(adapter);

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(categoria.this, supermercati.class);
        startActivity(intent);
        finish();
    }



}
