package BDD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by YacineBENKAIDALI on 7/30/2018.
 */
public class DxBDDHelper extends SQLiteOpenHelper
{
    public static final String CreateArticle="CREATE TABLE ARTICLE ("+
            "  CODE_ARTICLE VARCHAR(15)  NOT NULL PRIMARY KEY," +
            "  DESIGNATION VARCHAR(130)  NOT NULL ," +
            "  PRIX_VENTE DOUBLE PRECISION," +
            "  CODE_TYPE VARCHAR(15)," +
            "  QTE_CHARGE NUMERIC(12, 2)," +
            "  QTE_VENDUE NUMERIC(12, 2)" +
            ");" ;
    public static final String CreateClient ="CREATE TABLE CLIENTS_VAN (" +
            "  ADRESSE VARCHAR(120)  ," +
            "  CODE_CLTV SMALLINT NOT NULL  PRIMARY KEY," +
            "  CODE_VAN VARCHAR(9)  NOT NULL ," +
            "  DATE_CREATION DATE," +
            "  NOM_CLIENT VARCHAR(40)  NOT NULL ," +
            "  TEL VARCHAR(20)  ," +
            "  NO_ROUTE SMALLINT," +
            "  FOREIGN KEY (CODE_VAN) REFERENCES VENDEUR (CODE_VAN)" +
            ");";

    public static final String CreateVendeur ="CREATE TABLE VENDEUR (" +
            "  CODE_VAN VARCHAR(9) NOT NULL  PRIMARY KEY, " +
            "  NOM VARCHAR(130)  NOT NULL," +
            "  PASSWORD VARCHAR(130)  NOT NULL"+
            ");";


    public static final String Createlivraisontete="CREATE TABLE LIVRAISON_TETE (" +
            "       CODE_BON  Integer  NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "       DATE_LIVRAISON DATE DEFAULT NULL," +
            "       CODE_CLTV SMALLINT NOT NULL ," +
            "       MODE_REGLEMENT VARCHAR(12)," +
            "       MONTANT DOUBLE PRECISION," +
            "       VERSEMENT_TRANCHE DOUBLE PRECISION," +
            "       FOREIGN KEY (CODE_CLTV) REFERENCES CLIENTS_VAN (CODE_CLTV)" +
            ");";

    public  static final String Createlivraisondetail="CREATE TABLE LIVRAISON_DETAIL(" +
            "    CODE_LIVRAISON_DET  Integer  NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "    CODE_BON  Integer  NOT NULL," +
            "    CODE_ARTICLE VARCHAR(15)  NOT NULL ," +
            "    QTE_LIVRE int DEFAULT 0," +
            "    PRIX_ACHAT DOUBLE PRECISION," +
            "    FOREIGN KEY (CODE_ARTICLE) REFERENCES ARTICLE (CODE_ARTICLE)," +
            "    FOREIGN KEY (CODE_BON) REFERENCES LIVRAISON_TETE (CODE_BON)" +
            ");";


    public static  final String CreateReclamation="CREATE TABLE RECLAMATION (" +
            "    CODE_REC Integer  PRIMARY KEY AUTOINCREMENT," +
            "    CODE_CLTV SMALLINT NOT NULL ," +
            "    SUJET VARCHAR," +
            "    RECLAMATION VARCHAR," +
            "    FOREIGN KEY (CODE_CLTV) REFERENCES CLIENTS_VAN (CODE_CLTV)" +
            ");";

    public DxBDDHelper(Context context)
    {
        super(context,"DxBDD", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CreateVendeur);db.execSQL(CreateClient);db.execSQL(CreateArticle);
        db.execSQL(Createlivraisontete);db.execSQL(Createlivraisondetail);db.execSQL(CreateReclamation);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
    }
}