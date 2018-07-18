package com.larry.meetingroomreservation.domainTest;

import com.larry.meetingroomreservation.domain.entity.Reservation;
import com.larry.meetingroomreservation.domain.entity.Room;
import com.larry.meetingroomreservation.domain.entity.User;
import com.larry.meetingroomreservation.domain.entity.support.RoleName;
import org.junit.Test;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservationTest {

    private User larry = new User("larry", "test", "jung", "larry@gmail.com", RoleName.ADMIN);
    private Room room101 = new Room(101, 5);

    @Test
    public void thirtyMinutesValidation() {
       @Valid Reservation reservation = Reservation.builder().booker(larry)
                .reservedDate(LocalDate.of(2018, 7, 18))
                .startTime(LocalDateTime.of(2018, 7, 18, 12, 0))
                .endTime(LocalDateTime.of(2018, 7, 18, 11, 0))
                .reservedRoom(room101)
                .numberOfAttendee(10).build();
    }
}
