package com.larry.meetingroomreservation.domain.exceptions;

import com.larry.meetingroomreservation.domain.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CannotReserveSameBookerPerDayException extends RuntimeException {

    private String field;
    private User value;

    public CannotReserveSameBookerPerDayException(String defaultMessage, String field, User value) {
        super(defaultMessage);
        this.field = field;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public User getValue() {
        return value;
    }
}

