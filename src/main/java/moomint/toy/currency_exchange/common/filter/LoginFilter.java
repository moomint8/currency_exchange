package moomint.toy.currency_exchange.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import moomint.toy.currency_exchange.common.util.JwtUtil;
import moomint.toy.currency_exchange.user.domain.aggregate.entity.CustomUserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = "";

        try {
            LoginVO loginVO = new ObjectMapper().readValue(request.getInputStream(), LoginVO.class);
            username = loginVO.username;
            log.info("Attempting to authenticate user: {}", username);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(username, loginVO.password, null);

            log.info("Successfully authenticated user: {}", username);

            return authenticationManager.authenticate(authToken);

        } catch (IOException e) {
            log.error("Login IOException: ", e);
        } catch (BadCredentialsException e) {
            log.error("Login BadCredentialsException: {}", username);
        } catch (Exception e) {
            log.error("Login Unknown Exception: ", e);
        }
        throw new BadCredentialsException("Bad credentials");
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Long userId = userDetails.getId();
        String username = userDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String token = jwtUtil.createToken(userId, username, role);

        response.addHeader("Authorization", "Bearer " + token);

        log.info("Successfully issued the token user: {}", username);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {

        log.error("Authentication failed: {}", failed.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    public record LoginVO(String username, String password) {
    }
}
