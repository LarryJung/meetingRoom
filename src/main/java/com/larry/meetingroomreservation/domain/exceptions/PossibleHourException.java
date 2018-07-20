package com.larry.meetingroomreservation.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PossibleHourException extends RuntimeException {
    public PossibleHourException(String message) {
        super(message);
    }
}
