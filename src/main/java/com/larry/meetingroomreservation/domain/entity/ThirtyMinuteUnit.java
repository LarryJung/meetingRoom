package com.larry.meetingroomreservation.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.larry.meetingroomreservation.domain.exceptions.ThirtyMinutesUnitException;
import java.time.LocalTime;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

public class ThirtyMinuteUnit {

    @JsonFormat(pattern = "HH:mm")
    private LocalTime localTime;

    public ThirtyMinuteUnit(LocalTime localTime) {
        assertNotNull(localTime);
        if (localTime.getMinute() % 30 != 0) {
            throw new ThirtyMinutesUnitException(String.format("30분단위로 입력좀", localTime));
        }
        this.localTime = localTime;
    }

    public boolean isAfter(ThirtyMinuteUnit target) {
        return this.localTime.isAfter(target.localTime);
    }
}
