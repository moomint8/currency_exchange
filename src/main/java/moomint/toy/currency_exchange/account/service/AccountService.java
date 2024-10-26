package moomint.toy.currency_exchange.account.service;

import moomint.toy.currency_exchange.common.Exception.NotLoggedInException;

public interface AccountService {
    String createAccount(String currency) throws NotLoggedInException;
}
