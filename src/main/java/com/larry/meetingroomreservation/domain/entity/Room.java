package com.larry.meetingroomreservation.domain.entity;

import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer roomName;

    @Min(3)
    @Column(nullable = false)
    private Integer occupancy;

    public Room() {

    }

    @Builder
    public Room(int roomName, int occupancy) {
        this.roomName = roomName;
        this.occupancy = occupancy;
    }

    public Long getId() {
        return id;
    }

    public Integer getRoomName() {
        return roomName;
    }

    public int getOccupancy() {
        return occupancy;
    }

    public boolean isPossibleAttendeeNumber(int numberOfAttendee) {
        return occupancy >= numberOfAttendee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(id, room.id) &&
                Objects.equals(roomName, room.roomName) &&
                Objects.equals(occupancy, room.occupancy);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, roomName, occupancy);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomName=" + roomName +
                ", occupancy=" + occupancy +
                '}';
    }
}
