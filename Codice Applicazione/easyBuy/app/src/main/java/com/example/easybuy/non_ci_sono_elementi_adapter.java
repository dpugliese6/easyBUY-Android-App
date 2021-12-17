package com.example.easybuy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class non_ci_sono_elementi_adapter extends ArrayAdapter {

    private TextView t;
    private int layout;

    public non_ci_sono_elementi_adapter(@NonNull Context context, int resource, List objects) {
        super(context, resource, objects);
        layout=resource;
    }



    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View riga ;

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            riga = inflater.inflate(layout, null); //seleziono il layaut di personalizzazione per la riga
            t=(TextView)riga.findViewById(R.id.textView);

        return riga;
    }
}
