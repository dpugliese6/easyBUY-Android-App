package com.example.easybuy;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

public class crea_lista_carrello extends AppCompatActivity {

    private EditText nomecrea;
    private Button crea;
    private db_esterno dbe;
    private GestisciDB dbi;
    private TextView titolo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //costruttore della classe AppCompatActivity
        setContentView(R.layout.layout_crea_lista_carrello); //carica l'interfaccia grafica

        dbe=new db_esterno();
        dbi=new GestisciDB(this);
        final Bundle bundle =  getIntent().getExtras();

        nomecrea=(EditText)findViewById(R.id.nome_crea);
        crea=(Button)findViewById(R.id.crea);
        titolo = (TextView) findViewById(R.id.titolo_crea);

        if (bundle.getString("tipologia").equals("lista")) {
            titolo.setText("Crea Lista");
        }else{
            titolo.setText("Crea Carrello");
        }

        crea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verificaCrea()) {
                    dbe.internetOK=true;
                    if (bundle.getString("tipologia").equals("lista")) {

                        dbe.insertLista(nomecrea.getText().toString(), dbi.getEmail());
                        if (dbe.internetOK) {
                            CaricaDB carica = new CaricaDB(nomecrea.getContext());
                            carica.execute(
                                    "Lista",
                                    "ListaUtente"
                            );
                            Toast.makeText(getApplicationContext(), "Stiamo creando la tua lista...", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(crea_lista_carrello.this, home.class);
                            startActivity(intent);
                            finish();//Non funziona
                        } else {
                            Toast.makeText(getApplicationContext(), "Nessuna connessione a internet!\nImpossibile creare la lista...", Toast.LENGTH_LONG).show();
                        }
                    } else {

                        dbe.insertCarrello(nomecrea.getText().toString(), dbi.getEmail());
                        if (dbe.internetOK) {
                            CaricaDB carica = new CaricaDB(nomecrea.getContext());
                            carica.execute(
                                    "Carrello",
                                    "CarrelloUtente"
                            );
                            Toast.makeText(getApplicationContext(), "Stiamo creando il tuo carrello...", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(crea_lista_carrello.this, home.class);
                            startActivity(intent);
                            finish(); //Non funziona
                        } else {
                            Toast.makeText(getApplicationContext(), "Nessuna connessione a internet!\nImpossibile creare il carrello...", Toast.LENGTH_LONG).show();
                        }
                    }

                }
            }
        });

    }

    private boolean verificaCrea() {
        boolean cond = true;

        // controllo il nome
        if (TextUtils.isEmpty(nomecrea.getText())) {
            nomecrea.setError("Il campo è vuoto");
            cond = false;
        }

        // controllo che abbia inserito il nome
        if (nomecrea.getText().toString().length() > 20) {
            nomecrea.setError("Massimo 20 caratteri");
            cond = false;
        }

        if (!cond) {
            nomecrea.setText("");
        }

        return cond;
    }

    @Override
    public void onBackPressed() {
        Bundle bundle =  getIntent().getExtras();

        if (bundle.getString("tipologia").equals("lista")) {
            Intent intent = new Intent(crea_lista_carrello.this, liste.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(crea_lista_carrello.this, carrelli.class);
            startActivity(intent);
            finish();
        }
    }
}
