package moomint.toy.currency_exchange.user.controller;

import jakarta.validation.Valid;
import moomint.toy.currency_exchange.common.Exception.DuplicateException;
import moomint.toy.currency_exchange.user.domain.aggregate.vo.MessageResponseVO;
import moomint.toy.currency_exchange.user.domain.aggregate.vo.SignupRequestVO;
import moomint.toy.currency_exchange.user.dto.SignupDTO;
import moomint.toy.currency_exchange.user.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponseVO> signup(@Valid @RequestBody SignupRequestVO request) {

        try {
            SignupDTO userInfo = new SignupDTO(request.getUsername(), request.getPassword(), request.getName(), request.getEmail());
            authService.signUp(userInfo);

            return ResponseEntity.ok().body(new MessageResponseVO(request.getName() + "님 회원 가입을 축하합니다."));

        } catch (DuplicateException e) {
            return ResponseEntity.badRequest().body(new MessageResponseVO(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponseVO("[ERROR] 회원가입 실패"));
        }
    }
}