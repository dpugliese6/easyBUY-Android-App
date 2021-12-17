package com.example.easybuy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.List;

public class confronta extends AppCompatActivity {

    private GestisciDB db;

    private ListView lista_activity;
    private TextView nome, marchio, categoria, descrizione;
    private ImageView immagine;
    private String dacercare,cat,piva;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //costruttore della classe AppCompatActivity
        setContentView(R.layout.layout_compara); //carica l'interfaccia grafica

        db = new GestisciDB(this);
        Bundle datipassati = getIntent().getExtras();
        dacercare=datipassati.getString("cercato");
        piva=datipassati.getString("piva");
        cat=datipassati.getString("categoria");
        String ean = datipassati.getString("ean");

        prodotto_dato p = db.getProdottidaEan(ean);
        immagine = (ImageView) findViewById(R.id.immagine_compara);
        new download(immagine).execute(new String[]{"prodotto/" + p.getImmagine()});

        nome = (TextView) findViewById(R.id.nome_compara);
        nome.setText(p.getNome());
        marchio = (TextView) findViewById(R.id.marchio_compara);
        marchio.setText(p.getMarchio());
        categoria = (TextView) findViewById(R.id.categoria_compara);
        categoria.setText(p.getCategoria());
        descrizione = (TextView) findViewById(R.id.descrizione_compara);
        descrizione.setText(p.getDescrizione());

        lista_activity = (ListView) findViewById(R.id.lista_compara);

        List lista = db.getSupermercatidaEan(ean);

        if (lista.size() != 0) {

            confronta_adapter adapter = new confronta_adapter(this, R.layout.riga_compara, lista);
            lista_activity.setAdapter(adapter);

        } else {

            List objects = new LinkedList();
            objects.add("un elemento giusto per fare uscire una riga");
            non_ci_sono_elementi_adapter a = new non_ci_sono_elementi_adapter(this, R.layout.non_ci_sono_supermercati, objects);
            lista_activity.setAdapter(a);

        }

    }

    @Override
    public void onBackPressed() {
        if (piva == null) {
            Intent intent = new Intent(confronta.this, risultati.class);
            intent.putExtra("cercato", dacercare);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(confronta.this, supermercato_prodotti.class);
            intent.putExtra("piva", piva);
            intent.putExtra("categoria", cat);
            startActivity(intent);
            finish();
        }
    }
}
