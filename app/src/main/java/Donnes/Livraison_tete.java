package Donnes;

import java.sql.Date;

/**
 * Created by YacineBENKAIDALI on 7/31/2018.
 */
public class Livraison_tete {

    public Date DATE_LIVRAISON;
    public int CODE_CLTV;
    public long CODE_BON;
    public String MODE_REGLEMENT;
    public double MONTANT;
    public Double  VERSEMENT_TRANCHE;

    public Livraison_tete() {
    }

    public Livraison_tete(Date DATE_LIVRAISON, Double QTE_LIVRE, int CODE_CLTV, long CODE_BON,Double  VERSEMENT_TRANCHE) {
        this.DATE_LIVRAISON = DATE_LIVRAISON;
        this.CODE_CLTV = CODE_CLTV;
        this.CODE_BON = CODE_BON;
        this.VERSEMENT_TRANCHE=VERSEMENT_TRANCHE;
    }

    @Override
    public String toString() {
        return "Livraison_tete{" +
                "DATE_LIVRAISON=" + DATE_LIVRAISON +
                ", CODE_CLTV='" + CODE_CLTV + '\'' +
                ", CODE_ARTICLE='" + CODE_BON + '\'' +
                '}';
    }
}