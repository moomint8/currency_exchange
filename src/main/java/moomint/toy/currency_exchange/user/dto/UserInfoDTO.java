package moomint.toy.currency_exchange.user.dto;

public record UserInfoDTO(
        Long id,
        String username,
        String name,
        String email
) {}
