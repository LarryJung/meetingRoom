package com.larry.meetingroomreservation.domain.entity;

import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @Column
    private LocalDate reservedDate;

    @ManyToOne
    @JoinColumn
    private Room reservedRoom;

    @ManyToOne
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
}
