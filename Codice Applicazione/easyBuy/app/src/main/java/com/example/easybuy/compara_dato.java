package com.example.easybuy;

public class compara_dato {
    String immagine;
    String nome;
    float prezzo;
    String piva;
    String ean;

    public compara_dato(String imm, String nom, float pre,String p,String e){
        immagine=imm;
        nome=nom;
        prezzo=pre;
        piva=p;
        ean=e;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public String getImmagine() {
        return immagine;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public String getEan() {
        return ean;
    }

    public String getPiva() {
        return piva;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public void setPiva(String piva) {
        this.piva = piva;
    }
}
