package com.larry.meetingroomreservation.domain.entity.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.larry.meetingroomreservation.domain.entity.Reservation;
import com.larry.meetingroomreservation.domain.entity.Room;
import com.larry.meetingroomreservation.domain.entity.User;
import com.larry.meetingroomreservation.domain.entity.support.AbstractEntity;
import com.larry.meetingroomreservation.domain.entity.support.validator.EndTimeMustBeAfterStartTime;
import com.larry.meetingroomreservation.domain.entity.support.validator.ThirtyMinutesUnit;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@EndTimeMustBeAfterStartTime
public class ReservationDto extends AbstractEntity {

    @ThirtyMinutesUnit
    @DateTimeFormat
    private LocalTime startTime;

    @ThirtyMinutesUnit
    @DateTimeFormat
    private LocalTime endTime;

    @DateTimeFormat
    private LocalDate reservedDate;

    @Min(3)
    private Integer numberOfAttendee;

    @Builder
    public ReservationDto(LocalTime startTime, LocalTime endTime, LocalDate reservedDate, @Min(3) Integer numberOfAttendee) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.reservedDate = reservedDate;
        this.numberOfAttendee = numberOfAttendee;
    }

    public Reservation toEntity() {
        return Reservation.builder()
                .reservedDate(this.reservedDate)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .numberOfAttendee(this.numberOfAttendee).build();
    }
}

