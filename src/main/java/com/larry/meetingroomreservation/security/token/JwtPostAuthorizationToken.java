package com.larry.meetingroomreservation.security.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtPostAuthorizationToken extends UsernamePasswordAuthenticationToken{

    public JwtPostAuthorizationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public String getUserId() {
        return (String) getPrincipal();
    }
}
