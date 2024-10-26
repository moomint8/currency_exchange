package moomint.toy.currency_exchange.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import moomint.toy.currency_exchange.common.util.JwtUtil;
import moomint.toy.currency_exchange.user.domain.aggregate.entity.CustomUserDetails;
import moomint.toy.currency_exchange.user.domain.aggregate.entity.User;
import moomint.toy.currency_exchange.user.domain.aggregate.enums.UserRole;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        // Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            log.info("without token request");
            filterChain.doFilter(request, response);

            return;
        }

        // Bearer 부분 제거 및 순수 토큰 획득
        String token = authorization.split(" ")[1];

        // 토큰 유효 시간 검증
        if (jwtUtil.isExpired(token)) {

            log.info("token expired");
            filterChain.doFilter(request, response);

            return;
        }

        String username = jwtUtil.getUsername(token);
        UserRole role = UserRole.valueOf(jwtUtil.getRole(token));

        User user = User.builder()
                .username(username)
                .password("TempPassword")
                .userRole(role)
                .build();

        // UserDetails에 User 객체 추가
        CustomUserDetails userDetails = new CustomUserDetails(user);

        // SpringSecurity 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // SecurityContextHolder에 User 추가
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
