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

    @Column(nullable = false)
    private boolean reservable = true;

    @Column(nullable = false)
    private String roomName;

    @Min(3)
    @Column(nullable = false)
    private Integer occupancy;

    public Room() {

    }

    @Builder
    public Room(String roomName, int occupancy) {
        this.roomName = roomName;
        this.occupancy = occupancy;
    }

    public Long getId() {
        return id;
    }

    public boolean isReservable() {
        return reservable;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getOccupancy() {
        return occupancy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return reservable == room.reservable &&
                occupancy == room.occupancy &&
                Objects.equals(id, room.id) &&
                Objects.equals(roomName, room.roomName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, reservable, roomName, occupancy);
    }
}
