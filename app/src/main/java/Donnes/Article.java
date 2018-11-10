package Donnes;

/**
 * Created by YacineBENKAIDALI on 7/30/2018.
 */
public class Article {

    public String CODE_ARTICLE;
    public String DESIGNATION;
    public double PRIX_VENTE;
    public String CODETYPE;
    public double poids;
    public int QTE_CHARGE;
    public int QTE_VENDUE;
    public boolean checked;


    public Article()
    {

    }

    public Article(String CODE_ARTICLE, String DESIGNATION,String code_type, double PRIX_VENTE,
                   int QTE_CHARGE, int QTE_VENDUE) {


        this.CODE_ARTICLE = CODE_ARTICLE;
        this.DESIGNATION = DESIGNATION;
        this.CODETYPE = code_type;
        this.PRIX_VENTE = PRIX_VENTE;
        this.QTE_CHARGE = QTE_CHARGE;
        this.QTE_VENDUE = QTE_VENDUE;

    }

    @Override
    public String toString() {
        return "Article{" +
                "DESIGNATION='" + DESIGNATION + '\'' +
                ", PRIX_VENTE=" + PRIX_VENTE +
                ", poids=" + poids +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;

        Article article = (Article) o;

        return CODE_ARTICLE.equals(article.CODE_ARTICLE);

    }

    @Override
    public int hashCode() {
        return CODE_ARTICLE.hashCode();
    }
}