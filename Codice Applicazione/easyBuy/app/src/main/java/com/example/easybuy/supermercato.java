package com.example.easybuy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class supermercato extends AppCompatActivity {

    private ImageView supermercato, mappa;
    private TextView nome, citta, via, civico;
    private Button prodotti;
    private Bundle bundle;
    private GestisciDB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //costruttore della classe AppCompatActivity
        setContentView(R.layout.layout_supermercato); //carica l'interfaccia grafica

        supermercato=(ImageView)findViewById(R.id.image_supermercato);
        mappa=(ImageView)findViewById(R.id.image_mappa);
        nome=(TextView)findViewById(R.id.titolo_supermercato);
        citta=(TextView)findViewById(R.id.citta_supermercato);
        via=(TextView)findViewById(R.id.via_supermercato);
        civico=(TextView)findViewById(R.id.civico_supermercato);
        prodotti=(Button)findViewById(R.id.button_prodotti);

        bundle=getIntent().getExtras();
        db=new GestisciDB(this);

        nome.setText(db.getNomeSupermercato(bundle.getString("piva")));
        citta.setText(db.getCittaSupermercato(bundle.getString("piva")));
        via.setText(db.getViaSupermercato(bundle.getString("piva")));
        civico.setText(db.getCivicoSupermercato(bundle.getString("piva")));
        new download(supermercato).execute(new String[]{"supermercato/" + db.getImmagineSupermercato(bundle.getString("piva"))});
        new download(mappa).execute(new String[]{"mappa/" + db.getMappaSupermercato(bundle.getString("piva"))});


        prodotti.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(com.example.easybuy.supermercato.this, categoria.class);
                        intent.putExtra("piva", bundle.getString("piva"));
                        startActivity(intent);
                        finish();

                    }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(com.example.easybuy.supermercato.this, supermercati.class);
        startActivity(intent);
        finish();
    }
}
