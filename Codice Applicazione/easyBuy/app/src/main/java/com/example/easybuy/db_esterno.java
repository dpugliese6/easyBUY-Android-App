package com.example.easybuy;

import android.util.Log;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class db_esterno {
    boolean cond=false;
    boolean internetOK=true;
    JSONArray table;

    public void insertUtente(String email,String password,String nome,String cognome,char sesso,String data_nascita ) {

        String[] dn = data_nascita.split("/");

        String q="INSERT INTO `UTENTE` (`Email`, `Password`, `Nome`, `Cognome`, `Sesso`,`Data_Nascita`) VALUES ('"+email+"', '"+password+"', '"+nome+"', '"+cognome+"', '"+sesso+"','"+dn[2]+"-"+dn[1]+"-"+dn[0]+"');";
        queryEC(q);

    }
    public void updateUtente(String email,String nome,String cognome,char sesso,String data_nascita ) {

        String[] dn = data_nascita.split("/");

        String q="UPDATE `UTENTE` SET `Nome` = '"+nome+"', `Cognome` = '"+cognome+"', `Sesso` = '"+sesso+"', `Data_Nascita` = '"+dn[2]+"-"+dn[1]+"-"+dn[0]+"'  WHERE `Email` = '"+email+"';";
        queryEC(q);

    }
    public void updatePassword(String email, String password ) {

        String q="UPDATE `UTENTE` SET `Password` = '"+password+"' WHERE `Email` = '"+email+"';";
        queryEC(q);

    }
    public void deleteUtente(String email) {
        String q="DELETE FROM `UTENTE` WHERE `Email` = '"+email+"'";
        queryEC(q);

    }
    public void insertLista( String Nome,String email ) {

        String q = "INSERT INTO `LISTA` (`Nome`) VALUES ( '" + Nome + "');";
        queryEC(q);
        if (internetOK) {
            q = "INSERT INTO `LISTA_UTENTE` (`id_lista`,`Email`) VALUES ((SELECT MAX(Id_lista) FROM LISTA LIMIT 1), '" + email + "');";
            queryEC(q);
        }

    }
    public void insertCarrello( String Nome ,String email) {

        String q="INSERT INTO `CARRELLO` (`Nome`) VALUES ('"+Nome+"');";
        queryEC(q);
        if (internetOK) {
            q = "INSERT INTO `CARRELLO_UTENTE` (`id_carrello`,`Email`) VALUES ((SELECT MAX(Id_carrello) FROM CARRELLO LIMIT 1), '" + email + "');";
            queryEC(q);
        }
    }
    public void insertListaProdotti( String id ,String ean) {

        String q="INSERT INTO `LISTA_PRODOTTI` (`Id_lista`,`Ean`) VALUES ("+id+",'"+ean+"');";
        queryEC(q);


    }
    public void insertCarrelloProdotti( String id ,String ean, String piva) {

        String q="INSERT INTO `CARRELLO_PRODOTTI` (`Id_carrello`,`Ean`,`Piva`) VALUES ("+id+",'"+ean+"','"+piva+"');";
        queryEC(q);

    }
    public void condividiLista( String id_lista, String utente ) {

        String q="INSERT INTO `LISTA_UTENTE` (`Id_lista`,`Email`) VALUES ('"+id_lista+"','"+utente+"');";
    queryEC(q);

    }
    public void condividiCarrello( String id_carrello, String utente ) {

        String q="INSERT INTO `CARRELLO_UTENTE` (`Id_carrello`,`Email`) VALUES ('"+id_carrello+"','"+utente+"');";
      queryEC(q);

    }
    public void cancellaLista(String id_lista, String utente){
        String q="DELETE FROM `LISTA_UTENTE` WHERE `Id_lista`="+id_lista+" AND `Email`='"+utente+"';";
        queryEC(q);

    }
    public void cancellaCarrello(String id_carrello, String utente){
        String q="DELETE FROM `CARRELLO_UTENTE` WHERE `Id_carrello`="+id_carrello+" AND `Email`='"+utente+"' ;";
        queryEC(q);

    }
    public void cancellaProdottoLista(String ean, String Id_lista){
        String q="DELETE FROM `LISTA_PRODOTTI` WHERE `Id_lista`="+Id_lista+" AND `Ean`="+ean+" ;";
        queryEC(q);
        //cosi se rimuovo un prodotto da una mia lista che ho condiviso anche all'altro utente verrà cancellato il prodotto dalla lista

    }
    public void cancellaProdottoCarrello(String ean, String piva,  String Id_carrello){
        String q="DELETE FROM `CARRELLO_PRODOTTI` WHERE `Id_carrello`="+Id_carrello+" AND `Ean`="+ean+" AND `Piva`="+piva+" ;";
        queryEC(q);
        //cosi se rimuovo un prodotto da una mia lista che ho condiviso anche all'altro utente verrà cancellato il prodotto dalla lista

    }
    static public void query(final String query) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {


                    String url = "http://easybuy20.altervista.org/db/query.php";
                    String urlParameters = "query="+query;
                    byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                    HttpURLConnection con;
                    try {
                        URL myurl = new URL(url);
                        con = (HttpURLConnection) myurl.openConnection();
                        con.setDoOutput(true);
                        con.setRequestMethod("POST");
                        con.setRequestProperty("User-Agent", "Java client");
                        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                        wr.write(postData);
                        con.getInputStream();
                        con.disconnect();
                    }catch(Exception e){

                    }

            }
        });

        thread.start();
    }
     public void queryEC(final String query) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {


                String url = "http://easybuy20.altervista.org/db/query.php";
                String urlParameters = "query="+query;
                byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                HttpURLConnection con;
                try {
                    URL myurl = new URL(url);
                    con = (HttpURLConnection) myurl.openConnection();
                    con.setDoOutput(true);
                    con.setRequestMethod("POST");
                    con.setRequestProperty("User-Agent", "Java client");
                    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                    wr.write(postData);
                    con.getInputStream();
                    con.disconnect();
                }catch(Exception e){
             internetOK=false;
                }

            }
        });

        thread.start();
         while(thread.isAlive()){}
    }
    public boolean credenzialiOk(final String mail, final String pass){

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                    String url = "http://easybuy20.altervista.org/db/credenzialiok.php";
                    String urlParameters = "mail="+mail+"&pass="+pass;
                    byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                    HttpURLConnection con;

                    try {
                        URL myurl = new URL(url);
                        con = (HttpURLConnection) myurl.openConnection();
                        con.setDoOutput(true);
                        con.setRequestMethod("POST");
                        con.setRequestProperty("User-Agent", "Java client");
                        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                        wr.write(postData);



                        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String line;
                        line = br.readLine();
                        if(line.equals("1")){
                            cond=true;
                        }else{
                            cond=false;
                        }

                        con.disconnect();
                    }catch(Exception e){
                        internetOK=false;
                    }

            }
        });

        thread.start();
        while(thread.isAlive()){}

        return cond;

    }
    public boolean mailLibera(final String mail){


        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {


                    String url = "http://easybuy20.altervista.org/db/maillibera.php";
                    String urlParameters = "mail="+mail;
                    byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                    HttpURLConnection con;

                    try {
                        URL myurl = new URL(url);
                        con = (HttpURLConnection) myurl.openConnection();
                        con.setDoOutput(true);
                        con.setRequestMethod("POST");
                        con.setRequestProperty("User-Agent", "Java client");
                        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                        wr.write(postData);



                        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String line;
                        line = br.readLine();
                        if(line.equals("1")){
                            cond=true;
                        }else{
                            cond=false;
                        }

                        con.disconnect();
                    }catch(Exception e){
                        internetOK=false;
                    }

            }
        });

        thread.start();
        while(thread.isAlive()){}

        return cond;

    }
    public JSONArray scarica(final String tabella){

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {



                    String url = "http://easybuy20.altervista.org/db/scarica.php";
                    String urlParameters = "tabella="+tabella;
                    byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                    HttpURLConnection con;


                    try {
                        URL myurl = new URL(url);
                        con = (HttpURLConnection) myurl.openConnection();
                        con.setDoOutput(true);
                        con.setRequestMethod("POST");
                        con.setRequestProperty("User-Agent", "Java client");
                        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                        wr.write(postData);

                        con.getInputStream();

                        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String line;
                        line = br.readLine();
                        table =new JSONArray(line);




                        con.disconnect();
                    }catch(Exception e){
                      internetOK=false;
               }

            }
        });

        thread.start();
        while(thread.isAlive()){}

        return table;

    }








}
