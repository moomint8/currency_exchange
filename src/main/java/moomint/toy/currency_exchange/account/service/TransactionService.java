package moomint.toy.currency_exchange.account.service;

import moomint.toy.currency_exchange.account.dto.TransactionResultDTO;
import moomint.toy.currency_exchange.account.dto.TransactionDTO;
import moomint.toy.currency_exchange.common.Exception.InvalidCurrencyException;
import moomint.toy.currency_exchange.common.Exception.NotAccountOwnerException;
import moomint.toy.currency_exchange.common.Exception.NotLoggedInException;

public interface TransactionService {
    TransactionResultDTO deposit(TransactionDTO transactionDTO) throws NotAccountOwnerException, NotLoggedInException, InvalidCurrencyException;
}