package com.larry.meetingroomreservation.domainTest;

import com.larry.meetingroomreservation.domain.entity.Period;
import com.larry.meetingroomreservation.domain.entity.ThirtyMinuteUnit;
import com.larry.meetingroomreservation.domain.exceptions.ValidationException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalTime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PeriodTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();


    @Test
    public void constructorTest() {
        ThirtyMinuteUnit startTime = new ThirtyMinuteUnit(LocalTime.of(10, 0));
        ThirtyMinuteUnit endTime = new ThirtyMinuteUnit(LocalTime.of(11, 0));
        Period period = new Period(startTime, endTime);
        assertThat(period.getStartTime(), is(startTime));
    }

    @Test(expected = ValidationException.class)
    public void endTimeBeforeStartTime() {
        ThirtyMinuteUnit startTime = new ThirtyMinuteUnit(LocalTime.of(11, 0));
        ThirtyMinuteUnit endTime = new ThirtyMinuteUnit(LocalTime.of(10, 0));
        Period period = new Period(startTime, endTime);

        expectedEx.expect(ValidationException.class);
        expectedEx.expectMessage("시작시간은 끝나는 시간 이전이어야 합니다.");
    }

    @Test
    public void periodOverlapTest() {
        Period period1 = new Period(new ThirtyMinuteUnit(LocalTime.of(10, 0)), new ThirtyMinuteUnit(LocalTime.of(11, 0)));
        Period period2 = new Period(new ThirtyMinuteUnit(LocalTime.of(10, 0)), new ThirtyMinuteUnit(LocalTime.of(12, 0)));
        assertTrue(period1.isPeriodOverlap(period2));
    }
}
