package com.example.easybuy;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class profilo extends AppCompatActivity {
    private GestisciDB db;
    private Button salva;
    private Button cambia;
    private Button elimina;

    private TextView email;

    private EditText nome;
    private EditText cognome;

    private EditText data;
    private RadioGroup sex;
    private RadioButton m;
    private RadioButton f;
    private DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //costruttore della classe AppCompatActivity
        setContentView(R.layout.layout_profilo); //carica l'interfaccia grafica
        db = new GestisciDB(this);

        salva = (Button) findViewById(R.id.salva_profilo);
        cambia = (Button) findViewById(R.id.cambia_password);
        elimina = (Button) findViewById(R.id.elimina_profilo);

        email = (TextView) findViewById(R.id.mail_profilo);
        nome = (EditText) findViewById(R.id.campo_nome);
        cognome = (EditText) findViewById(R.id.campo_cognome);
        data = (EditText) findViewById(R.id.campo_dn);
        sex = (RadioGroup) findViewById(R.id.m_f_profilo);
        m = (RadioButton) findViewById(R.id.m_profilo);
        f = (RadioButton) findViewById(R.id.f_profilo);

        email.setText(db.getEmail());
        nome.setText(db.getNome(db.getEmail()));
        cognome.setText(db.getCognome(db.getEmail()));
        data.setText(db.getDN(db.getEmail()));
        if (db.getSesso(db.getEmail()).equals("m")) {
            m.setChecked(true);
            f.setChecked(false);
        } else {
            f.setChecked(true);
            m.setChecked(false);
        }

        data.setInputType(InputType.TYPE_NULL);
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(profilo.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
                                calendar.set(year, monthOfYear, dayOfMonth);
                                Date myDate = calendar.getTime();
                                Calendar oggi = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
                                Date today = oggi.getTime();
                                if (myDate.before(today)) {
                                    data.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                } else {
                                    data.setText("");
                                    Toast.makeText(getApplicationContext(), "Hai viaggiato nel tempo?", Toast.LENGTH_LONG).show();
                                }
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        salva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verificaCampi()) {
                    char s;
                    if (m.isChecked()) {
                        s = 'm';
                    } else {
                        s = 'f';
                    }
                    db_esterno dbe = new db_esterno();
                    dbe.updateUtente(email.getText().toString(), nome.getText().toString(), cognome.getText().toString(), s, data.getText().toString());
                    if (dbe.internetOK) {
                    Toast.makeText(getApplicationContext(), "Utente modificato correttamente", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(profilo.this, home.class);
                    startActivity(intent);
                    finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Devi essere connesso a internet per poter modificare i tuoi dati!", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
        cambia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profilo.this, modifica_password.class);
                startActivity(intent);
                finish();

            }
        });
        elimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e = db.getEmail();
                db_esterno dbe = new db_esterno();
                dbe.deleteUtente(e);
                if (dbe.internetOK) {
                    db.logout();
                    Toast.makeText(getApplicationContext(), "Utente eliminato correttamente", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(profilo.this, login.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Devi essere connesso a internet per poter eliminare il tuo account!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private boolean verificaCampi() {
        boolean cond = true;

        // controllo che abbia inserito il nome
        if (TextUtils.isEmpty(nome.getText().toString())) {
            nome.setError("Campo obbligatorio");
            cond = false;
        }
        //  controllo che abbia inserito il cognome
        if (TextUtils.isEmpty(cognome.getText().toString())) {
            cognome.setError("Campo obbligatorio");
            cond = false;
        }

        //  controllo che abbia inserito la data
        if (TextUtils.isEmpty(data.getText().toString())) {
            data.setError("Campo obbligatorio");
            cond = false;
        }
        return cond;
    }


    public void onBackPressed() {
        Intent intent = new Intent(profilo.this, home.class);
        startActivity(intent);
        finish();
    }


}
