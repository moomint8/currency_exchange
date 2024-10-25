package moomint.toy.currency_exchange.common.Exception;

public class NotLoggedInException extends CustomException {
    public NotLoggedInException() {
        super("로그인이 필요한 서비스입니다.");
    }
}