package moomint.toy.currency_exchange.common.Exception;

public class InvalidCurrencyException extends CustomException {
    public InvalidCurrencyException() { super("잘못된 통화 정보입니다."); }
}