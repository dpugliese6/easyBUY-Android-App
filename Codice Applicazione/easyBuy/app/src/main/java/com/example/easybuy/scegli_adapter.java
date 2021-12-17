package com.example.easybuy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class scegli_adapter extends ArrayAdapter { //classe per personalizzare gli elementi della ListView delle liste
    private String ean;
    private String piva;
    private TextView nome;
    private carrello_lista_dato c;
    private Context ctx;


    public scegli_adapter(Context context, int textViewResourceId, List objects, String ean) {
        super(context, textViewResourceId, objects);
        this.ean = ean;
        this.piva = null;
        ctx = context;
    }

    public scegli_adapter(Context context, int textViewResourceId, List objects, String ean, String piva) {
        super(context, textViewResourceId, objects);
        this.ean = ean;
        this.piva = piva;
        ctx = context;
    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View riga;

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            riga = inflater.inflate(R.layout.riga_scelta_lista, null);
            nome = (TextView) riga.findViewById(R.id.nome_scelta);

            c = (carrello_lista_dato) getItem(position);

            nome.setText(c.getNome());


        nome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = (carrello_lista_dato) getItem(position);
                db_esterno dbe = new db_esterno();
                if (piva == null) {
                    dbe.insertListaProdotti(c.getId(), ean);
                    if (dbe.internetOK) {
                        Toast.makeText(ctx, "Prodotto aggiunto alla lista", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ctx, home.class);
                        ctx.startActivity(intent);
                        //serve il finish
                    } else {
                        Toast.makeText(ctx, "Nessuna connessione a internet!\nProdotto non aggiunto...", Toast.LENGTH_LONG).show();
                    }

                } else {
                    dbe.insertCarrelloProdotti(c.getId(), ean, piva);
                    if (dbe.internetOK) {
                        Toast.makeText(nome.getContext(), "Prodotto aggiunto al carrello", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ctx, home.class);
                        ctx.startActivity(intent);
                        //serve il finish
                    } else {
                        Toast.makeText(ctx, "Nessuna connessione a internet!\nProdotto non aggiunto...", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        return riga;
    }


}
