package com.larry.meetingroomreservation.config.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/", "/me", "/h2-console/**", "/login/**", "/js/**", "/css/**", "/image/**", "/fonts/**", "/favicon.ico").permitAll()
                .and().headers().frameOptions().sameOrigin()
                .and().csrf().disable()
        ;
    }
}