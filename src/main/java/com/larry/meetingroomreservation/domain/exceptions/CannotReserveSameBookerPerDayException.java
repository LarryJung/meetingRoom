package com.larry.meetingroomreservation.domain.exceptions;

public class CannotReserveSameBookerPerDayException extends RuntimeException {
    public CannotReserveSameBookerPerDayException(String message) {
        super(message);
    }
}

