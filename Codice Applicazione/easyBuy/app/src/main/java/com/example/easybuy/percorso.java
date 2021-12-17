package com.example.easybuy;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class percorso extends AppCompatActivity {

    ArrayList<Point> points;
    private static ArrayList<Point> outputPathDef;
    ArrayList<ProdottoPercorso> prodottiPercorso;
    private GestisciDB db;
    int numNodes;
    tsp tsp=new tsp();
    int j=1;
    String s="";

    private Button prossimo;
    private ImageView pin;
    private TextView testo_prodotto;
    private ImageView immagine_mappa;
    private TextView cosafare;
private float molt;

private String id;
private String nomecarr;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_percorso);
        db=new GestisciDB(this);
        Bundle datipassati = getIntent().getExtras();
        id = datipassati.getString("Id_carrello");
        nomecarr = datipassati.getString("nome");

        Resources r = this.getResources();
        molt=getMolt(this);
        popolaNodi(id);
        tsp.setPoints(points);
        tsp.setNumNodes(points.size());
        tsp.createMap(points);
        Astar a = new Astar();
        a.search();
        outputdef();

        db=new GestisciDB(this);


        immagine_mappa=(ImageView) findViewById(R.id.mappa_supermercato);
        new download(immagine_mappa).execute(new String[]{"mappa/"+ db.getMappadaID(id) });

        prossimo= findViewById(R.id.prossimo);
        pin= findViewById(R.id.pin);
        testo_prodotto=findViewById(R.id.testo_prodotto);
        cosafare=findViewById(R.id.cosa_fare);
        cosafare.setText("Raggiungi l'ingresso...");
        //Punto iniziale settato alle coordinate di a
        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) pin.getLayoutParams();
        marginParams.setMarginStart((int) (((int) outputPathDef.get(0).x)*molt));
        marginParams.setMargins(marginParams.leftMargin,(int) (((int) outputPathDef.get(0).y)*molt),marginParams.rightMargin,marginParams.bottomMargin);



        prossimo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(j<= outputPathDef.size()-1){
                    float tx = calclTraslX(j) * molt;
                    float ty = calclTraslY(j) * molt;
                    pin.setTranslationX(tx);
                    pin.setTranslationY(ty);
                    if(j== outputPathDef.size()-1) {
                        cosafare.setText("Raggiungi l'uscita...");
                    }
                    else{
                        cosafare.setText("I prodotti da prendere sono:");
                        for(int k=0; k<prodottiPercorso.size(); k++){
                            if(outputPathDef.get(j).lettera.equals(prodottiPercorso.get(k).id_nodo)){
                                s += db.getnomedaEAN(prodottiPercorso.get(k).EAN)+ " \n";
                            }
                        }
                        testo_prodotto.setText(s);
                        s="";
                    }
                }

                j++;

                if(j==outputPathDef.size()){

                    prossimo.setText("Finisci");
                    testo_prodotto.setText("");
                    prossimo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(percorso.this, home.class);
                            Toast.makeText(percorso.this, "Hai terminato la tua spesa!", Toast.LENGTH_LONG).show();
                            startActivity(intent);
                            finish();
                        }
                    });
                }

            }
        });
    }




    public void popolaNodi(String Id){
        points= new ArrayList<Point>();
        prodottiPercorso= new ArrayList<ProdottoPercorso>();
        String piva=db.getPiva(Id);
        String ean;
        String id_nodo;
        Point p1= new Point("a",db.getX("a",piva),db.getY("a",piva));
        points.add(p1);
        for(int i=0; i<db.getEAN(Id).size();i++){
            ean=db.getEAN(Id).get(i);
            id_nodo=db.getIdNodo(ean,piva);
            ProdottoPercorso pp= new ProdottoPercorso(id_nodo,ean,piva);
            prodottiPercorso.add(pp);
            Point p= new Point(id_nodo,db.getX(id_nodo,piva),db.getY(id_nodo,piva));
            boolean flag=true;
            for(Point poin : points){
                if(poin.lettera.equals(id_nodo)){
                    flag=false;
                }
            }
            if(flag){
                points.add(p);
            }
        }

    }


    private void outputdef() {
        outputPathDef = new ArrayList<Point>();
        for (int i = 0; i < tsp.getOutputPath().size(); i++) {
            for (int j = 0; j < points.size(); j++) {
                if (tsp.getOutputPath().get(i).equals(points.get(j).lettera)) {
                    outputPathDef.add(points.get(j));
                }
            }
        }
    }


    public float calclTraslX (int i) {
        float traslx;
        traslx = outputPathDef.get(i).x - outputPathDef.get(0).x;
        return traslx;
    }

    public float calclTraslY (int i){
        float trasly;
        trasly = outputPathDef.get(i).y - outputPathDef.get(0).y;
        return trasly;
    }
    public float getMolt(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(percorso.this, prodotti_carrello.class);
        intent.putExtra("carrello_id", id);
        intent.putExtra("carrello_nome", nomecarr);
        startActivity(intent);
        finish();
    }
}
