package moomint.toy.currency_exchange.account.domain.aggregate.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class AccountResponseVO {
    private String accountNo;
    private String currency;
    private BigDecimal balance;
}