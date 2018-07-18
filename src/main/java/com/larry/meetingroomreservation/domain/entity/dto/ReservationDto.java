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
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
public class ReservationDto extends AbstractEntity {

    private String startTime;

    private String endTime;

    private String reservedDate;

    @Min(3)
    private Integer numberOfAttendee;

    @Builder
    public ReservationDto(String startTime, String endTime, String reservedDate, @Min(3) Integer numberOfAttendee) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.reservedDate = reservedDate;
        this.numberOfAttendee = numberOfAttendee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationDto that = (ReservationDto) o;
        return Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(reservedDate, that.reservedDate) &&
                Objects.equals(numberOfAttendee, that.numberOfAttendee);
    }

    @Override
    public int hashCode() {

        return Objects.hash(startTime, endTime, reservedDate, numberOfAttendee);
    }

    @Override
    public String toString() {
        return "ReservationDto{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", reservedDate=" + reservedDate +
                ", numberOfAttendee=" + numberOfAttendee +
                '}';
    }

    public Reservation toEntity() {
        return Reservation.builder()
                .reservedDate(LocalDate.parse(this.reservedDate))
                .startTime(LocalDateTime.parse(this.startTime))
                .endTime(LocalDateTime.parse(this.endTime))
                .numberOfAttendee(this.numberOfAttendee).build();
    }
}

