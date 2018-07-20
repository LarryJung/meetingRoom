package com.larry.meetingroomreservation.domain.entity;

import com.larry.meetingroomreservation.domain.exceptions.EndTimeMustBeAfterStartTimeException;
import lombok.*;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Data
@Setter
@Getter
@Embeddable
public class Period {

    @Embedded
    @AttributeOverride(name = "localTime", column = @Column(name = "START_TIME", nullable = false))
    private ThirtyMinuteUnit startTime;

    @Embedded
    @AttributeOverride(name = "localTime", column = @Column(name = "END_TIME", nullable = false))
    private ThirtyMinuteUnit endTime;

    public Period () {

    }

    public Period(ThirtyMinuteUnit startTime, ThirtyMinuteUnit endTime) {
        assertNotNull(startTime);
        assertNotNull(endTime);
        if (!endTime.isAfter(startTime)) {
            throw new EndTimeMustBeAfterStartTimeException("끝시간 시작시간 지켜주세요.");
        }
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean isTimeOverlap(Period target) {
        return target.endTime.isAfter(this.startTime) && this.endTime.isAfter(target.startTime);
    }
}
