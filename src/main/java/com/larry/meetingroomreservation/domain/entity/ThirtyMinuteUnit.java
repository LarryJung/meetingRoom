package com.larry.meetingroomreservation.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.larry.meetingroomreservation.domain.exceptions.ThirtyMinutesUnitException;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalTime;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@NoArgsConstructor
@Setter
@Getter
@Data
@Embeddable
public class ThirtyMinuteUnit {

    @DateTimeFormat
    @JsonFormat(pattern = "HH:mm")
    private LocalTime localTime;

    public ThirtyMinuteUnit(LocalTime localTime) {
        assertNotNull(localTime);
        if (!isPossibleTime(localTime)) {
            throw new ThirtyMinutesUnitException(String.format("30분단위로 입력좀", localTime));
        }
        this.localTime = localTime;
    }

    private boolean isPossibleTime(LocalTime localTime) {
        return localTime.getMinute() % 30 == 0 && (localTime.getHour() >= 10 && localTime.getHour() <= 18);
    }

    public boolean isAfter(ThirtyMinuteUnit target) {
        return this.localTime.isAfter(target.localTime);
    }
}
