package moomint.toy.currency_exchange.account.controller;

import jakarta.validation.Valid;
import moomint.toy.currency_exchange.account.domain.aggregate.vo.CreateAccountRequestVO;
import moomint.toy.currency_exchange.account.service.AccountService;
import moomint.toy.currency_exchange.common.Exception.NotLoggedInException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@Valid @RequestBody CreateAccountRequestVO request) {

        try {
            String accountNo = accountService.createAccount(request.getCurrencyCode());

            return ResponseEntity.ok().body("계좌 번호: " + accountNo);

        } catch (NotLoggedInException e) {
            return ResponseEntity.status(401).body("[ERROR] 로그인이 필요합니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("[ERROR] 계정 생성 실패");
        }
    }
}
