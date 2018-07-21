package com.larry.meetingroomreservation.domain.exceptions;

import com.larry.meetingroomreservation.domain.entity.Period;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EndTimeMustBeAfterStartTimeException extends RuntimeException {

    private String field;
    private Period value;

    public EndTimeMustBeAfterStartTimeException(String defaultMessage, String field, Period value) {
        super(defaultMessage);
        this.field = field;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public Period getValue() {
        return value;
    }
}
