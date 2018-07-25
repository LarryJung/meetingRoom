package com.larry.meetingroomreservation.security.token;

import com.larry.meetingroomreservation.domain.entity.support.RoleName;
import com.larry.meetingroomreservation.security.dto.FormLoginDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PreAuthorizationToken extends UsernamePasswordAuthenticationToken {

    private PreAuthorizationToken(String userId, String password) {
        super(userId, password);
    }

    public PreAuthorizationToken(FormLoginDto dto) {
        this(dto.getUserId(), dto.getPassword());
    }

    public String getUserId() {
        return (String)super.getPrincipal();
    }

    public String getUserPassword() {
        return (String)super.getCredentials();
    }

    public Authentication toPostToken(RoleName roleName) {
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ADMIN"));
        list.add(new SimpleGrantedAuthority("USER"));
        return new PostAuthorizationToken(getUserId(), getUserPassword(), list);
    }

    private static List<SimpleGrantedAuthority> parseAuthorities(RoleName role) {
        return Arrays.asList(role).stream().map(r -> new SimpleGrantedAuthority(r.name())).collect(Collectors.toList());
    }

}