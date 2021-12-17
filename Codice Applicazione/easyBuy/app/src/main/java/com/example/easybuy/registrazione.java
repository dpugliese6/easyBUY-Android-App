package com.example.easybuy;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class registrazione extends AppCompatActivity {

    private Button si_reg;
    private Button registrati;
    private EditText email;
    private EditText psw;
    private EditText ripeti_psw;
    private EditText nome;
    private EditText cognome;
    private EditText data;
    private RadioGroup sex;
    private RadioButton m;
    private RadioButton f;
    private GestisciDB db;
    private DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //deve stare sempre
        setContentView(R.layout.layout_registrazione); //imposta il layout

        db = new GestisciDB(this);

        si_reg = (Button) findViewById(R.id.si_registrato);
        registrati = (Button) findViewById(R.id.tasto_registrati);
        email = (EditText) findViewById(R.id.campo_mail2);
        nome = (EditText) findViewById(R.id.campo_nome);
        cognome = (EditText) findViewById(R.id.campo_cognome);
        data = (EditText) findViewById(R.id.data_nascita);
        sex = (RadioGroup) findViewById(R.id.m_f);
        m = (RadioButton) findViewById(R.id.but_m);
        f = (RadioButton) findViewById(R.id.but_F);
        psw = (EditText) findViewById(R.id.password);
        ripeti_psw = (EditText) findViewById(R.id.ripeti_password);


        registrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verificaRegistrazione()) {
                    char s;
                    if (m.isChecked()) {
                        s = 'm';
                    } else {
                        s = 'f';
                    }
                    db_esterno dbe = new db_esterno();
                    dbe.insertUtente(email.getText().toString(), psw.getText().toString(), nome.getText().toString(), cognome.getText().toString(), s, data.getText().toString());
                    if (dbe.internetOK) {
                        Toast.makeText(getApplicationContext(), "Utente iscritto correttamente", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(registrazione.this, login.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Nessuna connessione a internet!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        si_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(registrazione.this, login.class);
                startActivity(intent);
                finish();
            }
        });


        data.setInputType(InputType.TYPE_NULL);
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(registrazione.this,
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
    }


    public boolean passwordValida(String password) {//condizioni per cui la password è valida
        return password.length() >= 8 && password.length() <= 20;
    }

    private boolean emailValida(String e) {
        return e.contains("@") && !e.contains(" ") && e.contains(".") && e.length() < 51;
    }

    private boolean verificaRegistrazione() {
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
                    if (!c) {
                        email.setError("Indirizzo email già registrato");
                        cond = false;
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Nessuna connessione a internet!", Toast.LENGTH_LONG).show();
                    return false;
                }
            }
        }


        if (TextUtils.isEmpty(nome.getText().toString())) {
            nome.setError("Campo obbligatorio");
            cond = false;
        }

        if (TextUtils.isEmpty(cognome.getText().toString())) {
            cognome.setError("Campo obbligatorio");
            cond = false;
        }


        if (TextUtils.isEmpty(data.getText().toString())) {
            data.setError("Campo obbligatorio");
            cond = false;
        }


        if (TextUtils.isEmpty(psw.getText())) {
            psw.setError("Campo password vuoto!");
            cond = false;
            ripeti_psw.setText("");
        } else {
            if (!passwordValida(psw.getText().toString())) {
                psw.setError("Lunghezza >8 e <20");
                cond = false;
                psw.setText("");
                ripeti_psw.setText("");
            } else {
                if (!psw.getText().toString().equals(ripeti_psw.getText().toString())) {
                    ripeti_psw.setError("Le password non corrispondono");
                    cond = false;
                    ripeti_psw.setText("");
                }
            }
        }


        return cond;
    }

    public void onBackPressed() {
        Intent intent = new Intent(registrazione.this, login.class);
        startActivity(intent);
        finish();
    }


}