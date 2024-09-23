package moomint.toy.currency_exchange.user.dto;

public record SignupDTO(
        String username,  // 로그인 아이디
        String password,  // 비밀번호
        String name,      // 회원 이름
        String email     // 이메일
) {}