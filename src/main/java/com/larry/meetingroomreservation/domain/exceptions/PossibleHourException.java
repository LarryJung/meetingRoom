package com.larry.meetingroomreservation.domain.exceptions;

public class PossibleHourException extends RuntimeException {
    public PossibleHourException(String message) {
        super(message);
    }
}
