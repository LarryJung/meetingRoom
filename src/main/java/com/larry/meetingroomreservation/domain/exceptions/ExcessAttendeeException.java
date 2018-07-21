package com.larry.meetingroomreservation.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExcessAttendeeException extends RuntimeException {

    public String field;

    public int value;

    public ExcessAttendeeException(String message, String field, int value) {
        super(message);
        this.field = field;
        this.value = value;
    }
}
