package com.larry.meetingroomreservation.security.token;

import com.larry.meetingroomreservation.security.dto.FormLoginDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

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

}