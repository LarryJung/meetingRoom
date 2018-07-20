package com.larry.meetingroomreservation.domain.entity.support;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.larry.meetingroomreservation.domain.entity.Period;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Embeddable
public class MeetingTime {

    @Embedded
    private Period period;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate reservedDate;

    public boolean isTimeOverlap(MeetingTime target) {
        return this.reservedDate.equals(target.reservedDate) && (this.period.isTimeOverlap(target.period));

    }
}
