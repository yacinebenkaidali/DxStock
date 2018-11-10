package Donnes;

import java.util.Date;

/**
 * Created by YacineBENKAIDALI on 8/5/2018.
 */
public class Articletmp {

    public int  QTE_LIVRE;
    public Date DATE_LIVRAISON;
    public int Code_CLTV;

    public String DESIGNATION;
    public double PRIX_VENTE;
    public int QTE_CHARGE;
    public String CODE_ARTICLE;
    public long Code_Bon;
    public double Montant;
    public String nom_client;


    public Articletmp(String DESIGNATION, int QTE_LIVRE, double PRIX_VENTE, int QTE_CHARGE) {
        this.DESIGNATION = DESIGNATION;
        this.QTE_LIVRE = QTE_LIVRE;
        this.PRIX_VENTE = PRIX_VENTE;
        this.QTE_CHARGE = QTE_CHARGE;
    }

    public Articletmp() {
    }
}