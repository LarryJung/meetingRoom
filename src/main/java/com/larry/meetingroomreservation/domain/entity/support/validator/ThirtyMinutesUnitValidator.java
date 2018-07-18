package com.larry.meetingroomreservation.domain.entity.support.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ThirtyMinutesUnitValidator implements ConstraintValidator<ThirtyMinutesUnit, LocalDateTime> {
    @Override
    public void initialize(ThirtyMinutesUnit constraintAnnotation) {

    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return value.getMinute() % 30 == 0;
    }
}
