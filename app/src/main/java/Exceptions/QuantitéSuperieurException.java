package Exceptions;

/**
 * Created by YacineBENKAIDALI on 8/29/2018.
 */
public class QuantitéSuperieurException extends Exception{
    public QuantitéSuperieurException() {
    }

    public QuantitéSuperieurException(String detailMessage) {
        super(detailMessage);
    }
}