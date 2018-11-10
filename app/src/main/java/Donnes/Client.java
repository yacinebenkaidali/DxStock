package Donnes;
import java.util.Date;

/**
 * Created by YacineBENKAIDALI on 7/30/2018.
 */
public class Client {
    public int Code_CLTV;
    public String Code_VAN;
    public String NOM_CLIENT;
    public String Tel;
    public String Adresse;
    public Date DATE_CREATION;
    public int NO_ROUTE;


    public Client(){

    }

    public Client(String adresse,int code_CLTV, String code_VAN,
                  Date DATE_CREATION, String NOM_CLIENT,String tel, int NO_ROUTE) {
        Code_CLTV = code_CLTV;
        Code_VAN = code_VAN;
        this.NOM_CLIENT = NOM_CLIENT;
        Tel = tel;
        Adresse = adresse;
        this.DATE_CREATION = DATE_CREATION;
        this.NO_ROUTE = NO_ROUTE;

    }

    @Override
    public String toString() {
        return "Client{" +
                "Code_CLTV=" + Code_CLTV +
                ", NOM_CLIENT='" + NOM_CLIENT + '\'' +
                ", Adresse='" + Adresse + '\'' +
                '}';
    }
}