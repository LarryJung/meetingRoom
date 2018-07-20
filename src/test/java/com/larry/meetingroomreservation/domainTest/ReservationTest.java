package com.larry.meetingroomreservation.domainTest;

import com.larry.meetingroomreservation.domain.entity.Period;
import com.larry.meetingroomreservation.domain.entity.Reservation;
import com.larry.meetingroomreservation.domain.entity.Room;
import com.larry.meetingroomreservation.domain.entity.User;
import com.larry.meetingroomreservation.domain.entity.MeetingTime;
import com.larry.meetingroomreservation.domain.entity.support.RoleName;
import com.larry.meetingroomreservation.domain.exceptions.EndTimeMustBeAfterStartTimeException;
import com.larry.meetingroomreservation.domain.exceptions.ExcessAttendeeException;
import com.larry.meetingroomreservation.domain.exceptions.PossibleHourException;
import com.larry.meetingroomreservation.domain.exceptions.ThirtyMinutesUnitException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ReservationTest {

    private final Logger log = LoggerFactory.getLogger(ReservationTest.class);

    private User larry = new User("larry", "test", "jung", "larry@gmail.com", RoleName.ADMIN);
    private final int OCCUPANCY = 5;
    private Room room101 = new Room(101, OCCUPANCY);
    private Reservation reservation;


    @Before
    public void setup() {
        reservation = Reservation.builder().booker(larry).reservedRoom(room101).build();
    }

    @Test
    public void initiateReservation() {
        MeetingTime meetingTime = new MeetingTime(LocalDate.of(2018, 7, 18), new Period(LocalTime.of(10, 0), LocalTime.of(11, 0)));
        reservation = reservation.builder()
                .numberOfAttendee(OCCUPANCY)
                .meetingTime(meetingTime).build();
    }

    @Test(expected = ThirtyMinutesUnitException.class)
    public void thirtyMinute_fail() {
        MeetingTime meetingTime = new MeetingTime(LocalDate.of(2018, 7, 18), new Period(LocalTime.of(10, 1), LocalTime.of(11, 0)));
        reservation = reservation.builder()
                .numberOfAttendee(OCCUPANCY)
                .meetingTime(meetingTime).build();
    }

    @Test(expected = EndTimeMustBeAfterStartTimeException.class)
    public void endTimeMustBeAfterStartTime_fail() {
        MeetingTime meetingTime = new MeetingTime(LocalDate.of(2018, 7, 18), new Period(LocalTime.of(11, 0), LocalTime.of(10, 0)));
        reservation = reservation.builder()
                .numberOfAttendee(OCCUPANCY)
                .meetingTime(meetingTime).build();
    }

    @Test(expected = PossibleHourException.class)
    public void before_10oclock() {
        MeetingTime meetingTime = new MeetingTime(LocalDate.of(2018, 7, 18), new Period(LocalTime.of(9, 0), LocalTime.of(10, 0)));
        reservation = reservation.builder()
                .numberOfAttendee(OCCUPANCY)
                .meetingTime(meetingTime).build();
    }

    @Test(expected = PossibleHourException.class)
    public void after_18oclock() {
        MeetingTime meetingTime = new MeetingTime(LocalDate.of(2018, 7, 18), new Period(LocalTime.of(9, 0), LocalTime.of(19, 0)));
        reservation = reservation.builder()
                .numberOfAttendee(OCCUPANCY)
                .meetingTime(meetingTime).build();
    }

    // 얘는 validator 무언가를 사용하여 검증해야 할듯.
    @Test
    public void attendee_less_minimum() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        MeetingTime meetingTime = new MeetingTime(LocalDate.of(2018, 7, 18), new Period(LocalTime.of(10, 0), LocalTime.of(11, 0)));
        Reservation reservation1 = new Reservation(meetingTime, 2, room101);

        Set<ConstraintViolation<Reservation>> constraintViolations = validator.validate(reservation1);
        assertThat(constraintViolations.size(), is(1));

        for (ConstraintViolation<Reservation> constraintViolation : constraintViolations) {
            log.debug("violation error message : {}", constraintViolation.getMessage());
        }
    }

    @Test(expected = ExcessAttendeeException.class)
    public void attendee_access_maximum() {
        MeetingTime meetingTime = new MeetingTime(LocalDate.of(2018, 7, 18), new Period(LocalTime.of(10, 0), LocalTime.of(11, 0)));
        Reservation reservation1 = new Reservation(meetingTime, OCCUPANCY+1, room101);
    }

}
