package com.larry.meetingroomreservation.security.token;

import com.larry.meetingroomreservation.domain.entity.support.RoleName;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class JwtPreAuthorizationToken extends UsernamePasswordAuthenticationToken{

    public JwtPreAuthorizationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public String getUserId() {
        return (String) this.getPrincipal();
    }

    public String getToken() {
        return (String) this.getCredentials();
    }

    public Authentication toPostToken(List<RoleName> userRoles) {
        return new JwtPostAuthorizationToken(getUserId(), getToken(), parseAuthorities(userRoles));
    }

    private static List<SimpleGrantedAuthority> parseAuthorities(List<RoleName> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.name())).collect(Collectors.toList());
    }

}
