package com.example.easybuy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.Toast;

import com.example.easybuy.CreaDB;

import net.ricecode.similarity.JaroWinklerStrategy;
import net.ricecode.similarity.SimilarityStrategy;
import net.ricecode.similarity.StringSimilarityService;
import net.ricecode.similarity.StringSimilarityServiceImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GestisciDB
{
    private CreaDB db_interno;

    public GestisciDB(Context ctx)
    {

        db_interno=new CreaDB(ctx);
    }

    public void inserisci_credenziali(String e, String p, int r_c)
    {
        SQLiteDatabase db=db_interno.getWritableDatabase();
        ContentValues cv=new ContentValues(); //nuova tupla
        cv.put("email", e);
        cv.put("password", p);
        cv.put("resta_collegato", r_c);
        try
        {
            db.insert("credenziali", null,cv);
        }
        catch (SQLiteException sqle)
        {
         // Gestione delle eccezioni
        }
    }

    public Cursor getData(String sql){
        SQLiteDatabase db=db_interno.getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    public boolean ifLogin(){
        Cursor a= getData("SELECT * FROM credenziali WHERE resta_collegato=1 AND email IS NOT NULL AND password IS NOT NULL");
        if(a.getCount()>0){
            return true;
        }else{
            logout();
            return false;
        }
    }

    public String getPsw(){
        Cursor c = getData("SELECT password FROM credenziali LIMIT 1");
        c.moveToFirst();
        return c.getString(0);
    }

    public String getEmail(){
        Cursor c= getData("SELECT email FROM credenziali LIMIT 1");
        c.moveToFirst();
        return c.getString(0);

    }

    public int getRC(){
        Cursor c= getData("SELECT resta_collegato FROM credenziali LIMIT 1");
        c.moveToFirst();
        return c.getInt(0);

    }

    public String getNome(String email){
        Cursor c= getData("SELECT Nome FROM UTENTE WHERE Email='"+email+"' LIMIT 1");
        c.moveToFirst();
        return c.getString(0);

    }

    public String getCognome(String email){
    Cursor c= getData("SELECT Cognome FROM UTENTE WHERE Email='"+email+"' LIMIT 1");
    c.moveToFirst();
    return c.getString(0);
    }

    public String getSesso(String email){
        Cursor c= getData("SELECT Sesso FROM UTENTE WHERE Email='"+email+"' LIMIT 1");
        c.moveToFirst();
        return c.getString(0);

    }

    public String getDN(String email){
        Cursor c= getData("SELECT Data_Nascita FROM UTENTE WHERE Email='"+email+"' LIMIT 1");
        c.moveToFirst();
        String s[]=c.getString(0).split("-");
        return s[2]+"/"+s[1]+"/"+s[0];

    }

    public String getEandaIdNodo(String id,String PIVA){
        Cursor c=getData("SELECT Ean FROM SUPERMERCATO_PRODOTTO WHERE Numero='"+ id +"' AND Piva='"+ PIVA +"' LIMIT 1");
        c.moveToFirst();
        return c.getString(0);
    }


    public String getnomedaEAN(String EAN){
        Cursor c= getData("SELECT Nome FROM PRODOTTO WHERE Ean='"+EAN+"' LIMIT 1");

        if(!(c.getCount()==0)){
            c.moveToFirst();
        return c.getString(0);}else{
            return "£";

        }

    }

    public void insertUtente(String email,String password,String nome,String cognome,char sesso,String data_nascita ) {
        SQLiteDatabase database=db_interno.getReadableDatabase();
        Cursor c = getData("SELECT * FROM utente WHERE Email=\"" + email + "\"");
        if (c.getCount() == 0) {
            String sql = "INSERT INTO UTENTE(Email,Password,Nome,Cognome,Sesso,Data_Nascita) VALUES (?,?,?,?,?,"+data_nascita+")";

            SQLiteStatement statement = database.compileStatement(sql);
            statement.clearBindings();

            statement.bindString(1, email);
            statement.bindString(2, password);
            statement.bindString(3, nome);
            statement.bindString(4, cognome);
            statement.bindString(5, String.valueOf(sesso));

            statement.executeInsert();
        }
        database.close();
    }

    public void logout(){
        SQLiteDatabase db=db_interno.getWritableDatabase();
       db.delete("credenziali",null,null);

    }

    public void svuota(String tabella){
        SQLiteDatabase db=db_interno.getWritableDatabase();
        db.delete(tabella,null,null);

    }

    public boolean caricaSupermercato() throws JSONException {

        db_esterno dbe = new db_esterno();
        JSONArray j = dbe.scarica("SUPERMERCATO");
        if (dbe.internetOK) {

            SQLiteDatabase db = db_interno.getWritableDatabase();
            if ((j != null)) {
                svuota("SUPERMERCATO");
                for (int i = 0; i < j.length(); i++) {
                    JSONObject r = j.getJSONObject(i);
                    ContentValues riga = new ContentValues(); //nuova tupla
                    riga.put("Piva", r.get("Piva").toString());
                    riga.put("Nome", r.get("Nome").toString());
                    riga.put("Mappa", r.get("Mappa").toString());
                    riga.put("Immagine", r.get("Immagine").toString());
                    riga.put("Citta", r.get("Citta").toString());
                    riga.put("Via", r.get("Via").toString());
                    riga.put("Civico", Integer.parseInt(r.get("Civico").toString()));

                    db.insert("SUPERMERCATO", null, riga);
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }


    }
/*
    public boolean caricaVolantino() throws JSONException {

        db_esterno dbe = new db_esterno();
        JSONArray j = dbe.scarica("VOLANTINO");
        if (dbe.internetOK) {
            SQLiteDatabase db = db_interno.getWritableDatabase();
            if ((j != null)) {
                svuota("VOLANTINO");
                for (int i = 0; i < j.length(); i++) {
                    JSONObject r = j.getJSONObject(i);
                    ContentValues riga = new ContentValues(); //nuova tupla
                    riga.put("Data_vol", r.get("Data_vol").toString()); //Non so se funziona
                    riga.put("Piva", r.get("Piva").toString());
                    riga.put("Immagine", r.get("Immagine").toString());
                    db.insert("VOLANTINO", null, riga);
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }


    }*/

    public boolean caricaProdotto() throws JSONException {

        db_esterno dbe = new db_esterno();
        JSONArray j = dbe.scarica("PRODOTTO");
        if (dbe.internetOK) {
            SQLiteDatabase db = db_interno.getWritableDatabase();
            if ((j != null)) {
                svuota("PRODOTTO");
                for (int i = 0; i < j.length(); i++) {
                    JSONObject r = j.getJSONObject(i);
                    ContentValues riga = new ContentValues(); //nuova tupla
                    riga.put("Ean", r.get("Ean").toString());
                    riga.put("Nome", r.get("Nome").toString());
                    riga.put("Descrizione", r.get("Descrizione").toString());
                    riga.put("Marchio", r.get("Marchio").toString());
                    riga.put("Categoria", r.get("Categoria").toString());
                    riga.put("Immagine", r.get("Immagine").toString());
                    db.insert("PRODOTTO", null, riga);
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public boolean caricaCarrelloUtente() throws JSONException {

        db_esterno dbe = new db_esterno();
        JSONArray j = dbe.scarica("CARRELLO_UTENTE");
        if (dbe.internetOK) {
            SQLiteDatabase db = db_interno.getWritableDatabase();
            if ((j != null)) {
                svuota("CARRELLO_UTENTE");
                for (int i = 0; i < j.length(); i++) {
                    JSONObject r = j.getJSONObject(i);
                    ContentValues riga = new ContentValues(); //nuova tupla
                    riga.put("Id_carrello", Integer.parseInt(r.get("Id_carrello").toString()));
                    riga.put("Email", r.get("Email").toString());
                    db.insert("CARRELLO_UTENTE", null, riga);
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public boolean caricaCarrelloProdotti() throws JSONException {

        db_esterno dbe = new db_esterno();
        JSONArray j = dbe.scarica("CARRELLO_PRODOTTI");
        if (dbe.internetOK) {
            SQLiteDatabase db = db_interno.getWritableDatabase();
            if ((j != null)) {
                svuota("CARRELLO_PRODOTTI");
                for (int i = 0; i < j.length(); i++) {
                    JSONObject r = j.getJSONObject(i);
                    ContentValues riga = new ContentValues(); //nuova tupla
                    riga.put("Id_carrello", Integer.parseInt(r.get("Id_carrello").toString()));
                    riga.put("Piva", r.get("Piva").toString());
                    riga.put("Ean", r.get("Ean").toString());
                    db.insert("CARRELLO_PRODOTTI", null, riga);
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public boolean caricaCarrello() throws JSONException {

        db_esterno dbe = new db_esterno();
        JSONArray j = dbe.scarica("CARRELLO");
        if (dbe.internetOK) {
            SQLiteDatabase db = db_interno.getWritableDatabase();
            if ((j != null)) {
                svuota("CARRELLO");
                for (int i = 0; i < j.length(); i++) {
                    JSONObject r = j.getJSONObject(i);
                    ContentValues riga = new ContentValues(); //nuova tupla
                    riga.put("Id_carrello", Integer.parseInt(r.get("Id_carrello").toString()));
                    riga.put("Nome", r.get("Nome").toString());
                    db.insert("CARRELLO", null, riga);
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public boolean caricaListaUtente() throws JSONException {

        db_esterno dbe = new db_esterno();
        JSONArray j = dbe.scarica("LISTA_UTENTE");
        if (dbe.internetOK) {
            SQLiteDatabase db = db_interno.getWritableDatabase();
            if ((j != null)) {
                svuota("LISTA_UTENTE");
                for (int i = 0; i < j.length(); i++) {
                    JSONObject r = j.getJSONObject(i);
                    ContentValues riga = new ContentValues(); //nuova tupla
                    riga.put("Id_lista", Integer.parseInt(r.get("Id_lista").toString()));
                    riga.put("Email", r.get("Email").toString());
                    db.insert("LISTA_UTENTE", null, riga);
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public boolean caricaListaProdotti() throws JSONException {

        db_esterno dbe = new db_esterno();
        JSONArray j = dbe.scarica("LISTA_PRODOTTI");
        if (dbe.internetOK) {
            SQLiteDatabase db = db_interno.getWritableDatabase();
            if ((j != null)) {
                svuota("LISTA_PRODOTTI");
                for (int i = 0; i < j.length(); i++) {
                    JSONObject r = j.getJSONObject(i);
                    ContentValues riga = new ContentValues(); //nuova tupla
                    riga.put("Id_lista", Integer.parseInt(r.get("Id_lista").toString()));
                    riga.put("Ean", r.get("Ean").toString());
                    db.insert("LISTA_PRODOTTI", null, riga);
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public boolean caricaLista() throws JSONException {

        db_esterno dbe = new db_esterno();
        JSONArray j = dbe.scarica("LISTA");
        if (dbe.internetOK) {
            SQLiteDatabase db = db_interno.getWritableDatabase();
            if ((j != null)) {
                svuota("LISTA");
                for (int i = 0; i < j.length(); i++) {
                    JSONObject r = j.getJSONObject(i);
                    ContentValues riga = new ContentValues(); //nuova tupla
                    riga.put("Id_lista", Integer.parseInt(r.get("Id_lista").toString()));
                    riga.put("Nome", r.get("Nome").toString());
                    db.insert("LISTA", null, riga);
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public boolean caricaUtente() throws JSONException {

        db_esterno dbe = new db_esterno();
        JSONArray j = dbe.scarica("UTENTE");
        if (dbe.internetOK) {
            SQLiteDatabase db = db_interno.getWritableDatabase();
            if ((j != null)) {
                svuota("UTENTE");
                for (int i = 0; i < j.length(); i++) {
                    JSONObject r = j.getJSONObject(i);
                    ContentValues riga = new ContentValues(); //nuova tupla
                    riga.put("Email", r.get("Email").toString());
                    riga.put("Password", r.get("Password").toString());
                    riga.put("Nome", r.get("Nome").toString());
                    riga.put("Cognome", r.get("Cognome").toString());
                    riga.put("Sesso", r.get("Sesso").toString());
                    riga.put("Data_Nascita", r.get("Data_Nascita").toString());
                    db.insert("UTENTE", null, riga);
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public boolean caricaSupermercatoProdotto() throws JSONException {

        db_esterno dbe = new db_esterno();
        JSONArray j = dbe.scarica("SUPERMERCATO_PRODOTTO");
        if (dbe.internetOK) {
            SQLiteDatabase db = db_interno.getWritableDatabase();
            if ((j != null)) {
                svuota("SUPERMERCATO_PRODOTTO");
                for (int i = 0; i < j.length(); i++) {
                    JSONObject r = j.getJSONObject(i);
                    ContentValues riga = new ContentValues(); //nuova tupla
                    riga.put("Piva", r.get("Piva").toString());
                    riga.put("Ean", r.get("Ean").toString());
                    riga.put("Prezzo", Float.parseFloat(r.get("Prezzo").toString()));
                    riga.put("Numero", r.get("Numero").toString());
                    db.insert("SUPERMERCATO_PRODOTTO", null, riga);
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean caricaNodo() throws JSONException {

        db_esterno dbe = new db_esterno();
        JSONArray j = dbe.scarica("NODO");
        if (dbe.internetOK) {
            SQLiteDatabase db = db_interno.getWritableDatabase();
            if ((j != null)) {
                svuota("NODO");
                for (int i = 0; i < j.length(); i++) {
                    JSONObject r = j.getJSONObject(i);
                    ContentValues riga = new ContentValues(); //nuova tupla
                    riga.put("Numero", r.get("Numero").toString());
                    riga.put("Piva", r.get("Piva").toString());
                    riga.put("X", Float.parseFloat(r.get("X").toString()));
                    riga.put("Y", Float.parseFloat(r.get("Y").toString()));
                    db.insert("NODO", null, riga);
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }


    }

    public void ricerca(String target, ArrayList<String> vettoreOrdinato, ArrayList<String> vettoreOrdinatoEan) {
        SQLiteDatabase database = db_interno.getReadableDatabase();

        ArrayList<String> prodotti = new ArrayList<>();
        ArrayList<String> vettoreEan = new ArrayList<>();

        String[] columns = {"Ean","Nome","Descrizione","Marchio","Categoria","Immagine"};
        Cursor c = database.query("PRODOTTO", columns, null, null, null, null, null);

        int index = c.getColumnIndexOrThrow("Nome");
        int index2 = c.getColumnIndexOrThrow("Ean");


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            prodotti.add(c.getString(index));
            vettoreEan.add(c.getString(index2));
        }


        ArrayList<String> vettoreScelto = vettoreScelto(target, prodotti);
        ArrayList<String> vettoreSceltoEan = vettoreSceltoEan(target, prodotti, vettoreEan);

        vettoreDefinitivo(target, vettoreScelto, vettoreSceltoEan, vettoreOrdinato, vettoreOrdinatoEan);

    }

    public ArrayList<String> vettoreScelto(String target, ArrayList<String> prodotti){

        ArrayList<String> vettoreScelto = new ArrayList<>();
        SimilarityStrategy strategy = new JaroWinklerStrategy();
        StringSimilarityService service = new StringSimilarityServiceImpl(strategy);


        for(int i=0;i< prodotti.size();i++){

            String source = prodotti.get(i);
            double score = service.score(source, target);
            if(score>0.6){
                vettoreScelto.add(prodotti.get(i));
            }
        }

        return vettoreScelto;
    }

    public ArrayList<String> vettoreSceltoEan(String target, ArrayList<String> prodotti, ArrayList<String> vettoreEan){

        ArrayList<String> vettoreSceltoEan = new ArrayList<>();
        SimilarityStrategy strategy = new JaroWinklerStrategy();
        StringSimilarityService service = new StringSimilarityServiceImpl(strategy);


        for(int i=0;i< prodotti.size();i++){

            String source = prodotti.get(i);
            double score = service.score(source, target);
            if(score>0.6){
                vettoreSceltoEan.add(vettoreEan.get(i));
            }
        }

        return vettoreSceltoEan;
    }

    public void vettoreDefinitivo(String target, ArrayList<String> vettoreScelto, ArrayList<String> vettoreSceltoEan, ArrayList<String> vettoreOrdinato, ArrayList<String> vettoreOrdinatoEan){

        SimilarityStrategy strategy = new JaroWinklerStrategy();
        StringSimilarityService service = new StringSimilarityServiceImpl(strategy);

        int j = 0;
        double temp = 0;
        double score = 0;

        for (int i = 0; i < vettoreScelto.size(); i++) {

            String source = vettoreScelto.get(i);
            score = service.score(source, target);

            if (temp < score) {
                temp = score;
                j = i;
            }
        }

        if (vettoreScelto.size() > 0) {
            vettoreOrdinato.add(vettoreScelto.get(j));
            vettoreOrdinatoEan.add(vettoreSceltoEan.get(j));
            vettoreScelto.remove(j);
            vettoreSceltoEan.remove(j);
            vettoreDefinitivo(target, vettoreScelto, vettoreSceltoEan, vettoreOrdinato, vettoreOrdinatoEan);
        }
    }

    public List<carrello_lista_dato> getCarrelliFromUtente (String Email) {
        List <carrello_lista_dato> lista=new LinkedList();
        carrello_lista_dato ca;

        Cursor c= getData("SELECT cu.id_carrello,c.nome FROM carrello_utente cu,carrello c WHERE cu.email=\""+Email+"\" AND cu.id_carrello=c.id_carrello ");
        while(c.moveToNext()){
            ca=new carrello_lista_dato(c.getString(0),c.getString(1));
            lista.add(ca);
        }

        return lista;
    }

    public List<carrello_lista_dato> getListeFromUtente (String Email) {
        List <carrello_lista_dato> lista=new LinkedList();
        carrello_lista_dato ca;

        Cursor c= getData("SELECT lu.id_lista,l.nome FROM lista_utente lu,lista l WHERE lu.email=\""+Email+"\" AND lu.id_lista=l.id_lista ");
        while(c.moveToNext()){
            ca=new carrello_lista_dato(c.getString(0),c.getString(1));
            lista.add(ca);
        }

        return lista;
    }

    public List<prodotto_dato> getProdottiFromLista(String id_lista){
        List  lista=new LinkedList();
        prodotto_dato p;
        Cursor c=getData("SELECT p.ean,lp.id_lista,p.nome,p.descrizione,p.marchio,p.categoria, p.immagine FROM lista_prodotti lp, prodotto p WHERE lp.id_lista="+id_lista+" AND lp.ean=p.ean ");
        while (c.moveToNext()){
            p=new prodotto_dato(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6));
            lista.add(p);
        }
        return lista;
    }

    public List<prodotto_dato> getProdottiFromCarrello(String id_carrello){

        List  lista=new LinkedList();
        prodotto_dato p;
        Cursor c=getData("SELECT p.ean,sp.piva,cp.id_carrello,p.nome,p.descrizione,p.marchio,p.categoria,sp.prezzo, p.immagine FROM carrello_prodotti cp, prodotto p,supermercato_prodotto sp WHERE cp.id_carrello="+id_carrello+" AND cp.ean=p.ean AND cp.piva=sp.piva AND Sp.ean=p.ean ");
        while (c.moveToNext()){
            p=new prodotto_dato(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getFloat(7),c.getString(8));
            lista.add(p);
        }
        return lista;
    }
    public prodotto_dato getProdottidaEan(String ean){

        prodotto_dato p;
        Cursor c=getData("SELECT Ean, Nome, Marchio, Categoria, Descrizione, Immagine FROM PRODOTTO WHERE Ean='"+ean+"'");

        c.moveToFirst();
        p=new prodotto_dato(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5));
        return p;
    }
    public List<compara_dato> getSupermercatidaEan(String ean){
        compara_dato p;
        List  lista=new LinkedList();
        Cursor c=getData("SELECT p.Ean, s.Piva, sp.Prezzo, s.Nome, s.Immagine FROM PRODOTTO p JOIN SUPERMERCATO_PRODOTTO sp on p.Ean=sp.Ean join SUPERMERCATO s on sp.Piva=s.Piva WHERE p.Ean='"+ean+"' ORDER BY sp.Prezzo");
        while (c.moveToNext()){
            p=new compara_dato(c.getString(4),c.getString(3),c.getFloat(2),c.getString(1),c.getString(0));
            lista.add(p);}

        return lista;
    }

    public Boolean cancellaLista(String id_lista, String utente){
        SQLiteDatabase db=db_interno.getReadableDatabase();
        return db.delete("LISTA_UTENTE","id_lista="+id_lista+" AND Email=\""+utente+"\"" , null)>0;

    }

    public Boolean cancellaCarrello(String id_carrello, String utente){
        SQLiteDatabase db=db_interno.getReadableDatabase();
        return db.delete("CARRELLO_UTENTE","id_carrello="+id_carrello+" AND Email=\""+utente+"\"", null)>0;
    }

    public Boolean cancellaProdottoLista(String ean, String Id_lista){
        SQLiteDatabase db=db_interno.getReadableDatabase();
        return db.delete("LISTA_PRODOTTI","ean=" +ean+ " AND Id_lista="+Id_lista , null)>0;
    }

    public Boolean cancellaProdottoCarrello(String ean, String piva,  String Id_carrello){
        SQLiteDatabase db=db_interno.getReadableDatabase();
        return db.delete("CARRELLO_PRODOTTI","ean=" +ean+ " AND Id_carrello="+Id_carrello+" AND piva="+piva , null)>0;
    }

    public String NomeById_lista(String id_lista){
        SQLiteDatabase db=db_interno.getReadableDatabase();
        String q="SELECT Nome FROM Lista WHERE id_lista="+id_lista+" LIMIT 1";
        Cursor c = db.rawQuery(q, null);
        while(c.moveToNext()){
            q=c.getString(0);
        }
        return q;
    }

    public String NomeById_carrello(String id_carrello){
        SQLiteDatabase db=db_interno.getReadableDatabase();
        String q="SELECT Nome FROM Carrello WHERE id_carrello="+id_carrello+" LIMIT 1";
        Cursor c = db.rawQuery(q, null);
        while(c.moveToNext()){
            q=c.getString(0);
        }
        return q;
    }
    public String getImmaginedaIva(String iva){
        SQLiteDatabase db=db_interno.getReadableDatabase();
        String q="SELECT Immagine FROM Supermercato WHERE Piva='"+iva+"' LIMIT 1";
        Cursor c = db.rawQuery(q, null);
        while(c.moveToNext()){
            q=c.getString(0);
        }
        return q;
    }

public boolean isPercorribile(String id_carrello){
    boolean cond=true;
        Cursor c1=getData("SELECT * FROM CARRELLO_PRODOTTI WHERE Id_carrello="+id_carrello);
    if(c1.getCount()>0) {

        Cursor c2 = getData("SELECT DISTINCT Piva FROM CARRELLO_PRODOTTI WHERE Id_carrello="+ id_carrello);
        if(c2.getCount()>1) {cond=false;
           }
    }else{
        cond=false;
    }

        return cond;
}
//metodi ettore
public String getPiva(String Id_carello){
    Cursor c=getData("SELECT Piva FROM CARRELLO_PRODOTTI WHERE Id_carrello= "+Id_carello+" LIMIT 1");
    c.moveToFirst();
    return c.getString(0);
}


    public ArrayList<String> getEAN(String Id_carello){
        Cursor c=getData("SELECT Ean FROM CARRELLO_PRODOTTI WHERE Id_carrello="+Id_carello);
        ArrayList<String> eanList= new ArrayList<String>();

        while(c.moveToNext()) {
            eanList.add(c.getString(0));
        }
        return eanList;
    }

    public String getIdNodo(String EAN,String PIVA){
        Cursor c=getData("SELECT Numero FROM SUPERMERCATO_PRODOTTO WHERE Ean='"+EAN+"' AND Piva='"+ PIVA+"' LIMIT 1");
        c.moveToFirst();
        return c.getString(0);
    }


    public String getNomeProdotto(String EAN){
        Cursor c=getData("SELECT Nome FROM PRODOTTO WHERE Ean= '"+EAN+"' LIMIT 1");
        c.moveToFirst();
        return c.getString(0);
    }


    public float getX(String Id,String PIVA) {
        Log.d("test","SELECT X FROM NODO WHERE Numero= '"+Id+"' AND Piva= '"+ PIVA+"' LIMIT 1");
        Cursor c=getData("SELECT X FROM NODO WHERE Numero= '"+Id+"' AND Piva= '"+ PIVA+ "' LIMIT 1");
        c.moveToFirst();
        return c.getFloat(0);
    }

    public float getY(String Id,String PIVA) {
        Cursor c=getData("SELECT Y FROM NODO WHERE Numero= '"+Id+"' AND Piva= '"+ PIVA+ "' LIMIT 1");
        c.moveToFirst();
        return c.getFloat(0);
    }
    public String getMappadaID(String id){
        Cursor c=getData("SELECT DISTINCT s.Mappa FROM CARRELLO_PRODOTTI cp join SUPERMERCATO s on cp.Piva=s.Piva WHERE Id_carrello="+id);
        c.moveToFirst();
        return c.getString(0);
    }
    public List<String> getSupermercati(){

        List  lista=new LinkedList();
        Cursor c=getData("SELECT Piva FROM supermercato");
        while (c.moveToNext()){
            lista.add(c.getString(0));
        }
        return lista;
    }
    public String getNomeSupermercato(String piva){
        Cursor c=getData("SELECT Nome FROM supermercato WHERE Piva=\""+piva+"\"");
        c.moveToNext();
        return c.getString(0);
    }

    public String getCittaSupermercato(String piva){
        Cursor c=getData("SELECT Citta FROM supermercato WHERE Piva=\""+piva+"\"");
        c.moveToNext();
        return c.getString(0);
    }

    public String getViaSupermercato(String piva){
        Cursor c=getData("SELECT Via FROM supermercato WHERE Piva=\""+piva+"\"");
        c.moveToNext();
        return c.getString(0);
    }

    public String getCivicoSupermercato(String piva){
        Cursor c=getData("SELECT Civico FROM supermercato WHERE Piva=\""+piva+"\"");
        c.moveToNext();
        return c.getString(0);
    }

    public String getMappaSupermercato(String piva){
        Cursor c=getData("SELECT Mappa FROM supermercato WHERE Piva=\""+piva+"\"");
        c.moveToNext();
        return c.getString(0);
    }

    public String getImmagineSupermercato(String piva){
        Cursor c=getData("SELECT Immagine FROM supermercato WHERE Piva=\""+piva+"\"");
        c.moveToNext();
        return c.getString(0);
    }

    public ArrayList<String> getCategorieSupermercato(String piva){
        ArrayList<String> a =new ArrayList<>();
        Cursor c=getData("SELECT DISTINCT Categoria FROM PRODOTTO p,SUPERMERCATO_PRODOTTO sp WHERE p.Ean=sp.Ean AND sp.Piva=\""+piva+"\"");
        while(c.moveToNext()){
            a.add(c.getString(0));
        }
        return a;
    }

    public List<prodotto_dato> getProdottiFromCategoriaSupermercato(String piva,String categoria){

        List  lista=new LinkedList();
        prodotto_dato p;
        if(categoria.equals("Tutti i prodotti")) {
            Cursor c = getData("SELECT p.ean,sp.piva,p.nome,p.marchio,p.categoria,p.descrizione,sp.prezzo, p.immagine FROM  prodotto p,supermercato_prodotto sp WHERE sp.piva='" + piva + "' AND sp.ean=p.ean ORDER BY categoria ");
            while (c.moveToNext()) {
                p = new prodotto_dato(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getFloat(6),c.getString(7));
                Log.i("prodotto",p.getNome()+"*****");
                lista.add(p);
            }
        }else{
            Cursor c = getData("SELECT p.ean,sp.piva,p.nome,p.marchio,p.categoria,p.descrizione,sp.prezzo, p.immagine FROM  prodotto p,supermercato_prodotto sp WHERE sp.piva='" + piva + "' AND sp.ean=p.ean AND p.categoria='" + categoria + "'");//la queri è corretta perche funzione per gli altri supermercati però su altervista mettendo famila e succhi mi restituisce 2 tuple quindi l'errore è sul mio database interno
            while (c.moveToNext()) {
                p = new prodotto_dato(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getFloat(6),c.getString(7));
                lista.add(p);
            }
        }
        return lista;
    }
}