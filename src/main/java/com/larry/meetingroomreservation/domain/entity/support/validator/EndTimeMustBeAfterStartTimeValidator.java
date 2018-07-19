package com.larry.meetingroomreservation.domain.entity.support.validator;

import com.larry.meetingroomreservation.domain.entity.Reservation;
import com.larry.meetingroomreservation.domain.entity.dto.ReservationDto;
import com.larry.meetingroomreservation.domain.exceptions.EndTimeMustBeAfterStartTimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EndTimeMustBeAfterStartTimeValidator implements ConstraintValidator<EndTimeMustBeAfterStartTime, ReservationDto>{

    private final Logger log = LoggerFactory.getLogger(EndTimeMustBeAfterStartTimeValidator.class);

    private String message;

    @Override
    public void initialize(EndTimeMustBeAfterStartTime constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(ReservationDto value, ConstraintValidatorContext context) {
        if (value.getStartTime() == null || value.getEndTime() == null) {
            log.info("끝시간 시작시간 중복");
            return false;
//            throw new EndTimeMustBeAfterStartTimeException("시작시간 끝시간이 중복됩니다.");
        }
        if (!value.getEndTime().isAfter(value.getStartTime())) {
            log.info("끝시간이 먼저임");
            return false;
//            throw new EndTimeMustBeAfterStartTimeException("시작 시간이 먼저여야 합니다.");
        }
        return true;
    }
}
