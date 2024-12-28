package moomint.toy.currency_exchange.account.dto;

import moomint.toy.currency_exchange.account.domain.aggregate.enums.TransactionType;

import java.math.BigDecimal;

public record TransactionResultDTO(
        TransactionType transactionType,    // 거래 종류
        String accountNo,   // 계좌 번호
        String currency,    // 통화 정보
        BigDecimal amount,  // 거래 금액
        BigDecimal remainingBalance // 거래 후 잔액
) {}
