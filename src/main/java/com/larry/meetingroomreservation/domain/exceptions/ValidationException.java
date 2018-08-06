package com.larry.meetingroomreservation.domain.exceptions;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException{

    private String field;

    private Object value;

    public <T> ValidationException(String message, String field, T value) {
        super(message);
        this.field = field;
        this.value = value;
    }
}
