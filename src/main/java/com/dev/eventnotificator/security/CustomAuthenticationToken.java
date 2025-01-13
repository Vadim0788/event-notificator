package com.dev.eventnotificator.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {

    private final Long userId;
    private final String username;
    private final String role;

    public CustomAuthenticationToken(Long userId, String username, String role) {
        super(List.of(new SimpleGrantedAuthority(role)));
        this.userId = userId;
        this.username = username;
        this.role = role;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    public Long getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }
}