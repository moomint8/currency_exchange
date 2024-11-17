package moomint.toy.currency_exchange.account.domain.aggregate.vo;

import jakarta.validation.constraints.AssertTrue;
import lombok.Getter;

import java.util.Currency;

@Getter
public class CreateAccountRequestVO {

    private String currencyCode;

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
