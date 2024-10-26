package moomint.toy.currency_exchange.account.service;

import moomint.toy.currency_exchange.account.domain.repository.AccountRepository;
import moomint.toy.currency_exchange.common.Exception.DuplicateException;
import moomint.toy.currency_exchange.common.Exception.NotLoggedInException;
import moomint.toy.currency_exchange.commonSetting.CreateLoggedInUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
}