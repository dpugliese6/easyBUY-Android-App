package com.example.easybuy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.List;

public class prodotti_lista extends AppCompatActivity { //activity dove verranno mostrati i prodotti delle liste

    private GestisciDB db;
    private ListView lista_activity;

    private Bundle bundle;
    private TextView titolo;
    private String id_lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //costruttore della classe AppCompatActivity
        setContentView(R.layout.layout_prodotti_liste); //carica l'interfaccia grafica

        db = new GestisciDB(this);

        bundle = getIntent().getExtras();
        id_lista = bundle.getString("lista_id");

        titolo = (TextView) findViewById(R.id.titolo_prodotti_lista);
        titolo.setText(bundle.getString("lista_nome"));
        lista_activity = (ListView) findViewById(R.id.lista_prodotti_lista);

        List lista = db.getProdottiFromLista(id_lista);//in questa lista andranno i prodotti della lista

        if (lista.size() != 0) {

            prodotti_liste_adapter adapter = new prodotti_liste_adapter(this, R.layout.prodotti_liste, lista);
            lista_activity.setAdapter(adapter);

        } else {

            List objects = new LinkedList();
            objects.add("un elemento giusto per fare scire una riga");
            non_ci_sono_elementi_adapter a = new non_ci_sono_elementi_adapter(this, R.layout.non_ci_sono_prodotti, objects);
            lista_activity.setAdapter(a);
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(prodotti_lista.this, liste.class);
        startActivity(intent);
        finish();
    }
}

