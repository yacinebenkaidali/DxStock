package BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import Donnes.Article;
import Donnes.Articletmp;
import Donnes.Client;
import Donnes.Livraison_detail;
import Donnes.Livraison_tete;
import Donnes.Vendeur;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by YacineBENKAIDALI on 7/30/2018.
 */
public class Datasrc {


    SQLiteOpenHelper dbhelp;
    public SQLiteDatabase db;

    public Datasrc(Context context) {

        dbhelp = new DxBDDHelper(context);

    }

    public void open() {
        db = dbhelp.getWritableDatabase();
    }

    public void close() {

        dbhelp.close();
    }


    public List<Client> findallClient(Context context, String CODE_VAN) {
        List<Client> clients = new ArrayList<>();

        db = context.openOrCreateDatabase("DxBDD", Context.MODE_PRIVATE, null);
        String ven_cli = "select CLIENTS_VAN.* from CLIENTS_VAN inner join VENDEUR on VENDEUR.CODE_VAN=CLIENTS_VAN.CODE_VAN where VENDEUR.CODE_VAN=\"" + CODE_VAN + "\";";

        try{
            Cursor c = db.rawQuery(ven_cli, null);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        Client c1 = new Client();
                        c1.Adresse = c.getString(c.getColumnIndex("ADRESSE"));
                        c1.Code_CLTV = c.getInt(c.getColumnIndex("CODE_CLTV"));
                        c1.Code_VAN = c.getString(c.getColumnIndex("CODE_VAN"));
                        c1.DATE_CREATION = Date.valueOf(c.getString(c.getColumnIndex("DATE_CREATION")));
                        c1.NOM_CLIENT = c.getString(c.getColumnIndex("NOM_CLIENT"));
                        c1.Tel = c.getString(c.getColumnIndex("TEL"));
                        c1.NO_ROUTE =c.getInt(c.getColumnIndex("NO_ROUTE"));
                        // Adding client to list
                        clients.add(c1);
                    } while (c.moveToNext());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return clients;
    }

    public List<Client> filtreClient(Context context,String searchTerm)
    {

        List<Client> clients = new ArrayList<>();

        db = context.openOrCreateDatabase("DxBDD", Context.MODE_PRIVATE, null);
        String ven_cli = "SELECT * FROM CLIENTS_VAN WHERE NOM_CLIENT LIKE '%"+searchTerm+"%' OR CODE_CLTV LIKE '%"+searchTerm+"%'";
        try{
            Cursor c = db.rawQuery(ven_cli, null);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        Client c1 = new Client();
                        c1.Adresse = c.getString(c.getColumnIndex("ADRESSE"));
                        c1.Code_CLTV = c.getInt(c.getColumnIndex("CODE_CLTV"));
                        c1.Code_VAN = c.getString(c.getColumnIndex("CODE_VAN"));
                        c1.DATE_CREATION = Date.valueOf(c.getString(c.getColumnIndex("DATE_CREATION")));
                        c1.NOM_CLIENT = c.getString(c.getColumnIndex("NOM_CLIENT"));
                        c1.Tel = c.getString(c.getColumnIndex("TEL"));
                        c1.NO_ROUTE =c.getInt(c.getColumnIndex("NO_ROUTE"));
                        // Adding client to list
                        clients.add(c1);
                    } while (c.moveToNext());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return clients;
    }


    public List<Article> findallarticles(Context context, String CODE_VAN, int CODE_CLTV) {
        List<Article> articles = new ArrayList<>();
        db = context.openOrCreateDatabase("DxBDD", Context.MODE_PRIVATE, null);
        String allarticlesLivraison = "select * from ARTICLE where ARTICLE.CODE_ARTICLE not in (" +
                "select ARTICLE.CODE_ARTICLE from VENDEUR inner join CLIENTS_VAN on CLIENTS_VAN.CODE_VAN=VENDEUR.CODE_VAN" +
                "                     inner join LIVRAISON_TETE on CLIENTS_VAN.CODE_CLTV=LIVRAISON_TETE.CODE_CLTV" +
                "                     inner join LIVRAISON_DETAIL on LIVRAISON_DETAIL.CODE_BON=LIVRAISON_TETE.CODE_BON" +
                "                     inner join ARTICLE on ARTICLE.CODE_ARTICLE=LIVRAISON_DETAIL.CODE_ARTICLE " +
                "                     where CLIENTS_van.CODE_CLTV=" + CODE_CLTV + " and VENDEUR.CODE_VAN=\""+ CODE_VAN + "\");";

        Cursor c = db.rawQuery(allarticlesLivraison, null);

        if (c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    Article a = new Article();
                    a.CODE_ARTICLE = c.getString(c.getColumnIndex("CODE_ARTICLE"));
                    a.DESIGNATION = c.getString(c.getColumnIndex("DESIGNATION"));
                    a.CODETYPE = c.getString(c.getColumnIndex("CODE_TYPE"));
                    a.PRIX_VENTE = c.getDouble(c.getColumnIndex("PRIX_VENTE"));
                    a.QTE_CHARGE = c.getInt(c.getColumnIndex("QTE_CHARGE"));
                    a.QTE_VENDUE =c.getInt(c.getColumnIndex("QTE_VENDUE"));
                    // Adding article to list
                    articles.add(a);
                } while (c.moveToNext());
            }
        }
        return articles;
    }

    public List<Article> filtreArtcile(Context context,String searchTerm ) {
        List<Article> articles = new ArrayList<>();
        db = context.openOrCreateDatabase("DxBDD", Context.MODE_PRIVATE, null);
        String allarticlesLivraison = "select * from ARTICLE where DESIGNATION LIKE '%"+searchTerm+"%' OR CODE_ARTICLE LIKE '%"+searchTerm+"%'";

        Cursor c = db.rawQuery(allarticlesLivraison, null);

        if (c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    Article a = new Article();
                    a.CODE_ARTICLE = c.getString(c.getColumnIndex("CODE_ARTICLE"));
                    a.DESIGNATION = c.getString(c.getColumnIndex("DESIGNATION"));
                    a.CODETYPE = c.getString(c.getColumnIndex("CODE_TYPE"));
                    a.PRIX_VENTE = c.getDouble(c.getColumnIndex("PRIX_VENTE"));
                    a.QTE_CHARGE = c.getInt(c.getColumnIndex("QTE_CHARGE"));
                    a.QTE_VENDUE =c.getInt(c.getColumnIndex("QTE_VENDUE"));
                    // Adding article to list
                    articles.add(a);
                } while (c.moveToNext());
            }
        }
        return articles;
    }

    public List<Articletmp> findallarticlesacheter(String CODE_VAN, int CODE_CLTV,long CODE_BON) {
        List<Articletmp> articles = new ArrayList<>();

        String alllivraison = "select DESIGNATION,PRIX_VENTE,QTE_LIVRE,QTE_CHARGE,CLIENTS_VAN.CODE_CLTV,ARTICLE.CODE_ARTICLE,DATE_LIVRAISON,Livraison_tete.CODE_BON,LIVRAISON_TETE.MONTANT " +
                "from VENDEUR inner join CLIENTS_VAN on CLIENTS_VAN.CODE_VAN=VENDEUR.CODE_VAN" +
                "                     inner join LIVRAISON_TETE on CLIENTS_VAN.CODE_CLTV=LIVRAISON_TETE.CODE_CLTV" +
                "                     inner join LIVRAISON_DETAIL on LIVRAISON_DETAIL.CODE_BON=LIVRAISON_TETE.CODE_BON" +
                "                     inner join ARTICLE on ARTICLE.CODE_ARTICLE=LIVRAISON_DETAIL.CODE_ARTICLE " +
                "                     where CLIENTS_van.CODE_CLTV=" + CODE_CLTV + " and VENDEUR.CODE_VAN=\"" + CODE_VAN + "\";";


        String alllivraisonparbon="select DESIGNATION,PRIX_VENTE,QTE_LIVRE,QTE_CHARGE,CLIENTS_VAN.CODE_CLTV,ARTICLE.CODE_ARTICLE,DATE_LIVRAISON,Livraison_tete.CODE_BON,LIVRAISON_TETE.MONTANT " +
                "from VENDEUR inner join CLIENTS_VAN on CLIENTS_VAN.CODE_VAN=VENDEUR.CODE_VAN" +
                "                     inner join LIVRAISON_TETE on CLIENTS_VAN.CODE_CLTV=LIVRAISON_TETE.CODE_CLTV" +
                "                     inner join LIVRAISON_DETAIL on LIVRAISON_DETAIL.CODE_BON=LIVRAISON_TETE.CODE_BON" +
                "                     inner join ARTICLE on ARTICLE.CODE_ARTICLE=LIVRAISON_DETAIL.CODE_ARTICLE " +
                "                     where CLIENTS_van.CODE_CLTV=" + CODE_CLTV + " and VENDEUR.CODE_VAN=\"" + CODE_VAN + "\" and LIVRAISON_TETE.CODE_BON="+CODE_BON+";";;

        Cursor c;
        if (CODE_BON==-1) {
            c= db.rawQuery(alllivraison, null);
        }else {
            c= db.rawQuery(alllivraisonparbon, null);
        }
        if (c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Articletmp a = new Articletmp();
                    a.DESIGNATION = c.getString(c.getColumnIndex("DESIGNATION"));
                    a.PRIX_VENTE = Double.parseDouble(c.getString(c.getColumnIndex("PRIX_VENTE")));
                    a.QTE_LIVRE = Integer.parseInt(c.getString(c.getColumnIndex("QTE_LIVRE")));
                    a.QTE_CHARGE = Integer.parseInt(c.getString(c.getColumnIndex("QTE_CHARGE")));
                    a.Code_CLTV = Integer.parseInt(c.getString(c.getColumnIndex("CODE_CLTV")));
                    a.CODE_ARTICLE = c.getString(c.getColumnIndex("CODE_ARTICLE"));
                    a.Code_Bon = Long.parseLong(c.getString(c.getColumnIndex("CODE_BON")));
                    a.Montant = Double.parseDouble(c.getString(c.getColumnIndex("MONTANT")));
                    try {
                        a.DATE_LIVRAISON = df.parse(c.getString(c.getColumnIndex("DATE_LIVRAISON")));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    // Adding client to list
                    articles.add(a);
                } while (c.moveToNext());
            }
        }
        return articles;
    }

    public List<Articletmp> findallbon(int CODE_CLTV,String date)
    {
        List<Articletmp> tmp=new ArrayList<>();
        String findallbon="select NOM_CLIENT,LIVRAISON_TETE.CODE_BON,MONTANT,DATE_LIVRAISON from CLIENTS_VAN inner join  LIVRAISON_TETE on LIVRAISON_TETE.CODE_CLTV=CLIENTS_VAN.CODE_CLTV where CLIENTS_VAN.CODE_CLTV="+CODE_CLTV+";";

        String findallbonwithdate="select NOM_CLIENT,LIVRAISON_TETE.CODE_BON,MONTANT,DATE_LIVRAISON from CLIENTS_VAN inner join  LIVRAISON_TETE on LIVRAISON_TETE.CODE_CLTV=CLIENTS_VAN.CODE_CLTV where ( CLIENTS_VAN.CODE_CLTV="+CODE_CLTV+" and strftime(\"%Y-%m-%d\", date(DATE_LIVRAISON))=strftime(\"%Y-%m-%d\", date(\"" + date + "\")));";
        Cursor c;
        if (date.equals("")){
            c=db.rawQuery(findallbon,null);
        }else {
            c=db.rawQuery(findallbonwithdate,null);
        }
        if (c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Articletmp a = new Articletmp();
                    a.Code_Bon = Long.parseLong(c.getString(c.getColumnIndex("CODE_BON")));
                    a.nom_client = c.getString(c.getColumnIndex("NOM_CLIENT"));
                    a.Montant = Double.parseDouble(c.getString(c.getColumnIndex("MONTANT")));
                    try {
                        a.DATE_LIVRAISON = df.parse(c.getString(c.getColumnIndex("DATE_LIVRAISON")));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    // Adding client to list
                    tmp.add(a);
                } while (c.moveToNext());
            }
        }
        return tmp;
    }

    public List<Articletmp> filtreBon(Context context,String searchTerm)
    {
        List<Articletmp> tmp=new ArrayList<>();
        db = context.openOrCreateDatabase("DxBDD", Context.MODE_PRIVATE, null);
        String findallbon="select NOM_CLIENT,LIVRAISON_TETE.CODE_BON,MONTANT,DATE_LIVRAISON from CLIENTS_VAN inner join LIVRAISON_TETE" +
                " on LIVRAISON_TETE.CODE_CLTV=CLIENTS_VAN.CODE_CLTV WHERE CODE_BON LIKE '%"+searchTerm+"%'";
        Cursor c=db.rawQuery(findallbon,null);
        if (c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Articletmp a = new Articletmp();
                    a.Code_Bon = Long.parseLong(c.getString(c.getColumnIndex("CODE_BON")));
                    a.nom_client = c.getString(c.getColumnIndex("NOM_CLIENT"));
                    a.Montant = Double.parseDouble(c.getString(c.getColumnIndex("MONTANT")));
                    try {
                        a.DATE_LIVRAISON = df.parse(c.getString(c.getColumnIndex("DATE_LIVRAISON")));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    // Adding client to list
                    tmp.add(a);
                } while (c.moveToNext());
            }
        }
        return tmp;
    }



    public Client createClient(Client t) {
        ContentValues values = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        values.put("ADRESSE", t.Adresse);
        values.put("CODE_CLTV", t.Code_CLTV);
        values.put("CODE_VAN", t.Code_VAN);
        values.put("DATE_CREATION", dateFormat.format(t.DATE_CREATION));
        values.put("NOM_CLIENT", t.NOM_CLIENT);
        values.put("TEL", t.Tel);
        values.put("NO_ROUTE", t.NO_ROUTE);
        db.insert("CLIENTS_VAN", null, values);

        return t;
    }

    public void createArticle(Article t) {
        ContentValues values = new ContentValues();
        values.put("CODE_ARTICLE", t.CODE_ARTICLE);
        values.put("DESIGNATION", t.DESIGNATION);
        values.put("CODE_TYPE", t.CODETYPE);
        values.put("PRIX_VENTE", t.PRIX_VENTE);
        values.put("QTE_CHARGE", t.QTE_CHARGE);
        values.put("QTE_VENDUE", t.QTE_VENDUE);

        db.insert("ARTICLE", null, values);


    }

    public Vendeur createVendeur(Vendeur t) {
        ContentValues values = new ContentValues();
        values.put("CODE_VAN", t.CODE_VAN);
        values.put("NOM", t.NOM);
        values.put("PASSWORD", t.PASSWORD);

        db.insert("VENDEUR", null, values);

        return t;
    }



    public long createLivraisontete(Livraison_tete t) {
        ContentValues values = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        values.put("DATE_LIVRAISON", dateFormat.format(t.DATE_LIVRAISON));
        values.put("MODE_REGLEMENT", t.MODE_REGLEMENT);
        values.put("MONTANT", t.MONTANT);
        values.put("CODE_CLTV", t.CODE_CLTV);
        values.put("VERSEMENT_TRANCHE", t.VERSEMENT_TRANCHE);

        long id = db.insert("Livraison_tete", null, values);
        return id;
    }

    public void createLivraisondetail(Livraison_detail t) {
        ContentValues values = new ContentValues();
        values.put("CODE_BON", t.CODE_BON);
        values.put("CODE_ARTICLE", t.CODE_ARTICLE);
        values.put("QTE_LIVRE", t.QTE_LIVRE);
        values.put("PRIX_ACHAT",t.PRIX_ACHAT);

        db.insert("LIVRAISON_DETAIL", null, values);

    }



    public void createreclamation(String recalamtion, String sujet, int CODE_CLTV) {
        ContentValues values = new ContentValues();
        values.put("CODE_CLTV", CODE_CLTV);
        values.put("RECLAMATION", recalamtion);
        values.put("SUJET", sujet);

        db.insert("RECLAMATION", null, values);
    }


    public void updateArticle(String CODE_ARTICLE, double quantit, boolean Acheter) {
        double qte_vendue = 0.0;
        double qte_charge = 0.0;
        Cursor c = db.rawQuery("select QTE_VENDUE,QTE_CHARGE from ARTICLE where  ARTICLE.CODE_ARTICLE=" + CODE_ARTICLE, null);
        if (c.getCount() > 0) {
            if (c.moveToFirst()) {
                qte_vendue = Double.parseDouble(c.getString(0));
                qte_charge = Double.parseDouble(c.getString(1));
            }

            if (Acheter) {
                //Achat des produits
                qte_vendue = quantit + qte_vendue;
                qte_charge = qte_charge - quantit;
            } else {
                //Remboursement_Activity
                qte_vendue = quantit - qte_vendue;
                qte_charge = qte_charge + quantit;
            }

            ContentValues values = new ContentValues();
            values.put("QTE_VENDUE", qte_vendue);
            values.put("QTE_CHARGE", qte_charge);

            db.update("ARTICLE", values, "CODE_ARTICLE" + " = ? ", new String[]{CODE_ARTICLE});
        }
    }


    public void updateLivraisondetail(Livraison_detail l) {
        ContentValues values = new ContentValues();
        values.put("QTE_LIVRE", l.QTE_LIVRE);
        db.update("LIVRAISON_DETAIL", values, "CODE_BON" + " = ? " + " and  CODE_ARTICLE" + " = ? ", new String[]{String.valueOf(l.CODE_BON), l.CODE_ARTICLE});
    }




    public void updateMontant(Livraison_detail l) {
        Cursor c = db.rawQuery("select Montant from Livraison_tete where code_bon=" + l.CODE_BON, null);
        double montant = 0.0;
        if ((c.getCount() > 0)) {
            if (c.moveToFirst())
            {
                montant = Double.parseDouble(c.getString(0));
            }
        }
        montant=montant-l.PRIX_ACHAT*l.QTE_TMP+l.PRIX_ACHAT*l.QTE_LIVRE;
        ContentValues values=new ContentValues();
        values.put("MONTANT",montant);
        db.update("LIVRAISON_TETE",values,"CODE_BON="+l.CODE_BON,null);
    }


    public void supprimerLivraisondetail(Livraison_detail l) {
        db.delete("LIVRAISON_DETAIL", "CODE_BON" + " = ? " + " and  CODE_ARTICLE" + " = ? ", new String[]{String.valueOf(l.CODE_BON), l.CODE_ARTICLE});
    }

    public void cleardatabaseClient() {
        open();
        db.delete("CLIENTS_VAN",null, null);
    }
    public void cleardatabaseArticle() {
        open();
        db.delete("ARTICLE ",null, null);
    }

    public void cleardatabaseVendeur()
    {
        open();
        db.delete("VENDEUR",null, null);
    }
}