package com.example.easybuy;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.lang.reflect.Field;


public class home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private GestisciDB db;
    private ImageButton cerca;
    private Button scan;
    private EditText dacercare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);

        db = new GestisciDB(this);
        dacercare = (EditText) findViewById(R.id.campo_prodotto);
        scan = (Button) findViewById(R.id.tasto_scan);
        cerca = (ImageButton) findViewById(R.id.tasto_cerca);
        drawerLayout = findViewById(R.id.home_layout); //riferimento al layout dell'activity con menu laterale
        navigationView = findViewById(R.id.menu_laterale); //riferimento alla struttura del menu
        toolbar = findViewById(R.id.toolbar); //riferimento alla toolbar

        /*toolbar*/
        setSupportActionBar(toolbar); //setto la toolbar come actionbar

        /*Cambio font e dimensione della scritta easyBuy*/
        TextView scrittatoolbar = null;
        try {
            Field f = toolbar.getClass().getDeclaredField("mTitleTextView");
            f.setAccessible(true);
            scrittatoolbar = (TextView) f.get(toolbar);
            Typeface face = ResourcesCompat.getFont(this, R.font.mikan);
            scrittatoolbar.setTypeface(face);
            scrittatoolbar.setTextSize(24);
        } catch (Exception e) {
        }

        /*Imposto la toolbar per funzionare come contenitore del tasto che apre il menu laterale*/
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        /*Rendere i tasti del menu cliccabili*/
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this); //continua nel metodo onNavigationItemSelected

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                while (!(ActivityCompat.checkSelfPermission(home.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
                    ActivityCompat.requestPermissions(home.this, new
                            String[]{Manifest.permission.CAMERA}, 201);
                }
                Intent intent = new Intent(home.this, scanner.class);
                startActivity(intent);
                finish();
            }
        });
        cerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, risultati.class);
                intent.putExtra("cercato", dacercare.getText().toString());
                startActivity(intent);
                finish();
            }
        });

        //Carico tutte le tabelle
        new CaricaDB(this).execute(
                "Prodotto",
                "Utente",
                "Carrello",
                "CarrelloProdotti",
                "CarrelloUtente",
                "Lista",
                "ListaProdotti",
                "ListaUtente",
                "Supermercato",
                "SupermercatoProdotto",
                "Nodo"
        );


    }


    @Override
    public void onBackPressed() {
        //questo if serve a non far chiudere l'app quando si preme il tasto indietro a menù laterale aperto
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);

        } else {
            finish();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem scelta_menu) {
        switch (scelta_menu.getItemId()) {
            case R.id.menu_carrelli:
                Intent i1 = new Intent(home.this, carrelli.class);
                startActivity(i1);
                break;
            case R.id.menu_liste:
                Intent i2 = new Intent(home.this, liste.class);
                startActivity(i2);
                break;
            case R.id.menu_supermercati:
                Intent i3 = new Intent(home.this, supermercati.class);
                startActivity(i3);
                break;
            case R.id.menu_profilo:
                Intent i4 = new Intent(home.this, profilo.class);
                startActivity(i4);

                break;
            case R.id.menu_esci:
                db.logout();
                Intent i5 = new Intent(home.this, login.class);
                startActivity(i5);
                break;


        }


        drawerLayout.closeDrawer(GravityCompat.START);
        this.finish(); //NON VA
        return true;
    }


}