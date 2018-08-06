package com.larry.meetingroomreservation.domainTest;

import com.larry.meetingroomreservation.domain.entity.MeetingTime;
import com.larry.meetingroomreservation.domain.entity.Period;
import com.larry.meetingroomreservation.domain.entity.ThirtyMinuteUnit;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MeetingTimeTest {

    @Test
    public void timeOverlapTest_false1() {
        LocalDate localDate1 = LocalDate.of(2018, 1, 1);
        Period period1 = new Period(new ThirtyMinuteUnit(LocalTime.of(10, 0)), new ThirtyMinuteUnit(LocalTime.of(11, 0)));
        MeetingTime meetingTime1 = new MeetingTime(localDate1, period1);

        LocalDate localDate2 = LocalDate.of(2018, 1, 2);
        Period period2 = new Period(new ThirtyMinuteUnit(LocalTime.of(10, 0)), new ThirtyMinuteUnit(LocalTime.of(11, 0)));
        MeetingTime meetingTime2 = new MeetingTime(localDate2, period2);

        assertFalse(meetingTime1.isMeetingTimeOverlap(meetingTime2));
    }

    @Test
    public void timeOverlapTest_false2() {
        LocalDate localDate1 = LocalDate.of(2018, 1, 1);
        Period period1 = new Period(new ThirtyMinuteUnit(LocalTime.of(10, 0)), new ThirtyMinuteUnit(LocalTime.of(11, 0)));
        MeetingTime meetingTime1 = new MeetingTime(localDate1, period1);

        LocalDate localDate2 = LocalDate.of(2018, 1, 1);
        Period period2 = new Period(new ThirtyMinuteUnit(LocalTime.of(11, 0)), new ThirtyMinuteUnit(LocalTime.of(12, 0)));
        MeetingTime meetingTime2 = new MeetingTime(localDate2, period2);

        assertFalse(meetingTime1.isMeetingTimeOverlap(meetingTime2));
    }

    @Test
    public void timeOverlapTest_true() {
        LocalDate localDate1 = LocalDate.of(2018, 1, 1);
        Period period1 = new Period(new ThirtyMinuteUnit(LocalTime.of(10, 0)), new ThirtyMinuteUnit(LocalTime.of(11, 0)));
        MeetingTime meetingTime1 = new MeetingTime(localDate1, period1);

        LocalDate localDate2 = LocalDate.of(2018, 1, 1);
        Period period2 = new Period(new ThirtyMinuteUnit(LocalTime.of(10, 0)), new ThirtyMinuteUnit(LocalTime.of(12, 0)));
        MeetingTime meetingTime2 = new MeetingTime(localDate2, period2);

        assertTrue(meetingTime1.isMeetingTimeOverlap(meetingTime2));
    }

}
