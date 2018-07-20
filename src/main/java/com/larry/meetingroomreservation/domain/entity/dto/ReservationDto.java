package com.larry.meetingroomreservation.domain.entity.dto;


import com.larry.meetingroomreservation.domain.entity.Reservation;
import com.larry.meetingroomreservation.domain.entity.support.AbstractEntity;
import com.larry.meetingroomreservation.domain.entity.support.MeetingTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Embedded;
import javax.validation.constraints.Min;

@Setter
@Getter
@NoArgsConstructor
public class ReservationDto extends AbstractEntity {

    @Embedded
    private MeetingTime meetingTime;

    @Min(3)
    private Integer numberOfAttendee;

    public Reservation toEntity() {
        return new Reservation(meetingTime, numberOfAttendee);
    }
}

