package com.larry.meetingroomreservation.security.filter;

import lombok.Getter;
import org.springframework.http.HttpMethod;

@Getter
public class AntMatcherForm {

    String pattern;

    HttpMethod method;

    private AntMatcherForm(String pattern, HttpMethod method) {
        this.pattern = pattern;
        this.method = method;
    }

    public static AntMatcherForm of(String pattern, HttpMethod method) {
        return new AntMatcherForm(pattern, method);
    }
}
