package com.larry.meetingroomreservation.security.provider;

import com.larry.meetingroomreservation.domain.repository.UserRepository;
import com.larry.meetingroomreservation.security.jwt.JwtFactory;
import com.larry.meetingroomreservation.security.token.JwtPostAuthorizationToken;
import com.larry.meetingroomreservation.security.token.JwtPreAuthorizationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private JwtFactory jwtFactory;

    // 브라이언처럼 accountContext를 만들지 않으면 아래와 같이 일관도지 못한 코드가 나오는데, 이럴바에야 AbstractUsername~~~ provider 상속 받아서
    // UserDetails 반환하는 게 더 나을 것 같다.
    @Override
    public Authentication authenticate(Authentication jwtPreAuthenticationToken) throws AuthenticationException {
        String jwtString = ((JwtPreAuthorizationToken) jwtPreAuthenticationToken).getToken();
        if (!jwtFactory.isValidateToken(jwtString)) {
            throw new RuntimeException("토큰이 유효하지 않습니다.");
        }
        return ((JwtPreAuthorizationToken)jwtPreAuthenticationToken).toPostToken(jwtFactory.getUserRoles(jwtString));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtPreAuthorizationToken.class.isAssignableFrom(authentication));
    }

}
