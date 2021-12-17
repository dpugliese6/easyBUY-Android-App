package com.example.easybuy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CreaDB extends SQLiteOpenHelper
{
    public static final String DBNAME="db_interno"; //Nome DataBase

    public CreaDB(Context context) {
        super(context, DBNAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {



        String s="CREATE TABLE IF NOT EXISTS SUPERMERCATO(\n" +
                "Piva CHAR(11) PRIMARY KEY,\n" +
                "Nome VARCHAR(100),\n" +
                "Mappa VARCHAR(50),\n" +
                "Immagine VARCHAR(50),\n" +
                "Citta VARCHAR(50),\n" +
                "Via VARCHAR(50),\n" +
                "Civico SMALLINT" +
                ")";
        db.execSQL(s);

               s= "CREATE TABLE IF NOT EXISTS VOLANTINO(\n" +
                "Data_vol DATE,\n" +
                "Piva CHAR(11),\n" +
                "Immagine VARCHAR(50),\n" +
                "PRIMARY KEY(Data_vol,Piva),\n" +
                "FOREIGN KEY(Piva) REFERENCES SUPERMERCATO(Piva) ON DELETE CASCADE\n" +
                ")";
        db.execSQL(s);
                s="CREATE TABLE IF NOT EXISTS NODO(\n" +
                "Numero CHAR(1),\n" +
                "Piva CHAR(11),\n" +
                "X FLOAT,\n" +
                "Y FLOAT,\n" +
                "PRIMARY KEY(Numero,Piva),\n" +
                "FOREIGN KEY(Piva) REFERENCES SUPERMERCATO(Piva) ON DELETE CASCADE\n" +
                ")";
        db.execSQL(s);
                s="CREATE TABLE IF NOT EXISTS PRODOTTO(\n" +
                "Ean char(13) PRIMARY KEY,\n" +
                "Nome VARCHAR(100),\n" +
                "Descrizione VARCHAR(300),\n" +
                "Marchio VARCHAR(50),\n" +
                "Categoria VARCHAR(50),\n" +
                "Immagine VARCHAR(50)\n" +
                ")";
        db.execSQL(s);

                s="CREATE TABLE IF NOT EXISTS SUPERMERCATO_PRODOTTO(\n" +
                "Piva CHAR(11),\n" +
                "Ean CHAR(13),\n" +
                "Prezzo FLOAT,\n" +
                "Numero CHAR(1),\n" +
                "PRIMARY KEY(Piva,Ean),\n" +
                "FOREIGN KEY(Ean) REFERENCES PRODOTTO(Ean) ON DELETE CASCADE,\n" +
                "FOREIGN KEY(Piva,Numero) REFERENCES NODO(Piva,Numero)\n" +
                ")";
        db.execSQL(s);

                s="CREATE TABLE IF NOT EXISTS UTENTE(\n" +
                "Email VARCHAR(50) PRIMARY KEY,\n" +
                "Password VARCHAR(20) NOT NULL,     \n" +
                "Nome VARCHAR(20),\n" +
                "Cognome VARCHAR(30),\n" +
                "Sesso CHAR(6),\n" +
                "Data_Nascita DATE\n" +
                ")";
        db.execSQL(s);

              s="CREATE TABLE IF NOT EXISTS CARRELLO(\n" +
                "Id_carrello INT PRIMARY KEY,\n" +
                      "Nome VARCHAR(20)\n"+
                ")";
        db.execSQL(s);

                s="CREATE TABLE IF NOT EXISTS CARRELLO_UTENTE(\n" +
                "Id_carrello INT,\n" +
                "Email VARCHAR(50),\n" +
                "PRIMARY KEY(Id_carrello, Email),\n" +
                "FOREIGN KEY(Id_carrello) REFERENCES CARRELLO(Id_carrello) ON DELETE CASCADE,\n" +
                "FOREIGN KEY(Email) REFERENCES UTENTE(Email) ON DELETE CASCADE\n" +
                ")";
        db.execSQL(s);

                s="CREATE TABLE IF NOT EXISTS CARRELLO_PRODOTTI(\n" +
                "Id_carrello INT,\n" +
                "Piva CHAR(11),\n" +
                "Ean CHAR(13),\n" +
                "PRIMARY KEY(Id_carrello,Piva,Ean),\n" +
                "FOREIGN KEY(Id_carrello) REFERENCES CARRELLO(Id_carrello) ON DELETE CASCADE,\n" +
                "FOREIGN KEY(Piva) REFERENCES SUPERMERCATO(Piva) ON DELETE CASCADE,\n" +
                "FOREIGN KEY(Ean) REFERENCES PRODOTTO(Ean) ON DELETE CASCADE\n" +
                ")";
        db.execSQL(s);

                s="CREATE TABLE IF NOT EXISTS LISTA(\n" +
                "Id_lista INT PRIMARY KEY,\n" +
                        "Nome VARCHAR(20)\n"+
                ")";
        db.execSQL(s);

                s="CREATE TABLE IF NOT EXISTS LISTA_UTENTE(\n" +
                "Id_lista INT,\n" +
                "Email VARCHAR(50),\n" +
                "PRIMARY KEY(Id_lista, Email),\n" +
                "FOREIGN KEY(Id_lista) REFERENCES LISTA(Id_lista) ON DELETE CASCADE,\n" +
                "FOREIGN KEY(Email) REFERENCES UTENTE(Email) ON DELETE CASCADE\n" +
                ")";
        db.execSQL(s);
                s="CREATE TABLE IF NOT EXISTS LISTA_PRODOTTI(\n" +
                "Id_lista INT,\n" +
                "Ean CHAR(13),\n" +
                "PRIMARY KEY(Id_lista,Ean),\n" +
                "FOREIGN KEY(Id_lista) REFERENCES LISTA(Id_lista) ON DELETE CASCADE,\n" +
                "FOREIGN KEY(Ean) REFERENCES PRODOTTO(Ean) ON DELETE CASCADE\n" +
                ")";

        db.execSQL(s);

        s="CREATE TABLE IF NOT EXISTS credenziali (\n" +
            "  email varchar(50) NOT NULL,\n" +
            "  password varchar(20) NOT NULL,\n" +
            "  resta_collegato tinyint(1) NOT NULL\n" +
            ")";

        db.execSQL(s);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    { }
}