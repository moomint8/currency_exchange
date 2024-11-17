package moomint.toy.currency_exchange.user.service;

import lombok.extern.slf4j.Slf4j;
import moomint.toy.currency_exchange.common.Exception.DuplicateException;
import moomint.toy.currency_exchange.common.Exception.NotLoggedInException;
import moomint.toy.currency_exchange.user.domain.aggregate.entity.CustomUserDetails;
import moomint.toy.currency_exchange.user.domain.aggregate.entity.User;
import moomint.toy.currency_exchange.user.domain.aggregate.enums.UserRole;
import moomint.toy.currency_exchange.user.domain.repository.UserRepository;
import moomint.toy.currency_exchange.user.dto.SignupDTO;
import moomint.toy.currency_exchange.user.dto.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public String signUp(SignupDTO userInfo) throws DuplicateException {

        try {

            // 중복된 로그인 아이디
            if (userInfo.username() != null && userRepository.existsByUsername(userInfo.username())) {
                log.info("Username '{}' is already in use", userInfo.username());
                throw new DuplicateException("아이디");
            }

            // 중복된 이메일
            if (userInfo.email() != null && userRepository.existsByEmail(userInfo.email())) {
                log.info("Email '{}' is already in use", userInfo.email());
                throw new DuplicateException("이메일");
            }

            User user = User.builder()
                    .username(userInfo.username())
                    .password(bCryptPasswordEncoder.encode(userInfo.password()))
                    .name(userInfo.name())
                    .email(userInfo.email())
                    .userRole(UserRole.ROLE_NORMAL)
                    .build();

            userRepository.save(user);

            log.info("Created new user [ id: {}, username: {} ]", user.getId(), user.getUsername());

            return "회원가입 성공";

        } catch (DuplicateException e) {
            throw e;
        } catch (Exception e) {
            log.error("Signup Unknown error: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public UserInfoDTO getCurrentUserInfo() throws NotLoggedInException {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails userDetails)) {
            throw new NotLoggedInException();
        }

        return new UserInfoDTO(
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getName(),
                userDetails.getEmail()
        );
    }
}
