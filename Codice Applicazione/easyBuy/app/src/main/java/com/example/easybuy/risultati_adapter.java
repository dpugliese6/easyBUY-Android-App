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

public class risultati_adapter extends ArrayAdapter { //classe per personalizzare gli elementi della ListView delle liste

    private ImageView aggiungi, confronta;
    private TextView categoria, nome, marchio;
    private prodotto_dato p;
    private Context ctx;
    private String dacercare;

    public risultati_adapter(Context context, int textViewResourceId, List objects,String dacerc) {
        super(context, textViewResourceId, objects);
        ctx = context;
        dacercare=dacerc;
    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View riga;

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            riga = inflater.inflate(R.layout.riga_risultati, null); //seleziono il layaut di personalizzazione per la riga
            marchio = (TextView) riga.findViewById(R.id.marchio_risultato);
            nome = (TextView) riga.findViewById(R.id.nome_risultato);
            categoria = (TextView) riga.findViewById(R.id.categoria_risultato);
            aggiungi = (ImageView) riga.findViewById(R.id.aggiungi);
            confronta = (ImageView) riga.findViewById(R.id.compara);

            p = (prodotto_dato) getItem(position);
            nome.setText(p.getNome());
            marchio.setText(p.getMarchio());
            categoria.setText(p.getCategoria());



        aggiungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p = (prodotto_dato) getItem(position);
                Intent intent = new Intent(ctx, scegli.class);
                intent.putExtra("ean", p.getEan());
                intent.putExtra("piva", ""); //perchè uso la stessa classe per scegliere la lista o il carrello al quale aggiungere
                intent.putExtra("tipologia", "lista");
                ctx.startActivity(intent);
                //serve il finish

            }
        });

        confronta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p = (prodotto_dato) getItem(position);
                Intent intent = new Intent(ctx, confronta.class);
                intent.putExtra("cercato", dacercare);
                intent.putExtra("ean", p.getEan());
                ctx.startActivity(intent);
                //serve il finish
            }
        });
        return riga;
    }
}
