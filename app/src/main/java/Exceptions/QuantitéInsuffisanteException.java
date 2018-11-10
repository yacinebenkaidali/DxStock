package Exceptions;

/**
 * Created by YacineBENKAIDALI on 8/29/2018.
 */
public class QuantitéInsuffisanteException extends  Exception {
    public QuantitéInsuffisanteException() {

    }

    public QuantitéInsuffisanteException(String detailMessage) {
        super(detailMessage);
    }
}