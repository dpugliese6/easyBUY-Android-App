package com.example.easybuy;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.List;

public class carrelli extends AppCompatActivity {

    private GestisciDB db;
    private ListView lista_activity;
    private TextView titolo;
    private Button crea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //costruttore della classe AppCompatActivity
        setContentView(R.layout.layout_carrelli_liste); //carica l'interfaccia grafica

        db = new GestisciDB(this);
        List lista = db.getCarrelliFromUtente(db.getEmail());

        titolo = (TextView) findViewById(R.id.titolo_carrelli_liste);
        titolo.setText("Carrelli");
        crea = (Button) findViewById(R.id.bottone_aggiungi);
        lista_activity = (ListView) findViewById(R.id.lista_carrelli_liste);

        if (lista.size() != 0) {
            carrelli_adapter adapter = new carrelli_adapter(this, R.layout.riga, lista);
            lista_activity.setAdapter(adapter);
        } else {
            List objects = new LinkedList();
            objects.add("un elemento giusto per fare uscire una riga");
            non_ci_sono_elementi_adapter a = new non_ci_sono_elementi_adapter(this, R.layout.non_ci_sono_carrelli, objects);
            lista_activity.setAdapter(a);
        }
        crea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(carrelli.this, crea_lista_carrello.class);
                intent.putExtra("tipologia", "carrello");
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(carrelli.this, home.class);
        startActivity(intent);
        finish();
    }


}
