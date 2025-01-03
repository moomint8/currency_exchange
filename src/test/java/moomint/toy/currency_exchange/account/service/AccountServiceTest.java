package moomint.toy.currency_exchange.account.service;

import moomint.toy.currency_exchange.account.domain.repository.AccountRepository;
import moomint.toy.currency_exchange.account.dto.AccountDTO;
import moomint.toy.currency_exchange.account.dto.UpdateBalanceDTO;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CreateLoggedInUser createLoggedInUser;

    @DisplayName("계좌 생성 테스트")
    @Test
    void createAccount() throws DuplicateException, NotLoggedInException {

        // given
        createLoggedInUser.settingNormal();

        // when
        String accountNo = accountService.createAccount("KRW");

        // then
        assertTrue(accountRepository.existsByAccountNo(accountNo));
    }

    @DisplayName("계좌 전체 조회 테스트")
    @Test
    void getAllAccountsTest() throws DuplicateException, NotLoggedInException {

        // given
        createLoggedInUser.settingNormal();
        Map<String, String> accounts = new HashMap<>();
        accounts.put("KRW", accountService.createAccount("KRW"));
        accounts.put("USD", accountService.createAccount("USD"));

        // when
        List<AccountDTO> accountDTOs = accountService.getAllAccounts();

        // then
        for (AccountDTO accountDTO : accountDTOs) {
            assertEquals(accountDTO.accountNo(), accounts.get(accountDTO.currency()));
        }
    }

    @DisplayName("계좌 번호를 이용한 계좌 조회 테스트")
    @Test
    void getAccountByAccountByAccountNoNoTest() throws DuplicateException, NotLoggedInException, NotAccountOwnerException {

        // given
        createLoggedInUser.settingNormal();
        String accountNo = accountService.createAccount("KRW");

        // when
        AccountDTO accountDTO = accountService.getAccountByAccountNo(accountNo);

        // then
        assertEquals(accountNo, accountDTO.accountNo());
        assertEquals(accountDTO.currency(), "KRW");
        assertEquals(accountDTO.balance(), BigDecimal.ZERO);
    }

    @DisplayName("타인 계좌 조회 예외 테스트(계좌 번호 조회)")
    @Test
    void getOtherUsersAccountByAccountNoExceptionTest() throws DuplicateException, NotLoggedInException {

        // given
        createLoggedInUser.settingOtherUser();
        String otherAccountNo = accountService.createAccount("KRW");

        // when
        createLoggedInUser.settingNormal();

        // then
        assertThrows(NotAccountOwnerException.class, () -> accountService.getAccountByAccountNo(otherAccountNo));
    }

    @DisplayName("계좌 잔액 업데이트 기능 테스트")
    @Test
    void updateBalanceTest() throws DuplicateException, NotLoggedInException, NotAccountOwnerException, InvalidCurrencyException {

        // given
        createLoggedInUser.settingNormal();
        String accountNo = accountService.createAccount("USD");
        BigDecimal amount = new BigDecimal("100");

        // when
        accountService.updateBalance(new UpdateBalanceDTO(accountNo, "USD", amount));

        // then
        assertEquals(accountService.getAccountByAccountNo(accountNo).balance(), amount);
    }
}