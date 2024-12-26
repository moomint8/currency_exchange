package moomint.toy.currency_exchange.common.Exception;

public class NotAccountOwnerException extends CustomException {
    public NotAccountOwnerException() {
        super("소유권이 없는 계좌입니다.");
    }
}