package com.larry.meetingroomreservation.security.provider;

import com.larry.meetingroomreservation.domain.entity.User;
import com.larry.meetingroomreservation.domain.repository.UserRepository;
import com.larry.meetingroomreservation.security.token.PreAuthorizationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class FormLoginAuthenticationProvider implements AuthenticationProvider {

    private final Logger log = LoggerFactory.getLogger(FormLoginAuthenticationProvider.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication preAuthenticationToken) {

        PreAuthorizationToken token = (PreAuthorizationToken)preAuthenticationToken;

        String userId = token.getUserId();
        String password = token.getUserPassword();

        User user = userRepository.findByUserId(userId).orElseThrow(() -> new NoSuchElementException("정보에 맞는 계정이 없습니다."));

        if(isCorrectPassword(password, user)) {
            log.info("인증 성공");
            return ((PreAuthorizationToken)preAuthenticationToken).toPostToken(user.getRoleName());
        }
        throw new NoSuchElementException("인증 정보가 정확하지 않습니다.");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return PreAuthorizationToken.class.isAssignableFrom(aClass);
    }

    // 패스워드 순서 조심.
    private boolean isCorrectPassword(String password, User user) {
        log.info("input password : {}", password);
        log.info("user password : {}", user.getPassword());
        return passwordEncoder.matches(password, user.getPassword());
    }
}