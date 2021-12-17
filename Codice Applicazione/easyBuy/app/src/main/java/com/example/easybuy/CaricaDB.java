package com.example.easybuy;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;

public class CaricaDB extends AsyncTask<String, Void, Boolean> {
GestisciDB db;
Context context;
    public CaricaDB(Context ctx){
        db=new GestisciDB(ctx);
        context=ctx;
    }
    @Override
    protected Boolean doInBackground(String... tabelle) {
        Boolean internet = true;
boolean cond=true;
        for (String tabella : tabelle) {
            switch (tabella){
                case "Prodotto":
                    try {
                        cond=db.caricaProdotto();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Utente":
                    try {
                        cond=db.caricaUtente();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;/*
                case "Volantino":
                    try {
                        cond=db.caricaVolantino();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;*/
               case "Carrello":
                   try {
                       cond=db.caricaCarrello();
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
                   break;
                case "CarrelloProdotti":
                    try {
                        cond=db.caricaCarrelloProdotti();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case "CarrelloUtente":
                    try {
                        cond=db.caricaCarrelloUtente();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Lista":
                    try {
                        cond=db.caricaLista();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case "ListaProdotti":
                    try {
                        cond=db.caricaListaProdotti();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case "ListaUtente":
                    try {
                        cond=db.caricaListaUtente();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Supermercato":
                    try {
                        cond=db.caricaSupermercato();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case "SupermercatoProdotto":
                    try {
                        cond=db.caricaSupermercatoProdotto();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Nodo":
                    try {
                        cond=db.caricaNodo();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;


            }
            if(!cond){
            internet=false;}
        }






        return internet;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (!result) {
            Toast.makeText(context, "Nessuna connessione a internet!\nI dati potrebbero non essere aggiornati correttamente...", Toast.LENGTH_LONG).show();
        }
    }



}