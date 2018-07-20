package com.larry.meetingroomreservation.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ThirtyMinutesUnitException extends RuntimeException{
    public ThirtyMinutesUnitException(String message) {
        super(message);
    }

}
