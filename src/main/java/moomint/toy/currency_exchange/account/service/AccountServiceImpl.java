package moomint.toy.currency_exchange.account.service;

import lombok.extern.slf4j.Slf4j;
import moomint.toy.currency_exchange.account.domain.aggregate.entity.Account;
import moomint.toy.currency_exchange.account.domain.repository.AccountRepository;
import moomint.toy.currency_exchange.account.dto.AccountDTO;
import moomint.toy.currency_exchange.common.Exception.NotAccountOwnerException;
import moomint.toy.currency_exchange.common.Exception.NotLoggedInException;
import moomint.toy.currency_exchange.user.domain.aggregate.entity.User;
import moomint.toy.currency_exchange.user.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AuthService authService;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, AuthService authService) {
        this.accountRepository = accountRepository;
        this.authService = authService;
    }

    @Override
    public String createAccount(String currency) throws NotLoggedInException {

        try {
            String accountNo = createAccountNo();

            User user = User.builder()
                    .id(authService.getCurrentUserInfo().id())
                    .build();

            Account account = Account.builder()
                    .accountNo(accountNo)
                    .currency(currency)
                    .balance(BigDecimal.ZERO)
                    .createdAt(LocalDateTime.now())
                    .user(user)
                    .build();

            accountRepository.save(account);

            log.info("userId: {} Created account: {}", user.getId(), accountNo);

            return accountNo;

        } catch (Exception e) {
            log.error("Error creating account {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<AccountDTO> getAllAccounts() throws NotLoggedInException {

        try {
            User user = User.builder()
                    .id(authService.getCurrentUserInfo().id())
                    .build();

            List<Account> accounts = accountRepository.findAllByUserId(user.getId());

            List<AccountDTO> accountDTOs = new ArrayList<>();

            for (Account account : accounts) {
                accountDTOs.add(new AccountDTO(account.getAccountNo(), account.getCurrency(), account.getBalance()));
            }

            log.info("userId: {} Get all accounts: {}", user.getId(), accountDTOs);

            return accountDTOs;

        } catch (Exception e) {
            log.error("Error get all account {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public AccountDTO getAccount(String accountNo) throws NotLoggedInException, NotAccountOwnerException {

        try {
            User user = User.builder()
                    .id(authService.getCurrentUserInfo().id())
                    .build();

            Account account = accountRepository.findByAccountNo(accountNo);

            // 본인 계좌가 아닌 경우 예외 처리
            if (user.getId() != account.getUser().getId()) {
                log.warn("Unauthorized account access attempt detected. AccountNo: {}, UserId: {}", accountNo, user.getId());
                throw new NotAccountOwnerException();
            }

            return new AccountDTO(account.getAccountNo(), account.getCurrency(), account.getBalance());

        } catch (Exception e) {
            log.error("Error get account by account no {}", e.getMessage());
            throw e;
        }
    }

    private String createAccountNo() {

        while (true) {

            StringBuilder number = new StringBuilder();

            while (number.length() < 12) {
                String uuid = UUID.randomUUID().toString().replaceAll("[^0-9]", "");
                number.append(uuid);
            }

            String accountNo = formatAccountNumber(number.substring(0, 12));

            // Unique한 계좌 번호인 경우 반환
            if (!accountRepository.existsByAccountNo(accountNo)) {
                return accountNo;
            }
        }
    }

    private static String formatAccountNumber(String raw) {
        return raw.substring(0, 3) + "-" + raw.substring(3, 7) + "-" + raw.substring(7, 12);
    }
}