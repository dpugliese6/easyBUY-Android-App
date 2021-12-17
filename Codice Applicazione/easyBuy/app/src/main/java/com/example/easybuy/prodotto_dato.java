package com.example.easybuy;

public class prodotto_dato {
    private String ean;
    private String piva;
    private String id_carrello_lista;
    private String nome;
    private String descrizione;
    private String marchio;
    private String categoria;
    private String immagine;
 private float prezzo;

    public prodotto_dato(String ean,String id_lista, String nome, String descrizione, String marchio, String categoria, String immagine){
        this.ean=ean;
        id_carrello_lista=id_lista;
        this.nome=nome;
        this.descrizione=descrizione;
        this.marchio=marchio;
        this.categoria=categoria;
        this.immagine=immagine;
    }

    public prodotto_dato(String ean,String piva,String id_carrello, String nome, String descrizione, String marchio, String categoria,float prezzo,String immagine){
        this.ean=ean;
        this.piva=piva;
        id_carrello_lista=id_carrello;
        this.nome=nome;
        this.descrizione=descrizione;
        this.marchio=marchio;
        this.categoria=categoria;
        this.prezzo=prezzo;
        this.immagine=immagine;
    }


    public prodotto_dato(String ean,String piva, String nome, String marchio, String categoria,String descrizione,float prezzo,String immagine){
        this.ean=ean;
        this.piva=piva;
        this.nome=nome;
        this.descrizione=descrizione;
        this.marchio=marchio;
        this.categoria=categoria;
        this.prezzo=prezzo;
        this.immagine=immagine;
    }
    public prodotto_dato(String ean, String nome, String marchio, String categoria,String descrizione, String immagine){
        this.ean=ean;
        this.nome=nome;
        this.descrizione=descrizione;
        this.marchio=marchio;
        this.categoria=categoria;
        this.immagine=immagine;
    }
    public prodotto_dato(String ean, String nome, String marchio, String categoria){
        this.ean=ean;
        this.nome=nome;
        this.marchio=marchio;
        this.categoria=categoria;
    }
    public void setEan(String ean){
        this.ean=ean;
    }

    public void setPiva(String piva){
        this.piva=piva;
    }

    public void setNome(String nome){
        this.nome=nome;
    }

    public void setDescrizione(String descrizione){
        this.descrizione=descrizione;
    }

    public void setMarchio(String marchio){
        this.marchio=marchio;
    }

    public void setCategoria(String categoria){
        this.categoria=categoria;
    }

    public void setImmagine(String immagine) {this.immagine = immagine;}

    public void setPrezzo(float prezzo){
        this.prezzo=prezzo;
    }

    public String getEan(){
        return ean;
    }

    public String getPiva(){
        return piva;
    }

    public String getNome(){
        return nome;
    }

    public String getDescrizione(){
        return descrizione;
    }

    public String getMarchio(){
        return marchio;
    }

    public String getCategoria(){
        return categoria;
    }

    public String getId(){
        return id_carrello_lista;
    }

    public String getImmagine() {
        return immagine;
    }

    public float getPrezzo(){
        return prezzo;
    }


}
