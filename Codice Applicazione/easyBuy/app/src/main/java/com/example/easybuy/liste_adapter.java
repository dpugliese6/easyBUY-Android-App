package com.example.easybuy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import static com.example.easybuy.R.id;
import static com.example.easybuy.R.layout;

public class liste_adapter extends ArrayAdapter { //classe per personalizzare gli elementi della ListView delle liste

    private Button condividi,elimina;
    private TextView lista,text;
    private carrello_lista_dato c;
    private db_esterno dbe;
    private GestisciDB dbi;
    private Context ctx;

    public liste_adapter(Context context, int textViewResourceId, List objects) {
        super(context, textViewResourceId, objects);
        ctx=context;
    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View riga;
        dbe=new db_esterno();
        
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            riga = inflater.inflate(layout.riga, null); //seleziono il layaut di personalizzazione per la riga
            lista=(TextView)riga.findViewById(id.nome_carrello_lista);
            condividi=(Button)riga.findViewById(id.bottone_condividi_carrello_lista);
            elimina=(Button)riga.findViewById(id.cancella_carrello_lista);

            c= (carrello_lista_dato) getItem(position);
            dbi=new GestisciDB(elimina.getContext());
            lista.setText(c.getNome());



        condividi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                c= (carrello_lista_dato) getItem(position);
                Intent intent = new Intent(ctx, condividi.class);
                intent.putExtra("chiave",c.getId());
                intent.putExtra("tipologia","lista");
                ctx.startActivity(intent);
                //Serve il finish
            }
        });

        elimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c= (carrello_lista_dato) getItem(position);
                dbe.internetOK=true;
                dbe.cancellaLista(c.getId(),dbi.getEmail());
                if (dbe.internetOK) {
                dbi.cancellaLista(c.getId(),dbi.getEmail());
                Intent intent = new Intent(liste_adapter.super.getContext(), liste.class);
                ctx.startActivity(intent);
                Toast.makeText(elimina.getContext(), "Lista rimossa", Toast.LENGTH_LONG).show();
                //serve il finish
                } else {
                    Toast.makeText(elimina.getContext(), "Nessuna connessione a internet!\nLista non rimossa...", Toast.LENGTH_LONG).show();
                }
            }
        });

        lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                c= (carrello_lista_dato) getItem(position);
                Intent intent = new Intent(ctx, prodotti_lista.class); //login per testare
                intent.putExtra("lista_id",c.getId());
                intent.putExtra("lista_nome",c.getNome());
                ctx.startActivity(intent);
                //Serve il finish
            }
        });

        return riga;
    }




}
