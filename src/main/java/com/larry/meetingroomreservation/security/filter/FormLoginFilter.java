package com.larry.meetingroomreservation.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.larry.meetingroomreservation.security.dto.FormLoginDto;
import com.larry.meetingroomreservation.security.token.PreAuthorizationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 이 필터는 요청에 대해 entry point역할을 하고, 가장 기본적인 처리 과정을 표현해줄 수 있다.(메소드를 보면)
 * 필터는 Component로 만들지 않는다.(defaultUrl까지 빈으로 만들 순 없자나..단지 이 이유인가..) Config에서 handler, matcher DI를 받게 된다.
 */

public class FormLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final Logger log = LoggerFactory.getLogger(FormLoginFilter.class);

    //AuthenticationFailureHandler 도 있으면 좋지만, 일단 디폴트로 하겠다.
    private AuthenticationSuccessHandler authenticationSuccessHandler; // 근데 얘가 언제 쓰이지.. 아무데도 안쓰인다고 경고뜨는데..?

//    protected FormLoginFilter(String defaultFilterProcessesUrl) {
//        super(defaultFilterProcessesUrl);
//    }

    public FormLoginFilter(String defaultUrl, AuthenticationSuccessHandler successHandler) {
        super(defaultUrl);
        this.authenticationSuccessHandler = successHandler;
    }

    /**
     * 이 메소드는 세 가지 중 하나를 반환해야 한다.
     * 1. 인증 성공 시 Authentication object return
     * 2. null, but keep authentication process. 반환하기 전 추가 작업을 해 주어야 한다.
     * 3. throw AuthenticationException. 이것은 manager에서 수행하겠지.
     * <p>
     * 현재는 2번째에 대한 처리가 없고, Manager.authenticate(token) 이 어떻게 exception을 발생시키는 지 모르겠다.
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException{
        FormLoginDto dto = new ObjectMapper().readValue(request.getReader(), FormLoginDto.class);
        PreAuthorizationToken token = new PreAuthorizationToken(dto);
        log.info("input authentication data : {}", token.getUserId());
        log.info("input authentication data : {}", token.getUserPassword());
        return super.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        this.authenticationSuccessHandler.onAuthenticationSuccess(request, response, authResult);
    }
}
