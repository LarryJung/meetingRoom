package com.larry.meetingroomreservation.security.jwt;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class HeaderTokenExtractor {

    private final Logger log = LoggerFactory.getLogger(HeaderTokenExtractor.class);

    @Autowired
    private JwtFactory jwtFactory;

    public static final String HEADER_PREFIX = "Bearer ";

    public String extract(HttpServletRequest request) {
        log.info("path , method : {}, {} ", request.getPathInfo(), request.getMethod());
        String payload = request.getHeader("Authorization");
        if(StringUtils.isEmpty(payload) | payload.length() < HEADER_PREFIX.length()) {
            throw new RuntimeException("올바른 토큰 정보가 아닙니다.");
        }
        return payload.substring(HEADER_PREFIX.length(), payload.length());
    }

    public Long extractId(HttpServletRequest request) {
        String token = extract(request);
        return jwtFactory.getIdFromToken(token);
    }

}
