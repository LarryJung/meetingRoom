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
        if(StringUtils.isEmpty(payload)) {
            throw new RuntimeException("토큰이 들어있지 않습니다.");
        }
        if (payload.length() < HEADER_PREFIX.length()) {
            throw new RuntimeException("토큰 길이가 유효하지 않습니다. : " + payload);
        }
        return payload.substring(HEADER_PREFIX.length(), payload.length());
    }

    public Long extractId(HttpServletRequest request) {
        String token = extract(request);
        return jwtFactory.getIdFromToken(token);
    }

}
