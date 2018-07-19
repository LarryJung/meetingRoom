package com.larry.meetingroomreservation.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EndTimeMustBeAfterStartTimeException extends RuntimeException {

    public EndTimeMustBeAfterStartTimeException(String defaultMessage) {
        super(defaultMessage);
    }

}
