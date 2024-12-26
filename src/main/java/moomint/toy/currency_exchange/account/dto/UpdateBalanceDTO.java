package moomint.toy.currency_exchange.account.dto;

import java.math.BigDecimal;

public record UpdateBalanceDTO (
        String accountNo,   // 계좌 번호
        String currency,    // 통화 정보
        BigDecimal amount  // 거래 금액
) {}
