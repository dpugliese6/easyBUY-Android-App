package com.example.easybuy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class categoria_adapter extends ArrayAdapter {

    private TextView nome_categoria;
    private String categoria,piva;

    public categoria_adapter(Context context, int textViewResourceId, List objects, String Piva) {
        super(context, textViewResourceId, objects);
        piva=Piva;
    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View riga;


        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        riga = inflater.inflate(R.layout.riga_categoria, null);
        nome_categoria=(TextView)riga.findViewById(R.id.nome_categoria);
        categoria=(String) getItem(position);
        nome_categoria.setText(categoria);

        riga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoria=(String) getItem(position);

                Intent intent = new Intent(categoria_adapter.super.getContext(), supermercato_prodotti.class);
                intent.putExtra("categoria",categoria);
                intent.putExtra("piva",piva);
                categoria_adapter.super.getContext().startActivity(intent); //non è una classe activity per richiamare il metodo startactivity co devo fare su un oggetto di tipo context


            }
        });



        return riga;
    }

}
