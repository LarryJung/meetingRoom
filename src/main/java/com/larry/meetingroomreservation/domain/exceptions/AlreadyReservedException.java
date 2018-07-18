package com.larry.meetingroomreservation.domain.exceptions;

public class AlreadyReservedException extends RuntimeException{

    public AlreadyReservedException(String message) {
        super(message);
    }

}
