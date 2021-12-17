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

public class modifica_password extends AppCompatActivity {
    GestisciDB db;
    private Button cambia;
    private EditText vecchia;
    private EditText nuova;
    private EditText nuova2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //costruttore della classe AppCompatActivity
        setContentView(R.layout.layout_modifica_password); //carica l'interfaccia grafica

        db = new GestisciDB(this);

        vecchia = (EditText) findViewById(R.id.vecchia);
        nuova = (EditText) findViewById(R.id.nuova);
        nuova2 = (EditText) findViewById(R.id.nuova2);
        cambia = (Button) findViewById(R.id.cambia);

        cambia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (verificaPassword()) {
                    int r_c = db.getRC();
                    String email = db.getEmail();
                    db_esterno dbe = new db_esterno();
                    dbe.updatePassword(email, nuova.getText().toString());
                    if (dbe.internetOK) {
                        db.logout();
                        db.inserisci_credenziali(email, nuova.getText().toString(), r_c);
                        Toast.makeText(getApplicationContext(), "Password modificata correttamente", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(modifica_password.this, profilo.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Devi essere connesso a internet per poter modificare la password!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public boolean passwordValida(String password) {//condizioni per cui la password è valida
        return password.length() >= 8 && password.length() <= 20;
    }

    private boolean verificaPassword() {
        boolean cond = true;

        if (TextUtils.isEmpty(vecchia.getText())) {
            vecchia.setError("Campo obblicatorio!");
            cond = false;

        } else {
            if (!vecchia.getText().toString().equals(db.getPsw())) {

                vecchia.setError("Password errata");
                cond = false;
            }
        }

        if (TextUtils.isEmpty(nuova.getText())) {
            nuova.setError("Campo password vuoto!");
            cond = false;
            nuova2.setText("");
        } else {
            if (!passwordValida(nuova.getText().toString())) {
                nuova.setError("Lunghezza >8 e <20");
                cond = false;
                nuova.setText("");
                nuova2.setText("");
            } else {
                if (!nuova.getText().toString().equals(nuova2.getText().toString())) {
                    nuova2.setError("Le password non corrispondono");
                    cond = false;
                    nuova2.setText("");
                }
            }
        }


        if (!cond) {
            vecchia.setText("");
        }

        return cond;
    }

    public void onBackPressed() {
        Intent intent = new Intent(modifica_password.this, profilo.class);
        startActivity(intent);
        finish();
    }

}