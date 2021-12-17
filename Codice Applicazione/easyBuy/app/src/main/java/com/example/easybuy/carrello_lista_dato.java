package com.example.easybuy;

public class carrello_lista_dato {

    private String id;
    private String nome;

    public carrello_lista_dato(String id, String nome){
        this.id=id;
        this.nome=nome;
    }

    public void setNome(String nome){
        this.nome=nome;
    }

    public void setId(String id){
        this.id=id;
    }

    public String getNome(){
        return nome;
    }

    public String getId(){
        return id;
    }
}
