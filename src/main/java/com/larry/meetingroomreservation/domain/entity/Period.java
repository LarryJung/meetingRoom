package com.larry.meetingroomreservation.domain.entity;

import com.larry.meetingroomreservation.domain.exceptions.EndTimeMustBeAfterStartTimeException;

public class Period {

    private ThirtyMinuteUnit startTime;
    private ThirtyMinuteUnit endTime;

    public Period(ThirtyMinuteUnit startTime, ThirtyMinuteUnit endTime) {
        if (!endTime.isAfter(startTime)) {
            throw new EndTimeMustBeAfterStartTimeException("끝시간 시작시간 지켜주세요.");
        }
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
