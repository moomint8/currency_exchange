package moomint.toy.currency_exchange.user.service;

import moomint.toy.currency_exchange.common.Exception.DuplicateException;
import moomint.toy.currency_exchange.common.Exception.NotLoggedInException;
import moomint.toy.currency_exchange.commonSetting.CreateLoggedInUser;
import moomint.toy.currency_exchange.commonSetting.TestUserInfo;
import moomint.toy.currency_exchange.user.dto.SignupDTO;
import moomint.toy.currency_exchange.user.dto.UserInfoDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class AuthServiceTest {

    private final String USERNAME = TestUserInfo.USERNAME;
    private final String PASSWORD = TestUserInfo.PASSWORD;
    private final String NAME = TestUserInfo.NAME;
    private final String EMAIL = TestUserInfo.EMAIL;

    @Autowired
    private AuthService authService;

    @Autowired
    private CreateLoggedInUser createLoggedInUser;

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

    @DisplayName("현재 로그인 중인 계정 확인 테스트")
    @Test
    void getCurrentUserInfoTest() throws DuplicateException, NotLoggedInException {

        // given
        createLoggedInUser.settingNormal();

        // when
        UserInfoDTO userInfo = authService.getCurrentUserInfo();

        // then
        assertEquals(USERNAME, userInfo.username());
        assertEquals(NAME, userInfo.name());
        assertEquals(EMAIL, userInfo.email());
    }

    @DisplayName("로그인하지 않은 경우 예외 테스트")
    @Test
    void getCurrentUserWithoutLoginExceptionTest() {

        // given, when, then
        assertThrows(NotLoggedInException.class, () -> authService.getCurrentUserInfo());
    }
}