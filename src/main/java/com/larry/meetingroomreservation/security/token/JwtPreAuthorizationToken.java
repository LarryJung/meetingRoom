package com.larry.meetingroomreservation.security.token;

import com.larry.meetingroomreservation.domain.entity.support.RoleName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class JwtPreAuthorizationToken extends UsernamePasswordAuthenticationToken{

    private final Logger log = LoggerFactory.getLogger(JwtPreAuthorizationToken.class);

    public JwtPreAuthorizationToken(Object principal, Object credentials) {
        super(principal, credentials);
        log.info("principal (Id) : {}", principal);
        log.info("credentials (token) : {}", credentials);
    }

    public Long getId() {
        return (Long) this.getPrincipal();
    }

    public String getToken() {
        return (String) this.getCredentials();
    }

    public Authentication toPostToken(List<RoleName.Scope> userRoles) {
        log.info("pre to post token");
        return new JwtPostAuthorizationToken(getId(), getToken(), parseAuthorities(userRoles));
    }

    private List<SimpleGrantedAuthority> parseAuthorities(List<RoleName.Scope> roles) {
        log.info("roles {}", roles.toString());
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.name())).collect(Collectors.toList());
    }

}
