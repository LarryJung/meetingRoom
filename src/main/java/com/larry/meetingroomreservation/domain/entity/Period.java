package com.larry.meetingroomreservation.domain.entity;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.larry.meetingroomreservation.domain.exceptions.EndTimeMustBeAfterStartTimeException;
import com.larry.meetingroomreservation.domain.exceptions.ValidationException;
import lombok.*;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import java.time.LocalTime;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Data
@Setter
@Getter
@Embeddable
public class Period {

    @JsonUnwrapped(prefix = "start_")
    @Embedded
    @AttributeOverride(name = "time", column = @Column(name = "START_TIME", nullable = false))
    private ThirtyMinuteUnit startTime;

    @JsonUnwrapped(prefix = "end_")
    @Embedded
    @AttributeOverride(name = "time", column = @Column(name = "END_TIME", nullable = false))
    private ThirtyMinuteUnit endTime;

    public Period () {

    }

    public Period(ThirtyMinuteUnit startTime, ThirtyMinuteUnit endTime) {
        assertNotNull(startTime);
        assertNotNull(endTime);
        if (!endTime.isAfter(startTime)) {
            throw new ValidationException("시작시간은 끝나는 시간 이전이어야 합니다.", "time", this);
        }
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Period(LocalTime startTime, LocalTime endTime) {
        this(new ThirtyMinuteUnit(startTime), new ThirtyMinuteUnit(endTime));
    }

    public boolean isTimeOverlap(Period target) {
        return target.endTime.isAfter(this.startTime) && this.endTime.isAfter(target.startTime);
    }
}
