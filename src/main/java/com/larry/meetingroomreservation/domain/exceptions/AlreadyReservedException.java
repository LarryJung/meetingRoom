package com.larry.meetingroomreservation.domain.exceptions;

import com.larry.meetingroomreservation.domain.entity.MeetingTime;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class AlreadyReservedException extends RuntimeException{

    private String field;
    private MeetingTime value;

    public AlreadyReservedException(String defaultMessage, String field, MeetingTime value){
        super(defaultMessage);
        this.field = field;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public MeetingTime getValue() {
        return value;
    }
}
