package com.larry.meetingroomreservation.domain.entity;

import com.larry.meetingroomreservation.domain.exceptions.EndTimeMustBeAfterStartTimeException;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

public class Period {

    private final ThirtyMinuteUnit startTime;
    private final ThirtyMinuteUnit endTime;

    public Period(ThirtyMinuteUnit startTime, ThirtyMinuteUnit endTime) {
        assertNotNull(startTime);
        assertNotNull(endTime);
        if (!endTime.isAfter(startTime)) {
            throw new EndTimeMustBeAfterStartTimeException("끝시간 시작시간 지켜주세요.");
        }
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
