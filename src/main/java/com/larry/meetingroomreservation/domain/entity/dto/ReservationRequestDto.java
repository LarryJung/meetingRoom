package com.larry.meetingroomreservation.domain.entity.dto;


import com.larry.meetingroomreservation.domain.entity.Reservation;
import com.larry.meetingroomreservation.domain.entity.Room;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Setter
@Getter
public class ReservationRequestDto {

    private LocalDate reservedDate;

    @DateTimeFormat
    private LocalTime startTime;

    @DateTimeFormat
    private LocalTime endTime;

    @Min(3)
    private Integer numberOfAttendee;

    public ReservationRequestDto() {

    }

    public ReservationRequestDto(LocalDate reservedDate, LocalTime startTime, LocalTime endTime, Integer numberOfAttendee) {
        this.reservedDate = reservedDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numberOfAttendee = numberOfAttendee;
    }

    public Reservation toEntity(Room room) {
        return new Reservation(reservedDate, startTime, endTime, numberOfAttendee, room);
    }

}

