package moomint.toy.currency_exchange.account.service;

import moomint.toy.currency_exchange.account.domain.repository.AccountRepository;
import moomint.toy.currency_exchange.account.dto.AccountDTO;
import moomint.toy.currency_exchange.common.Exception.DuplicateException;
import moomint.toy.currency_exchange.common.Exception.NotLoggedInException;
import moomint.toy.currency_exchange.commonSetting.CreateLoggedInUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("ci")
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
}