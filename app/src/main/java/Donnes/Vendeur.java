package Donnes;
/**
 * Created by YacineBENKAIDALI on 7/31/2018.
 */
public class Vendeur {

    public String CODE_VAN;
    public String NOM;
    public String PASSWORD;

    public Vendeur() {
    }

    public Vendeur(String CODE_VAN, String NOM, String PASSWORD) {
        this.CODE_VAN = CODE_VAN;
        this.NOM = NOM;
        this.PASSWORD = PASSWORD;
    }

    @Override
    public String toString() {
        return "Vendeur{" +
                "CODE_VAN='" + CODE_VAN + '\'' +
                ", NOM='" + NOM + '\'' +
                ", PASSWORD='" + PASSWORD + '\'' +
                '}';
    }
}