package com.larry.meetingroomreservation.security.filter;

import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;

public class AntMatcherFormChain {

    public static PatternBuilder patternBuilder() {
        return new PatternBuilder();
    }

    public static class PatternBuilder {

        private List<AntMatcherForm> matcherForms = new ArrayList<>();

        public PatternBuilder antMatchers(String pattern, HttpMethod method) {
            matcherForms.add(AntMatcherForm.of(pattern, method));
            return this;
        }

        public List<AntMatcherForm> build() {
            return matcherForms;
        }
    }


}
