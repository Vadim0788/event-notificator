package com.dev.eventnotificator.security.jwt;

import com.dev.eventnotificator.security.user.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtTokenFilter.class);
    private final JwtTokenManager jwtTokenManager;


    public JwtTokenFilter(
            JwtTokenManager jwtTokenManager
    ) {
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        var jwtToken = authorizationHeader.substring(7);
        String loginFromToken;
        String role;
        Long id;
        try {
            loginFromToken = jwtTokenManager.getLoginFromJwt(jwtToken);
            role = jwtTokenManager.getRoleFromToken(jwtToken);
            id = jwtTokenManager.getIdFromJwt(jwtToken);
        } catch (Exception e) {
            log.error("Error while reading jwt", e);
            filterChain.doFilter(request, response);
            return;
        }
        User user = new User(id, loginFromToken);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        List.of(new SimpleGrantedAuthority(role))
                );

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.debug("Authentication: {}", auth);
        filterChain.doFilter(request, response);
        Authentication authAfter = SecurityContextHolder.getContext().getAuthentication();
        log.debug("Authentication after filter chain: {}", authAfter);
    }
}


