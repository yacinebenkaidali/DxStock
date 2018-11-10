package Donnes;

/**
 * Created by YacineBENKAIDALI on 8/26/2018.
 */
public class Livraison_detail {

    public long CODE_BON;
    public String CODE_ARTICLE;
    public Double  QTE_LIVRE;
    public double PRIX_ACHAT;


    public Double QTE_TMP;

    public Livraison_detail(int CODE_BON, String CODE_ARTICLE, Double QTE_LIVRE, Double VERSEMENT_TRANCHE) {
        this.CODE_BON = CODE_BON;
        this.CODE_ARTICLE = CODE_ARTICLE;
        this.QTE_LIVRE = QTE_LIVRE;
    }

    public Livraison_detail() {
    }

    @Override
    public String toString() {
        return "Livraison_detail{" +
                "CODE_BON=" + CODE_BON +
                ", CODE_ARTICLE='" + CODE_ARTICLE + '\'' +
                ", QTE_LIVRE=" + QTE_LIVRE +
                '}';
    }
}