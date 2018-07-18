package com.larry.meetingroomreservation.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.larry.meetingroomreservation.domain.entity.support.validator.EndTimeMustBeAfterStartTime;
import com.larry.meetingroomreservation.domain.entity.support.validator.ThirtyMinutesUnit;
import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@EndTimeMustBeAfterStartTime(message = "종료 시간은 시작 시간보다 빠를 수 없습니다.")
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ThirtyMinutesUnit
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    @Column
    private LocalDateTime startTime;

    @ThirtyMinutesUnit
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    @Column
    private LocalDateTime endTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
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

    public Long getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return numberOfAttendee == that.numberOfAttendee &&
                Objects.equals(id, that.id) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(reservedDate, that.reservedDate) &&
                Objects.equals(reservedRoom, that.reservedRoom) &&
                Objects.equals(booker, that.booker);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, startTime, endTime, reservedDate, reservedRoom, booker, numberOfAttendee);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", reservedDate=" + reservedDate +
                ", reservedRoom=" + reservedRoom.getRoomName() +
                ", booker=" + booker.getUserId() +
                ", numberOfAttendee=" + numberOfAttendee +
                '}';
    }

    public boolean isOverlap(Reservation target) {
        if (this.booker.equals(target.booker)) {
            return true;
        }
        return !(this.startTime.isAfter(target.endTime) && target.startTime.isAfter(this.endTime));
    }
}
