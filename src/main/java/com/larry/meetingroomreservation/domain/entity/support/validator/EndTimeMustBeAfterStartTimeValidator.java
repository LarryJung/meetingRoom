package com.larry.meetingroomreservation.domain.entity.support.validator;

import com.larry.meetingroomreservation.domain.entity.Reservation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EndTimeMustBeAfterStartTimeValidator implements ConstraintValidator<EndTimeMustBeAfterStartTime, Reservation>{
    private String message;


    @Override
    public void initialize(EndTimeMustBeAfterStartTime constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Reservation value, ConstraintValidatorContext context) {
        if (value.getStartTime() == null || value.getEndTime() == null) {
            return false;
        }
        if (!value.getEndTime().isAfter(value.getStartTime())) {
            throw new RuntimeException("오류오류");
        }
        return true;
    }
}
