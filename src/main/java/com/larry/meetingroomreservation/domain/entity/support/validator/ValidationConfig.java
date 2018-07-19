package com.larry.meetingroomreservation.domain.entity.support.validator;

import com.larry.meetingroomreservation.domain.exceptions.AlreadyReservedException;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;

import java.util.Map;

@Configuration
public class ValidationConfig {
//
//    @Bean
//    public ErrorAttributes errorAttributes() {
//        return new DefaultErrorAttributes() {
//
//            public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
//                Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
//                Throwable error = getError(requestAttributes);
//                if (error instanceof AlreadyReservedException) {
//                    errorAttributes.put("errors", ((AlreadyReservedException)error).getErrors());
//                }
//                return errorAttributes;
//            }
//        };
//    }

}
