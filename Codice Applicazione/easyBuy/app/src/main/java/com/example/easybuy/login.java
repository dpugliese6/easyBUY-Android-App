package com.example.easybuy;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {


    private Button no_reg;
    private Button accedi;
    private EditText email;
    private EditText psw;
    private TextView err;
    private CheckBox resta_collegato;
    private GestisciDB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //deve stare sempre
        setContentView(R.layout.layout_login); //imposta il layout

        db = new GestisciDB(this);


        no_reg = (Button) findViewById(R.id.no_registrato);
        accedi = (Button) findViewById(R.id.tasto_accedi);
        email = (EditText) findViewById(R.id.campo_mail);
        psw = (EditText) findViewById(R.id.campo_password);
        resta_collegato = (CheckBox) findViewById(R.id.resta_collegato);
        err=(TextView) findViewById(R.id.text_error);

        no_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, registrazione.class);
                startActivity(intent);
                finish();

            }
        });

        accedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (verificaLogin()) {
                    if (resta_collegato.isChecked()) {
                        db.inserisci_credenziali(email.getText().toString(), psw.getText().toString(), 1);
                    } else {
                        db.inserisci_credenziali(email.getText().toString(), psw.getText().toString(), 0);
                    }
                    Intent intent = new Intent(login.this, home.class);
                    startActivity(intent);
                    finish();

                }
            }
        });


    }


    private boolean emailValida(String email) { //condizioni per cui l'email è valida
        return email.contains("@") && !email.contains(" ") && email.contains(".") && email.length()<51;
    }

    public boolean passwordValida(String password) {//condizioni per cui la password è valida
        return password.length() >= 8 && password.length() <= 20;
    }


    private boolean verificaLogin() {
        boolean cond = true;

        if (TextUtils.isEmpty(email.getText())) {
            email.setError("Campo email vuoto!");
            cond = false;
        }else{
        if (!emailValida(email.getText().toString())) {
            email.setError("Inserisci un indirizzo email valido!");
            cond = false;
        }}

        if (TextUtils.isEmpty(psw.getText()) ) {
            psw.setError("Campo password vuoto!");
            cond = false;
        }


        if (cond) {
            db_esterno dbe = new db_esterno();
            boolean c = dbe.credenzialiOk(email.getText().toString(), psw.getText().toString());
            if (dbe.internetOK) {
                if (!c) {
                    err.setText("Email o password errata");
                    psw.setText("");
                    cond = false;
                }
            } else {
                Toast.makeText(getApplicationContext(), "Nessuna connessione a internet!", Toast.LENGTH_LONG).show();
                cond = false;
            }

        }

        return cond;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}