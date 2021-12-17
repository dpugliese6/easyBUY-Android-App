package com.example.easybuy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
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
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

public class confronta_adapter extends ArrayAdapter { //classe per personalizzare gli elementi della ListView delle liste

    private ConstraintLayout scelta;
    private ImageView immagine;
    private compara_dato c;
    private TextView nome, prezzo;
    private Context ctx;

    public confronta_adapter(Context context, int textViewResourceId, List objects) {
        super(context, textViewResourceId, objects);
        ctx=context;
    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View  riga;

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            riga = inflater.inflate(R.layout.riga_compara, null);
            immagine = (ImageView) riga.findViewById(R.id.immagine_supermercato);
            scelta = (ConstraintLayout) riga.findViewById(R.id.riga_compara_scelta);
            nome = (TextView) riga.findViewById(R.id.nome_supermercato);
            prezzo = (TextView) riga.findViewById(R.id.campo_prezzo);

            c = (compara_dato) getItem(position);

            new download(immagine).execute(new String[]{"supermercato/" + c.getImmagine()});
            nome.setText(c.getNome());
            prezzo.setText(Float.toString(c.getPrezzo())+"€");



        scelta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                c = (compara_dato) getItem(position);
                Intent intent = new Intent(ctx, scegli.class);
                intent.putExtra("ean", c.getEan());
                intent.putExtra("piva", c.getPiva());
                intent.putExtra("tipologia", "carrello");
                ctx.startActivity(intent);

            }
        });


        return riga;
    }


}
