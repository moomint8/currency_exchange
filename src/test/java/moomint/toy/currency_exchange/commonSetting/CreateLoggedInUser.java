package moomint.toy.currency_exchange.commonSetting;

import moomint.toy.currency_exchange.common.Exception.DuplicateException;
import moomint.toy.currency_exchange.user.domain.repository.UserRepository;
import moomint.toy.currency_exchange.user.dto.SignupDTO;
import moomint.toy.currency_exchange.user.dto.UserInfoDTO;
import moomint.toy.currency_exchange.user.service.AuthService;
import moomint.toy.currency_exchange.user.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CreateLoggedInUser {

    private final AuthService authService;
    private final UserRepository userRepository;

    @Autowired
    public CreateLoggedInUser(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    // 일반 회원 계정 세팅
    public void settingNormal() throws DuplicateException {

        // 생성 계정 정보
        SignupDTO userDTO = new SignupDTO(TestUserInfo.USERNAME, TestUserInfo.PASSWORD, TestUserInfo.NAME, TestUserInfo.EMAIL);
        authService.signUp(userDTO);

        // 로그인
        login(userDTO.username());
    }

    // 다른 계정 생성
    public void settingOtherUser() throws DuplicateException {

        // 생성 계정 정보
        SignupDTO userDTO = new SignupDTO("other" + TestUserInfo.USERNAME, "other" + TestUserInfo.PASSWORD,
                "other" + TestUserInfo.NAME, "other" + TestUserInfo.EMAIL);
        authService.signUp(userDTO);

        // 로그인
        login(userDTO.username());
    }

    private void login(String username) {
        CustomUserDetailsService customUserDetailsService = new CustomUserDetailsService(userRepository);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities()));
    }
}
