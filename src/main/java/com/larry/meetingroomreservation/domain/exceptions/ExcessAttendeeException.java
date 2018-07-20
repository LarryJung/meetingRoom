package com.larry.meetingroomreservation.domain.exceptions;

public class ExcessAttendeeException extends RuntimeException {
    public ExcessAttendeeException(String message) {
        super(message);
    }
}
