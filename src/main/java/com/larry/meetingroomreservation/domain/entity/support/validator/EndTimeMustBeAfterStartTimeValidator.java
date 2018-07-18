package com.larry.meetingroomreservation.domain.entity.support.validator;

import com.larry.meetingroomreservation.domain.entity.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EndTimeMustBeAfterStartTimeValidator implements ConstraintValidator<EndTimeMustBeAfterStartTime, Reservation>{

    private final Logger log = LoggerFactory.getLogger(EndTimeMustBeAfterStartTimeValidator.class);

    private String message;

    @Override
    public void initialize(EndTimeMustBeAfterStartTime constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Reservation value, ConstraintValidatorContext context) {
        log.info("reserve time : {}", value);
        if (value.getStartTime() == null || value.getEndTime() == null) {
            throw new RuntimeException("시작시간 끝시간이 중복됩니다.");
        }
        if (!value.getEndTime().isAfter(value.getStartTime())) {
            throw new RuntimeException("시작 시간이 먼저여야 합니다.");
        }
        return true;
    }
}
