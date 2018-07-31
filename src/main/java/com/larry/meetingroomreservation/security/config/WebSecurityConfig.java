package com.larry.meetingroomreservation.security.config;

import com.larry.meetingroomreservation.security.filter.*;
import com.larry.meetingroomreservation.security.handler.FormLoginAuthenticationSuccessHandler;
import com.larry.meetingroomreservation.security.jwt.HeaderTokenExtractor;
import com.larry.meetingroomreservation.security.provider.FormLoginAuthenticationProvider;
import com.larry.meetingroomreservation.security.provider.JwtAuthenticationProvider;
import com.larry.meetingroomreservation.security.provider.SocialLoginAuthenticationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Autowired
    private FormLoginAuthenticationSuccessHandler formLoginAuthenticationSuccessHandler;

    @Autowired
    private FormLoginAuthenticationProvider provider;

    @Autowired
    private SocialLoginAuthenticationProvider socialProvider;

    @Autowired
    private JwtAuthenticationProvider jwtProvider;

    @Autowired
    private HeaderTokenExtractor headerTokenExtractor;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private FormLoginFilter formLoginFilter() throws Exception {
        FormLoginFilter filter = new FormLoginFilter("/formLogin", formLoginAuthenticationSuccessHandler);
        filter.setAuthenticationManager(super.authenticationManagerBean());
        return filter;
    }

    private List<AntMatcherForm> passPatterns = AntMatcherFormChain.patternBuilder()
            .antMatchers("/formLogin", HttpMethod.GET)
            .antMatchers("/api/rooms*", HttpMethod.GET)
            .antMatchers("/api/reservations/*/rooms/*", HttpMethod.GET)
            .antMatchers("/error", HttpMethod.POST).build();

    private List<AntMatcherForm> processPatterns = AntMatcherFormChain.patternBuilder()
            .antMatchers("/api/**", HttpMethod.GET)
            .antMatchers("/api/**", HttpMethod.POST).build();

    private JwtAuthenticationFilter jwtFilter() throws Exception {
        log.info("json web token filter start");
        FilterSkipMatcher matcher = new FilterSkipMatcher(passPatterns, processPatterns);
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(matcher, headerTokenExtractor);
        filter.setAuthenticationManager(super.authenticationManagerBean());
        return filter;
    }

    private SocialLoginFilter socialFilter() throws Exception {
        SocialLoginFilter filter = new SocialLoginFilter("/socialLogin", formLoginAuthenticationSuccessHandler);
        filter.setAuthenticationManager(super.authenticationManagerBean());
        return filter;
    }

    // 매니저는 프로바이더에 의존하고 있으니 그것을 해 주어야 한다. 메소드의 인자를 보고 생각해보면 된다.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(this.provider)
                .authenticationProvider(this.socialProvider)
                .authenticationProvider(this.jwtProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .headers().frameOptions().sameOrigin()
                .and().csrf().disable();
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .addFilterBefore(formLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(socialFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        http
                .authorizeRequests()
                    .antMatchers("/", "/me", "/h2-console/**", "/js/**", "/css/**", "/image/**", "/fonts/**", "/favicon.ico").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/reservations/*/rooms/*").access("hasRole('SCOPE_USER')")
                .anyRequest().permitAll();

    }

}