package com.example.easybuy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class risultati extends AppCompatActivity {

    private GestisciDB db;
    private ListView lista_activity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //deve stare sempre
        setContentView(R.layout.layout_risultati); //imposta il layout

        db = new GestisciDB(this);
        lista_activity = (ListView) findViewById(R.id.lista_risultati);

        Bundle datipassati = getIntent().getExtras();
        String dacercare = datipassati.getString("cercato");

        if (!(dacercare.equals("£"))) {

            ArrayList<String> vettoreOrdinato = new ArrayList<>();
            ArrayList<String> vettoreOrdinatoEan = new ArrayList<>();
            db.ricerca(dacercare, vettoreOrdinato, vettoreOrdinatoEan);
            if (vettoreOrdinatoEan.size() > 0) {
                List lista = new LinkedList();
                prodotto_dato p;
                for (int i = 0; i < vettoreOrdinatoEan.size(); i++) {
                    p = db.getProdottidaEan(vettoreOrdinatoEan.get(i));
                    lista.add(p);
                }

                risultati_adapter adapter = new risultati_adapter(this, R.layout.riga_risultati, lista,dacercare);
                lista_activity.setAdapter(adapter);

            } else {

                List objects = new LinkedList();
                objects.add("un elemento giusto per fare uscire una riga");
                non_ci_sono_elementi_adapter a = new non_ci_sono_elementi_adapter(this, R.layout.non_ci_sono_risultati, objects);
                lista_activity.setAdapter(a);

            }
        } else {
            List objects = new LinkedList();
            objects.add("un elemento giusto per fare uscire una riga");
            non_ci_sono_elementi_adapter a = new non_ci_sono_elementi_adapter(this, R.layout.non_ci_sono_elementi, objects);
            lista_activity.setAdapter(a);


        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(risultati.this, home.class);
        startActivity(intent);
        finish();
    }
}
