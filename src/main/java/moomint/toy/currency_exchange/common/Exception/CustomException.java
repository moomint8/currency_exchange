package moomint.toy.currency_exchange.common.Exception;

public class CustomException extends Exception {
    public CustomException(String message) {
        super("[ERROR] " + message);
    }
}
