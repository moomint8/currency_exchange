package moomint.toy.currency_exchange.common.util;

public class RegularExpression {

    public static final String USERNAME = "^[0-9a-zA-Z]{5,12}+$";
    public static final String USERNAME_MESSAGE = "숫자, 알파벳 5자 ~ 12자가 아닙니다.";

    public static final String PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{8,16}$";
    public static final String PASSWORD_MESSAGE = "숫자, 대문자, 소문자 각 1개 이상 8자 ~ 16자가 아닙니다.";

    public static final String NAME = "^[가-힣a-zA-Z]{1,16}$";
    public static final String NAME_MESSAGE = "이름, 알파벳 1자 ~ 16자가 아닙니다.";
}