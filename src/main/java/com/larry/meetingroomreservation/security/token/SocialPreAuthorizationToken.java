package com.larry.meetingroomreservation.security.token;

import com.larry.meetingroomreservation.domain.entity.User;
import com.larry.meetingroomreservation.domain.entity.support.RoleName;
import com.larry.meetingroomreservation.security.dto.SocialLoginDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SocialPreAuthorizationToken extends UsernamePasswordAuthenticationToken {

    public SocialPreAuthorizationToken(SocialLoginDto dto) {
        super(dto.getProvider(), dto);
    }

    public SocialLoginDto getDto() {
        return (SocialLoginDto)super.getCredentials();
    }

    public Authentication toPostToken(User user) {
        return new PostAuthorizationToken((user.getId()), user.getName(), parseAuthorities(user.getRoleName()));
    }

    private static List<SimpleGrantedAuthority> parseAuthorities(RoleName role) {
        return Stream.of(role).map(s -> new SimpleGrantedAuthority(s.name())).collect(Collectors.toList());
    }
}