package com.larry.meetingroomreservation.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.larry.meetingroomreservation.domain.exceptions.ValidationException;
import lombok.*;

import javax.persistence.Embeddable;
import java.time.LocalTime;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@NoArgsConstructor
@Setter
@Data
@Embeddable
public class ThirtyMinuteUnit {

    private static final int MINUTE_UNIT = 30;
    private static final int MIN_HOUR = 10;
    private static final int MAX_HOUR = 18;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;

    public ThirtyMinuteUnit(LocalTime localTime) {
        assertNotNull(localTime);
        if (!isThirtyMinuteUnit(localTime)) {
            throw new ValidationException("30분단위로 입력좀", "time", localTime);
        }
        if (!isPossibleHour(localTime)) {
            throw new ValidationException("10시에서 18시 사이의 시간만 예약 가능합니다.", "time", localTime);
        }
        this.time = localTime;
    }

    private boolean isThirtyMinuteUnit(LocalTime localTime) {
        return localTime.getMinute() % MINUTE_UNIT == 0;
    }

    private boolean isPossibleHour(LocalTime localTime) {
        return localTime.getHour() >= MIN_HOUR && localTime.getHour() <= MAX_HOUR;
    }

    public boolean isAfter(ThirtyMinuteUnit target) {
        return this.time.isAfter(target.time);
    }
}
