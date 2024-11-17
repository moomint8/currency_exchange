package moomint.toy.currency_exchange.account.dto;

import java.math.BigDecimal;

public record AccountDTO(
        String accountNo,   // 계좌 번호
        String currency,    // 통화 정보
        BigDecimal balance  // 잔고
) {}
