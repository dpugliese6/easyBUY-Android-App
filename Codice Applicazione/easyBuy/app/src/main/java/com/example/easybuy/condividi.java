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

public class condividi extends AppCompatActivity {

    private EditText email;
    private Button condividi;
    private db_esterno db;
    private TextView titolo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //costruttore della classe AppCompatActivity
        setContentView(R.layout.layout_condividi); //carica l'interfaccia grafica


        final Bundle bundle = getIntent().getExtras();

        email = (EditText) findViewById(R.id.email_condividi);
        condividi = (Button) findViewById(R.id.bottone_condividi);
        titolo = (TextView) findViewById(R.id.titolo_condividi);

        if (bundle.getString("tipologia").equals("lista")) {
            titolo.setText("Condividi Lista");
        } else {
            titolo.setText("Condividi Carrello");
        }

        condividi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verificaCondivisione()) {
                    db = new db_esterno();
                    if (bundle.getString("tipologia").equals("lista")) {

                        //
                        db.condividiLista(bundle.getString("chiave"), email.getText().toString());
                        if (db.internetOK) {
                            Intent intent = new Intent(com.example.easybuy.condividi.this, liste.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(), "Lista condivisa correttamente", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Nessuna connessione a internet!\nImpossibile condividere la lista/carrello...", Toast.LENGTH_LONG).show();
                        }
                    } else {

                        db.condividiCarrello(bundle.getString("chiave"), email.getText().toString());
                        if (db.internetOK) {
                            Intent intent = new Intent(com.example.easybuy.condividi.this, carrelli.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(), "Carrello condiviso correttamente", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Nessuna connessione a internet!\nImpossibile condividere la lista/carrello...", Toast.LENGTH_LONG).show();
                        }
                    }

                }
            }
        });

    }


    private boolean emailValida(String e) {
        return e.contains("@") && !e.contains(" ") && e.contains(".") && e.length() < 51;
    }

    private boolean verificaCondivisione() {
        boolean cond = true;

        if (TextUtils.isEmpty(email.getText())) {
            email.setError("Campo email vuoto!");
            cond = false;
        } else {
            if (!emailValida(email.getText().toString())) {
                email.setError("Inserisci un indirizzo email valido!");
                cond = false;
            } else {
                db_esterno dbe = new db_esterno();
                boolean c = dbe.mailLibera(email.getText().toString());
                if (dbe.internetOK) {
                    if (c) {
                        email.setError("Indirizzo non presente nel nostro Database");
                        cond = false;
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Nessuna connessione a internet!\nImpossibile condividere la lista/carrello...", Toast.LENGTH_LONG).show();
                    return false;
                }
            }
        }


        return cond;
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("tipologia").equals("lista")){
        Intent intent = new Intent(condividi.this, liste.class);
        startActivity(intent);
        finish();
    }else{
            Intent intent = new Intent(condividi.this, carrelli.class);
            startActivity(intent);
            finish();
        }
    }
}
