package com.larry.meetingroomreservation.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.larry.meetingroomreservation.domain.exceptions.PossibleHourException;
import com.larry.meetingroomreservation.domain.exceptions.ThirtyMinutesUnitException;
import lombok.*;

import javax.persistence.Embeddable;
import java.time.LocalTime;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@NoArgsConstructor
@Setter
@Getter
@Data
@Embeddable
public class ThirtyMinuteUnit {

    @JsonFormat(pattern = "HH:mm")
    private LocalTime localTime;

    public ThirtyMinuteUnit(LocalTime localTime) {
        assertNotNull(localTime);
        if (!isThirtyMinuteUnit(localTime)) {
            throw new ThirtyMinutesUnitException(String.format("30분단위로 입력좀", localTime));
        }
        if (!isPossibleHour(localTime)) {
            throw new PossibleHourException("10시에서 18시 사이의 시간만 예약 가능합니다.");
        }
        this.localTime = localTime;
    }

    private boolean isThirtyMinuteUnit(LocalTime localTime) {
        return localTime.getMinute() % 30 == 0;
    }

    private boolean isPossibleHour(LocalTime localTime) {
        return localTime.getHour() >= 10 && localTime.getHour() <= 18;
    }

    public boolean isAfter(ThirtyMinuteUnit target) {
        return this.localTime.isAfter(target.localTime);
    }
}
