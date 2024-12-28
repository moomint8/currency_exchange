package moomint.toy.currency_exchange.account.service;

import lombok.extern.slf4j.Slf4j;
import moomint.toy.currency_exchange.account.dto.TransactionResultDTO;
import moomint.toy.currency_exchange.account.dto.AccountDTO;
import moomint.toy.currency_exchange.account.dto.TransactionDTO;
import moomint.toy.currency_exchange.account.dto.UpdateBalanceDTO;
import moomint.toy.currency_exchange.common.Exception.InvalidCurrencyException;
import moomint.toy.currency_exchange.common.Exception.NotAccountOwnerException;
import moomint.toy.currency_exchange.common.Exception.NotLoggedInException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    private final AccountService accountService;

    @Autowired
    public TransactionServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public TransactionResultDTO deposit(TransactionDTO transactionDTO) throws NotAccountOwnerException, NotLoggedInException, InvalidCurrencyException {

        try {

            UpdateBalanceDTO updateBalanceDTO = new UpdateBalanceDTO(transactionDTO.accountNo(), transactionDTO.currency(), transactionDTO.amount());

            AccountDTO accountDTO = accountService.updateBalance(updateBalanceDTO);

            return new TransactionResultDTO(transactionDTO.transactionType(),
                    accountDTO.accountNo(), accountDTO.currency(), transactionDTO.amount(), accountDTO.balance());

        } catch (Exception e) {
            log.error("Error in deposit", e);
            throw e;
        }
    }
}
