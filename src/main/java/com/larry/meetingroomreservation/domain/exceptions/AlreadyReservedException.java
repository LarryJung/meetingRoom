package com.larry.meetingroomreservation.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlreadyReservedException extends RuntimeException{

    public static final String DEFAULT_MESSAGE = "겹치는 시간입니다.";

    public AlreadyReservedException(String defaultMessage){
        super(defaultMessage);
    }

    public AlreadyReservedException(){
        super(DEFAULT_MESSAGE);
    }

}
