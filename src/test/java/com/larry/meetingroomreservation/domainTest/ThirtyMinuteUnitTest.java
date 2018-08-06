package com.larry.meetingroomreservation.domainTest;

import com.larry.meetingroomreservation.domain.entity.ThirtyMinuteUnit;
import com.larry.meetingroomreservation.domain.exceptions.ValidationException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalTime;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ThirtyMinuteUnitTest {

    private ThirtyMinuteUnit thirtyMinuteUnit;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();


    @Test
    public void thirtyUnitConstructor() {
        thirtyMinuteUnit = new ThirtyMinuteUnit(LocalTime.of(10, 0));
        assertThat(thirtyMinuteUnit.getTime().toString(), is("10:00"));
    }

    @Test(expected = ValidationException.class)
    public void no_thirty_unit() {
        thirtyMinuteUnit = new ThirtyMinuteUnit(LocalTime.of(10, 1));
        expectedEx.expect(ValidationException.class);
        expectedEx.expectMessage("30분단위로 입력좀");
    }

    @Test(expected = ValidationException.class)
    public void less_boundary() {
        thirtyMinuteUnit = new ThirtyMinuteUnit(LocalTime.of(9, 0));
        expectedEx.expect(ValidationException.class);
        expectedEx.expectMessage("10시에서 18시 사이의 시간만 예약 가능합니다.");
    }

    @Test(expected = ValidationException.class)
    public void over_boundary() {
        thirtyMinuteUnit = new ThirtyMinuteUnit(LocalTime.of(19, 0));
        expectedEx.expect(ValidationException.class);
        expectedEx.expectMessage("10시에서 18시 사이의 시간만 예약 가능합니다.");
    }

}
