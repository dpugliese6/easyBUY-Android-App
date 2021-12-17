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

public class prodotti_liste_adapter extends ArrayAdapter {

    private ImageView immagine;
    private TextView nome;
    private TextView marchio;
    private TextView descrizione;
    private TextView categoria;
    private Button elimina;
    private prodotto_dato p;
    private db_esterno dbe;
    private GestisciDB dbi;
    private Context ctx;


    public prodotti_liste_adapter(Context context, int textViewResourceId, List objects) {
        super(context, textViewResourceId, objects);
        ctx = context;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View riga;
        dbe = new db_esterno();

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            riga = inflater.inflate(R.layout.prodotti_liste, null); //seleziono il layaut di personalizzazione per la riga

            nome = (TextView) riga.findViewById(R.id.nome_prod_lista);
            marchio = (TextView) riga.findViewById(R.id.marchio_prod_lista);
            descrizione = (TextView) riga.findViewById(R.id.desc_prod_lista);
            categoria = (TextView) riga.findViewById(R.id.categoria_prodotto_lista);
            elimina = (Button) riga.findViewById(R.id.cancella_prodotto_lista);

            p = (prodotto_dato) getItem(position);
            dbi = new GestisciDB(elimina.getContext());
            immagine = (ImageView) riga.findViewById(R.id.im_prod_lista);

            new download(immagine).execute(new String[]{"prodotto/" + p.getImmagine()});

            nome.setText(p.getNome());
            marchio.setText(p.getMarchio());
            descrizione.setText(p.getDescrizione());
            categoria.setText(p.getCategoria());



        elimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p = (prodotto_dato) getItem(position);
                dbe.internetOK = true;
                dbe.cancellaProdottoLista(p.getEan(), p.getId());
                if (dbe.internetOK) {
                    dbi.cancellaProdottoLista(p.getEan(), p.getId());
                    Intent intent = new Intent(ctx, prodotti_lista.class);
                    intent.putExtra("lista_id", p.getId());
                    intent.putExtra("lista_nome", dbi.NomeById_lista(p.getId()));
                    ctx.startActivity(intent);
                    Toast.makeText(elimina.getContext(), "Prodotto rimosso", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(elimina.getContext(), "Nessuna connessione a internet!\nProdotto non rimosso...", Toast.LENGTH_LONG).show();
                }

            }
        });

        return riga;
    }


}
