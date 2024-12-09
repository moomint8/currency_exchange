package moomint.toy.currency_exchange.account.service;

import moomint.toy.currency_exchange.account.dto.AccountDTO;
import moomint.toy.currency_exchange.common.Exception.NotLoggedInException;

import java.util.List;

public interface AccountService {
    String createAccount(String currency) throws NotLoggedInException;
    List<AccountDTO> getAllAccounts() throws NotLoggedInException;
}