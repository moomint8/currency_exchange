package moomint.toy.currency_exchange.common.Exception;

public class DuplicateException extends CustomException {
    public DuplicateException(String target) {
        super("중복된 " + target + "입니다.");
    }
}