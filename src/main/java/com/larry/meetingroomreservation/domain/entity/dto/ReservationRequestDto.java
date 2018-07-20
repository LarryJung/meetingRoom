package com.larry.meetingroomreservation.domain.entity.dto;


import com.larry.meetingroomreservation.domain.entity.Reservation;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Setter
@Getter
@NoArgsConstructor
public class ReservationRequestDto {

    private LocalDate reservedDate;

    @DateTimeFormat
    private LocalTime startTime;

    @DateTimeFormat
    private LocalTime endTime;

    private Integer numberOfAttendee;

    public Reservation toEntity() {
        return new Reservation(reservedDate, startTime, endTime, numberOfAttendee);
    }


}

