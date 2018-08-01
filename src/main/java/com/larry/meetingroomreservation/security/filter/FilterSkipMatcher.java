package com.larry.meetingroomreservation.security.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class FilterSkipMatcher implements RequestMatcher {

    private final Logger log = LoggerFactory.getLogger(FilterSkipMatcher.class);

    private OrRequestMatcher passPatterns;
    private OrRequestMatcher processingPatterns;

    public FilterSkipMatcher(List<AntMatcherForm> passPatterns, List<AntMatcherForm> processingPatterns) {
        this.passPatterns = new OrRequestMatcher(passPatterns.stream().map(f -> new AntPathRequestMatcher(f.getPattern(), f.getMethod().toString())).collect(Collectors.toList()));
        this.processingPatterns = new OrRequestMatcher(processingPatterns.stream().map(f -> new AntPathRequestMatcher(f.getPattern(), f.getMethod().toString())).collect(Collectors.toList()));
    }

    @Override
    public boolean matches(HttpServletRequest req) {
        log.info("request url and method {}, {} : ", req.getServletPath(), req.getMethod());
        log.info("boolean : {} and {}", passPatterns.matches(req), processingPatterns.matches(req));
        if (passPatterns.matches(req)) {
            return false;
        }
        return processingPatterns.matches(req);
    }
}