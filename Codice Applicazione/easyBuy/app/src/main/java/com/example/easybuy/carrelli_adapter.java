package com.example.easybuy;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class carrelli_adapter extends ArrayAdapter {  //classe che serve per personalizzare gli elementi di una listView

    private Button condividi, elimina;
    private TextView carrello;
    private carrello_lista_dato c;
    private db_esterno dbe;
    private GestisciDB dbi;
    private Context ctx;


    public carrelli_adapter(Context context, int textViewResourceId, List objects) {
        super(context, textViewResourceId, objects);
        this.ctx = context;
    }


    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View riga;
        dbe = new db_esterno();

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            riga = inflater.inflate(R.layout.riga, null); //seleziono il layout di personalizzazione per la riga

            carrello = (TextView) riga.findViewById(R.id.nome_carrello_lista);
            condividi = (Button) riga.findViewById(R.id.bottone_condividi_carrello_lista);
            elimina = (Button) riga.findViewById(R.id.cancella_carrello_lista);

            c = (carrello_lista_dato) getItem(position);
            dbi = new GestisciDB(elimina.getContext());
            carrello.setText(c.getNome());



        condividi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                c = (carrello_lista_dato) getItem(position);
                Intent intent = new Intent(ctx, condividi.class);
                intent.putExtra("chiave", c.getId());
                intent.putExtra("tipologia", "carrello");
                ctx.startActivity(intent);
                //Serve il finish
            }
        });

        carrello.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                c = (carrello_lista_dato) getItem(position);
                Intent intent = new Intent(ctx, prodotti_carrello.class); //home per testare
                intent.putExtra("carrello_id", c.getId());
                intent.putExtra("carrello_nome", c.getNome());
                ctx.startActivity(intent);
                //Serve il finish
            }
        });

        elimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = (carrello_lista_dato) getItem(position);
                dbe.internetOK=true;
                dbe.cancellaCarrello(c.getId(), dbi.getEmail());
                if (dbe.internetOK) {
                    dbi.cancellaCarrello(c.getId(), dbi.getEmail());
                    Intent intent = new Intent(ctx, carrelli.class);
                    ctx.startActivity(intent);
                    Toast.makeText(elimina.getContext(), "Carrello rimosso", Toast.LENGTH_LONG).show();
                //serve il finish
                } else {
                    Toast.makeText(elimina.getContext(), "Nessuna connessione a internet!\nCarrello non rimosso...", Toast.LENGTH_LONG).show();
                }
            }
        });

        return riga;
    }


}
