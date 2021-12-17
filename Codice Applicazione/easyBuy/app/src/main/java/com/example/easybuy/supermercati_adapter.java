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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class supermercati_adapter  extends ArrayAdapter {

    private ImageView im_super;
    private TextView  nome;
    private Button bottone;
    private String piva;

    public supermercati_adapter(Context context, int textViewResourceId, List objects) {
        super(context, textViewResourceId, objects);
    }


    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View riga = convertView;

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            riga = inflater.inflate(R.layout.riga_supermercati, null); //seleziono il layaut di personalizzazione per la riga
            nome=(TextView)riga.findViewById(R.id.nome_supermercati);
            im_super=(ImageView)riga.findViewById(R.id.image_supermercati);
            bottone = (Button) riga.findViewById(R.id.prodotti_supermercato);

            piva = (String) getItem(position);
            nome.setText(new GestisciDB(nome.getContext()).getNomeSupermercato(piva));
            new download(im_super).execute(new String[]{"supermercato/" + new GestisciDB(nome.getContext()).getImmagineSupermercato(piva)});



        bottone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                piva= (String) getItem(position);

                Intent intent = new Intent(supermercati_adapter.super.getContext(), categoria.class);
                intent.putExtra("piva",piva);
                supermercati_adapter.super.getContext().startActivity(intent); //non è una classe activity per richiamare il metodo startactivity co devo fare su un oggetto di tipo context
            }
        });

        nome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                piva= (String) getItem(position);

                Intent intent = new Intent(supermercati_adapter.super.getContext(), supermercato.class);
                intent.putExtra("piva",piva);
                supermercati_adapter.super.getContext().startActivity(intent);
            }
        });


        return riga;







    }

}
