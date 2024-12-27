package moomint.toy.currency_exchange.account.service;

import moomint.toy.currency_exchange.account.domain.aggregate.enums.TransactionType;
import moomint.toy.currency_exchange.account.dto.TransactionDTO;
import moomint.toy.currency_exchange.account.dto.TransactionResultDTO;
import moomint.toy.currency_exchange.common.Exception.DuplicateException;
import moomint.toy.currency_exchange.common.Exception.InvalidCurrencyException;
import moomint.toy.currency_exchange.common.Exception.NotAccountOwnerException;
import moomint.toy.currency_exchange.common.Exception.NotLoggedInException;
import moomint.toy.currency_exchange.commonSetting.CreateLoggedInUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CreateLoggedInUser createLoggedInUser;

    @Autowired
    private AccountService accountService;

    @DisplayName("입금 테스트")
    @Test
    void depositTest() throws DuplicateException, NotLoggedInException, NotAccountOwnerException, InvalidCurrencyException {

        // given
        createLoggedInUser.settingNormal();
        String accountNo = accountService.createAccount("USD");
        BigDecimal amount = new BigDecimal("100");
        TransactionDTO transactionDTO = new TransactionDTO(TransactionType.deposit, accountNo, "USD", amount);

        // when
        TransactionResultDTO result = transactionService.deposit(transactionDTO);

        // then
        assertEquals(result.transactionType(), transactionDTO.transactionType());
        assertEquals(result.accountNo(), transactionDTO.accountNo());
        assertEquals(result.amount(), transactionDTO.amount());
        assertEquals(result.remainingBalance(), amount);
    }

    @DisplayName("입금 잘못된 통화 정보 예외 테스트")
    @Test
    void invalidCurrencyDepositExceptionTest() throws DuplicateException, NotLoggedInException {

        // given
        createLoggedInUser.settingNormal();
        String accountNo = accountService.createAccount("KRW");
        BigDecimal amount = new BigDecimal("100");

        // when
        TransactionDTO transactionDTO = new TransactionDTO(TransactionType.deposit, accountNo, "USD", amount);

        // then
        assertThrows(InvalidCurrencyException.class, () -> transactionService.deposit(transactionDTO));
    }

    @DisplayName("입금 타인 계좌 예외 테스트")
    @Test
    void otherUserAccountDepositExceptionTest() throws DuplicateException, NotLoggedInException {

        // given
        createLoggedInUser.settingOtherUser();
        String otherAccountNo = accountService.createAccount("USD");
        BigDecimal amount = new BigDecimal("100");

        // when
        createLoggedInUser.settingNormal();
        TransactionDTO transactionDTO = new TransactionDTO(TransactionType.deposit, otherAccountNo, "USD", amount);

        // then
        assertThrows(NotAccountOwnerException.class, () -> transactionService.deposit(transactionDTO));
    }
}