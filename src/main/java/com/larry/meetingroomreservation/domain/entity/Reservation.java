package com.larry.meetingroomreservation.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.larry.meetingroomreservation.domain.entity.support.AbstractEntity;
import com.larry.meetingroomreservation.domain.entity.support.validator.EndTimeMustBeAfterStartTime;
import com.larry.meetingroomreservation.domain.entity.support.validator.ThirtyMinutesUnit;
import com.larry.meetingroomreservation.domain.exceptions.AlreadyReservedException;
import com.larry.meetingroomreservation.domain.exceptions.CannotReserveSameBookerPerDayException;
import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@EndTimeMustBeAfterStartTime(message = "종료 시간은 시작 시간보다 빠를 수 없습니다.")
@Entity
public class Reservation extends AbstractEntity{

    @ThirtyMinutesUnit
    @Column
    private LocalDateTime startTime;

    @ThirtyMinutesUnit
    @Column
    private LocalDateTime endTime;

    @Column
    private LocalDate reservedDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Room reservedRoom;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private User booker;

    @Min(3)
    @Column
    private Integer numberOfAttendee;

    public Reservation() {

    }

    @Builder
    public Reservation(LocalDateTime startTime, LocalDateTime endTime, LocalDate reservedDate, Room reservedRoom, User booker, int numberOfAttendee) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.reservedDate = reservedDate;
        this.reservedRoom = reservedRoom;
        this.booker = booker;
        this.numberOfAttendee = numberOfAttendee;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public LocalDate getReservedDate() {
        return reservedDate;
    }

    public Room getReservedRoom() {
        return reservedRoom;
    }

    public User getBooker() {
        return booker;
    }

    public int getNumberOfAttendee() {
        return numberOfAttendee;
    }

    public boolean isOverlap(Reservation target) {
        if (this.booker.equals(target.booker)) {
            throw new CannotReserveSameBookerPerDayException("예약은 해당 날짜에 1번만 가능합니다.");
        }
        if (target.endTime.isAfter(this.startTime) && this.endTime.isAfter(target.startTime)) {
            throw new AlreadyReservedException("겹치는 시간입니다.");
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(reservedDate, that.reservedDate) &&
                Objects.equals(reservedRoom, that.reservedRoom) &&
                Objects.equals(booker, that.booker) &&
                Objects.equals(numberOfAttendee, that.numberOfAttendee);
    }

    @Override
    public int hashCode() {

        return Objects.hash(startTime, endTime, reservedDate, reservedRoom, booker, numberOfAttendee);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", reservedDate=" + reservedDate +
                ", reservedRoom=" + reservedRoom.getRoomName() +
                ", booker=" + booker.getName() +
                ", numberOfAttendee=" + numberOfAttendee +
                '}';
    }

    public Reservation bookBy(User loginUser) {
        this.booker = loginUser;
        return this;
    }

    public Reservation bookRoom(Room room) {
        this.reservedRoom = room;
        return this;
    }
}
