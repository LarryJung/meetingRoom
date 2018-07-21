package com.larry.meetingroomreservation.domainTest;

import com.larry.meetingroomreservation.domain.entity.Period;
import com.larry.meetingroomreservation.domain.entity.Reservation;
import com.larry.meetingroomreservation.domain.entity.Room;
import com.larry.meetingroomreservation.domain.entity.User;
import com.larry.meetingroomreservation.domain.entity.MeetingTime;
import com.larry.meetingroomreservation.domain.entity.support.RoleName;
import com.larry.meetingroomreservation.domain.exceptions.ValidationException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ReservationTest {

    private final Logger log = LoggerFactory.getLogger(ReservationTest.class);

    private User larry = new User("larry", "test", "jung", "larry@gmail.com", RoleName.ADMIN);
    private User charry = new User("charry", "test", "chae", "charry@gmail.com", RoleName.USER);
    private final int OCCUPANCY = 5;
    private Room room101 = new Room(101, OCCUPANCY);
    private Reservation reservation;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

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

    @Test(expected = ValidationException.class)
    public void thirtyMinute_fail() {
        MeetingTime meetingTime = new MeetingTime(LocalDate.of(2018, 7, 18), new Period(LocalTime.of(10, 1), LocalTime.of(11, 0)));
        reservation = reservation.builder()
                .numberOfAttendee(OCCUPANCY)
                .meetingTime(meetingTime).build();
        expectedEx.expect(ValidationException.class);
        expectedEx.expectMessage("30분단위로 입력좀");
    }

    @Test(expected = ValidationException.class)
    public void endTimeMustBeAfterStartTime_fail() {
        MeetingTime meetingTime = new MeetingTime(LocalDate.of(2018, 7, 18), new Period(LocalTime.of(11, 0), LocalTime.of(10, 0)));
        reservation = reservation.builder()
                .numberOfAttendee(OCCUPANCY)
                .meetingTime(meetingTime).build();
        expectedEx.expect(ValidationException.class);
        expectedEx.expectMessage("시작시간은 끝나는 시간 이전이어야 합니다.");
    }

    @Test(expected = ValidationException.class)
    public void before_10oclock() {
        MeetingTime meetingTime = new MeetingTime(LocalDate.of(2018, 7, 18), new Period(LocalTime.of(9, 0), LocalTime.of(10, 0)));
        reservation = reservation.builder()
                .numberOfAttendee(OCCUPANCY)
                .meetingTime(meetingTime).build();
        expectedEx.expect(ValidationException.class);
        expectedEx.expectMessage("10시에서 18시 사이의 시간만 예약 가능합니다.");
    }

    @Test(expected = ValidationException.class)
    public void after_18oclock() {
        MeetingTime meetingTime = new MeetingTime(LocalDate.of(2018, 7, 18), new Period(LocalTime.of(9, 0), LocalTime.of(19, 0)));
        reservation = reservation.builder()
                .numberOfAttendee(OCCUPANCY)
                .meetingTime(meetingTime).build();
        expectedEx.expect(ValidationException.class);
        expectedEx.expectMessage("10시에서 18시 사이의 시간만 예약 가능합니다.");
    }

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

    @Test(expected = ValidationException.class)
    public void attendee_access_maximum() {
        MeetingTime meetingTime = new MeetingTime(LocalDate.of(2018, 7, 18), new Period(LocalTime.of(10, 0), LocalTime.of(11, 0)));
        Reservation reservation1 = new Reservation(meetingTime, OCCUPANCY+1, room101);
        expectedEx.expect(ValidationException.class);
        expectedEx.expectMessage(String.format("인원 초과입니다. 허용인원 : %d", OCCUPANCY));
    }

    @Test(expected = ValidationException.class)
    public void duplicate_time() {
        MeetingTime meetingTime = new MeetingTime(LocalDate.of(2018, 7, 18), new Period(LocalTime.of(10, 0), LocalTime.of(12, 0)));
        Reservation reservation = Reservation.builder()
                .booker(larry)
                .reservedRoom(room101)
                .numberOfAttendee(OCCUPANCY)
                .meetingTime(meetingTime).build();
        log.info("resee : {}", reservation);
        MeetingTime meetingTime2 = new MeetingTime(LocalDate.of(2018, 7, 18), new Period(LocalTime.of(10, 0), LocalTime.of(13, 0)));
        Reservation reservation2 = Reservation.builder()
                .booker(charry)
                .reservedRoom(room101)
                .numberOfAttendee(OCCUPANCY)
                .meetingTime(meetingTime2).build();

        reservation.isOverlap(reservation2);
        expectedEx.expect(ValidationException.class);
        expectedEx.expectMessage("겹치는 시간입니다.");
    }

    @Test(expected = ValidationException.class)
    public void duplicate_booker() {
        MeetingTime meetingTime = new MeetingTime(LocalDate.of(2018, 7, 18), new Period(LocalTime.of(10, 0), LocalTime.of(12, 0)));
        Reservation reservation = Reservation.builder()
                .booker(larry)
                .reservedRoom(room101)
                .numberOfAttendee(OCCUPANCY)
                .meetingTime(meetingTime).build();

        Reservation reservation2 = Reservation.builder()
                .booker(charry)
                .reservedRoom(room101)
                .numberOfAttendee(OCCUPANCY)
                .meetingTime(meetingTime).build();

        reservation.isOverlap(reservation2);
        expectedEx.expect(ValidationException.class);
        expectedEx.expectMessage("예약은 해당 날짜에 1번만 가능합니다.");
    }


}
