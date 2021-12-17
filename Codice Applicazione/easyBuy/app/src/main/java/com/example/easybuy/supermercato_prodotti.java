package com.example.easybuy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class supermercato_prodotti extends AppCompatActivity {

    private TextView titolo;
    private ListView lista;
    private GestisciDB db;
    private Bundle bundle;
    private String categoria,piva;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //costruttore della classe AppCompatActivity
        setContentView(R.layout.layout_supermercati); //carica l'interfaccia grafica

        db= new GestisciDB(this);
        bundle =getIntent().getExtras();
categoria=bundle.getString("categoria");
piva=bundle.getString("piva");
        titolo=(TextView)findViewById(R.id.titolo_supermercati);
        titolo.setText(categoria);

        lista=(ListView)findViewById(R.id.lista_supermercati);

        List list= db.getProdottiFromCategoriaSupermercato(piva,categoria);
        supermercato_prodotti_adapter adapter =new supermercato_prodotti_adapter(this, R.layout.riga_prodotti_supermercato,list);
        lista.setAdapter(adapter);


    }


    public void onBackPressed(){
        Intent intent = new Intent(supermercato_prodotti.this, categoria.class);
        intent.putExtra("piva",piva);
        startActivity(intent);
        finish();
    }
}
