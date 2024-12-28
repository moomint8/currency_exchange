package moomint.toy.currency_exchange.account.domain.aggregate.vo;

import lombok.Builder;
import lombok.Getter;
import moomint.toy.currency_exchange.account.domain.aggregate.enums.TransactionType;

import java.math.BigDecimal;

@Getter
@Builder
public class TransactionResponseVO {
    private String message;
    private TransactionType transactionType;
    private String accountNo;
    private String currencyCode;
    private BigDecimal amount;
    private BigDecimal remainingBalance;

    @Builder

    public TransactionResponseVO(String message, TransactionType transactionType, String accountNo, String currencyCode, BigDecimal amount, BigDecimal remainingBalance) {
        this.message = message;
        this.transactionType = transactionType;
        this.accountNo = accountNo;
        this.currencyCode = currencyCode;
        this.amount = amount;
        this.remainingBalance = remainingBalance;
    }
}