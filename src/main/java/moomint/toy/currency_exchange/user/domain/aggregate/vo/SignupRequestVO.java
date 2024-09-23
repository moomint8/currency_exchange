package moomint.toy.currency_exchange.user.domain.aggregate.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import moomint.toy.currency_exchange.common.util.RegularExpression;

@Getter
public class SignupRequestVO {

    @Pattern(regexp = RegularExpression.USERNAME,
            message = RegularExpression.USERNAME_MESSAGE)
    private String username;  // 로그인 아이디

    @Pattern(regexp = RegularExpression.PASSWORD,
            message = RegularExpression.PASSWORD_MESSAGE)
    private String password;  // 비밀번호

    @Pattern(regexp = RegularExpression.NAME,
            message = RegularExpression.NAME_MESSAGE)
    private String name;     // 회원 이름

    @Email(message = "이메일 형태가 아닙니다.")
    private String email;     // 이메일
}
