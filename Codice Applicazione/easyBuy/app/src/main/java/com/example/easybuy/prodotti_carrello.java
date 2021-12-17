package com.example.easybuy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.LinkedList;
import java.util.List;

public class prodotti_carrello extends AppCompatActivity {

    private GestisciDB db;
    private ListView lista_activity;
    private Button iniziapercorso;
    private ConstraintLayout layperc;
    private Bundle bundle;
    private TextView titolo;
    private String id_carrello, nome_carrello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //costruttore della classe AppCompatActivity
        setContentView(R.layout.layout_prodotti_carrelli); //carica l'interfaccia grafica

        db = new GestisciDB(this);
        bundle = getIntent().getExtras();

        id_carrello = bundle.getString("carrello_id");
        nome_carrello = bundle.getString("carrello_nome");
        layperc = (ConstraintLayout) findViewById(R.id.lay_percorso);


        if (!db.isPercorribile(id_carrello)) {
            layperc.setVisibility(View.INVISIBLE);
        } else {
            iniziapercorso = (Button) findViewById(R.id.bottone_inizia_percorso);
            iniziapercorso.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(prodotti_carrello.this, percorso.class);
                    intent.putExtra("Id_carrello", id_carrello);
                    intent.putExtra("nome", nome_carrello);
                    startActivity(intent);
                    finish();
                }
            });
        }
        titolo = (TextView) findViewById(R.id.titolo_prodotti_carrello);
        titolo.setText(nome_carrello);
        lista_activity = (ListView) findViewById(R.id.lista_prodotti_carrello);

        List lista = db.getProdottiFromCarrello(id_carrello);//in questa lista andranno i prodotti della lista

        if (lista.size() != 0) {

            prodotti_carrelli_adapter adapter = new prodotti_carrelli_adapter(this, R.layout.prodotti_carrelli, lista);
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
        Intent intent = new Intent(prodotti_carrello.this, carrelli.class);
        startActivity(intent);
        finish();
    }
}
