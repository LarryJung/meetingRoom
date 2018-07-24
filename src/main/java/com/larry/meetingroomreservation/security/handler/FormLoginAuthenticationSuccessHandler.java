package com.larry.meetingroomreservation.security.handler;

import com.larry.meetingroomreservation.security.jwt.JwtFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FormLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final Logger log = LoggerFactory.getLogger(FormLoginAuthenticationSuccessHandler.class);

    @Autowired
    private JwtFactory jwtFactory;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws IOException {
        String token = jwtFactory.generateToken(auth);
        log.info("token : {}", token);
        processResponse(res, token);
    }

    private void processResponse(HttpServletResponse res, String token) throws IOException {
        res.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        res.setStatus(HttpStatus.OK.value());
        res.getWriter().write(token);
    }
}