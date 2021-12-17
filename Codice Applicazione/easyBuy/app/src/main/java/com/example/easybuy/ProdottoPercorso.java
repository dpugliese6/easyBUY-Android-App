package com.example.easybuy;
public class ProdottoPercorso {
    public  String id_nodo;
    public  String EAN;
    public  String PIVA;

    public ProdottoPercorso(String lettera_nodo, String ean, String piva) {
        this.id_nodo = lettera_nodo;
        this.EAN = ean;
        this.PIVA = piva;
    }
}
