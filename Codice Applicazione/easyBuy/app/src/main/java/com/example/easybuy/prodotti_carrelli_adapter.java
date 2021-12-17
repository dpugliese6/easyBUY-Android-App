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

public class prodotti_carrelli_adapter extends ArrayAdapter { //per personalizzare la visione dei prodotti dei carrelli

    private ImageView immagine_prod,immagine_super;
    private TextView nome;
    private TextView marchio;
    private TextView descrizione;
    private TextView prezzo;
    private TextView categoria;
    private Button elimina;
    private prodotto_dato p;
    private db_esterno dbe;
    private GestisciDB dbi;
    private Context ctx;


    public prodotti_carrelli_adapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        ctx=context;
    }




    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View riga;
        dbe =new db_esterno();

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            riga = inflater.inflate(R.layout.prodotti_carrelli, null); //seleziono il layaut di personalizzazione per la riga
            nome = (TextView) riga.findViewById(R.id.nome_prod_carrello);
            marchio = (TextView) riga.findViewById(R.id.marchio_prod_carrello);
            descrizione = (TextView) riga.findViewById(R.id.descrizione_prod_carrello);
            categoria=(TextView)riga.findViewById(R.id.categ_prod_carrello);
            prezzo=(TextView)riga.findViewById(R.id.prezzo_prod_carrello);
            elimina = (Button)riga.findViewById(R.id.cancella_prodotto_carrello);

            p=(prodotto_dato) getItem(position);
            dbi=new GestisciDB(elimina.getContext());
            immagine_prod = (ImageView) riga.findViewById(R.id.im_prod_carrello);
            immagine_super = (ImageView) riga.findViewById(R.id.im_super_carrello);

                new download(immagine_prod).execute(new String[]{"prodotto/" + p.getImmagine()});
                new download(immagine_super).execute(new String[]{"supermercato/" + dbi.getImmaginedaIva(p.getPiva())});

            nome.setText(p.getNome());
            marchio.setText(p.getMarchio());
            descrizione.setText(p.getDescrizione());
            categoria.setText(p.getCategoria());
            prezzo.setText(p.getPrezzo()+"€");





        elimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p = (prodotto_dato) getItem(position);
                dbe.internetOK = true;
                dbe.cancellaProdottoCarrello(p.getEan(), p.getPiva(), p.getId());
                if (dbe.internetOK) {
                    dbi.cancellaProdottoCarrello(p.getEan(), p.getPiva(), p.getId());
                    Intent intent = new Intent(ctx, prodotti_carrello.class);
                    intent.putExtra("carrello_id", p.getId());
                    intent.putExtra("carrello_nome", dbi.NomeById_carrello(p.getId()));
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
