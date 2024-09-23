package moomint.toy.currency_exchange.user.service;

import moomint.toy.currency_exchange.common.Exception.DuplicateException;
import moomint.toy.currency_exchange.user.dto.SignupDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class AuthServiceTest {

    private final String USERNAME = "TestUserID";
    private final String PASSWORD = "TestPassword";
    private final String NAME = "TestName";
    private final String EMAIL = "TestEmail@example.com";

    @Autowired
    private AuthService authService;

    @DisplayName("회원가입 테스트")
    @Test
    void signUpTest() {

        // given
        SignupDTO userinfo = new SignupDTO(USERNAME, PASSWORD, NAME, EMAIL);

        // when
        String result = assertDoesNotThrow(() -> authService.signUp(userinfo));

        // then
        assertEquals(result, "회원가입 성공");
    }

    @DisplayName("로그인 아이디 중복 예외 테스트")
    @Test
    void duplicateUsernameTest() {

        // given
        SignupDTO userinfo = new SignupDTO(USERNAME, PASSWORD, NAME, EMAIL);
        SignupDTO duplicateUserIdUser = new SignupDTO(USERNAME, PASSWORD, NAME, "UniqueEmail@example.com");

        // when
        assertDoesNotThrow(() -> authService.signUp(userinfo));
        String result = assertThrows(DuplicateException.class, () -> authService.signUp(duplicateUserIdUser)).getMessage();

        // then
        assertTrue(result.contains("[ERROR]"));
        assertTrue(result.contains("아이디"));
    }

    @DisplayName("이메일 중복 예외 테스트")
    @Test
    void duplicateEmailTest() {

        // given
        SignupDTO userinfo = new SignupDTO(USERNAME, PASSWORD, NAME, EMAIL);
        SignupDTO duplicateEmailUser = new SignupDTO("UniqueUsername", PASSWORD, NAME, EMAIL);

        // when
        assertDoesNotThrow(() -> authService.signUp(userinfo));
        String result = assertThrows(DuplicateException.class, () -> authService.signUp(duplicateEmailUser)).getMessage();

        // then
        assertTrue(result.contains("[ERROR]"));
        assertTrue(result.contains("이메일"));
    }
}