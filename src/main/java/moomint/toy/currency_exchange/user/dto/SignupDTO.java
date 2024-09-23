package moomint.toy.currency_exchange.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@ToString
public class SignupDTO {

    // 로그인 아이디
    private String username;

    // 비밀번호
    private String password;

    // 회원 이름
    private String name;

    // 이메일
    private String email;

    // 회원 생성일
    private LocalDate createdAt;

    @Builder
    public SignupDTO(String username, String password, String name, String email, LocalDate createdAt) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }
}
