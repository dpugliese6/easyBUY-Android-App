package com.example.easybuy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;


public class supermercato_prodotti_adapter extends ArrayAdapter {

    private ImageView immagine_prod,lista,carrello;
    private TextView nome;
    private TextView marchio;
    private TextView descrizione;
    private TextView prezzo;
    private prodotto_dato p;
    private db_esterno dbe;
    private GestisciDB dbi;

    public supermercato_prodotti_adapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View riga;
        dbe =new db_esterno();

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        riga = inflater.inflate(R.layout.riga_prodotti_supermercato, null);
            nome = (TextView) riga.findViewById(R.id.nome_prod_supermercato);
            marchio = (TextView) riga.findViewById(R.id.marchio_prod_supermercato);
            descrizione = (TextView) riga.findViewById(R.id.descrizione_prod_supermercato);
            prezzo=(TextView)riga.findViewById(R.id.prezzo_prod_supermercato);
            immagine_prod = (ImageView) riga.findViewById(R.id.im_prod_supermercato);
            lista=(ImageView) riga.findViewById(R.id.aggiungi_lista);
            carrello=(ImageView) riga.findViewById(R.id.aggiungi_carrello);

            p=(prodotto_dato) getItem(position);

            dbi=new GestisciDB(nome.getContext());

            nome.setText(p.getNome());
            marchio.setText(p.getMarchio());
            descrizione.setText(p.getDescrizione());
            prezzo.setText(p.getPrezzo()+"€");
            new download(immagine_prod).execute(new String[]{"prodotto/" + p.getImmagine()});





        lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p=(prodotto_dato) getItem(position);

                Intent intent = new Intent(supermercato_prodotti_adapter.super.getContext(), scegli.class);
                intent.putExtra("ean",p.getEan());
                intent.putExtra("piva","");
                intent.putExtra("tipologia","lista");
                supermercato_prodotti_adapter.super.getContext().startActivity(intent); //non è una classe activity per richiamare il metodo startactivity co devo fare su un oggetto di tipo context


            }
        });

        carrello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p=(prodotto_dato) getItem(position);
                Intent intent = new Intent(supermercato_prodotti_adapter.super.getContext(), confronta.class);
                intent.putExtra("ean",p.getEan());
                intent.putExtra("piva",p.getPiva());
                intent.putExtra("categoria",p.getCategoria());
                supermercato_prodotti_adapter.super.getContext().startActivity(intent);
            }
        });

        return riga;
    }
}

