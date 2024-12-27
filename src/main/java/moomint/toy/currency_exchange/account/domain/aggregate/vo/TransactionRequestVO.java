package moomint.toy.currency_exchange.account.domain.aggregate.vo;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import moomint.toy.currency_exchange.account.domain.aggregate.enums.TransactionType;

import java.math.BigDecimal;
import java.util.Currency;

@Getter
public class TransactionRequestVO {

    @NotNull(message = "거래 유형은 필수 입력 값입니다.")
    private TransactionType transactionType;

    @NotNull(message = "계좌 번호는 필수 입력 값입니다.")
    private String accountNo;

    @NotNull(message = "통화 코드는 필수 입력 값입니다.")
    private String currencyCode;

    @NotNull(message = "거래 금액은 필수 입력 값입니다.")
    private BigDecimal amount;

    @AssertTrue(message = "유효하지 않은 통화 코드입니다.")
    public boolean isCurrencyCodeValid() {
        if (currencyCode == null || currencyCode.isBlank()) {
            return false;
        }
        try {
            Currency.getInstance(currencyCode);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
