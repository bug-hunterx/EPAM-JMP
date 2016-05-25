package com.epam.dataservice;

import com.epam.data.TimeOfDay;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;
 
import static com.epam.data.TimeOfDay.getTimeOfDay;
import static org.junit.Assert.assertEquals;

/**
 * Created by rahul.mujnani on 5/25/2016.
 */
public class TimeOfDayTest {
    private static TimeOfDay timeservice=new TimeOfDay();

    @Before
    public void init(){}

    @Test
    public void test_getTimeOfDay(){

        assertEquals(timeservice.getTimeOfDay(LocalTime.of(10, 11, 12)), TimeOfDay.TimeCategory.MORNING.toString());
        assertEquals(timeservice.getTimeOfDay(LocalTime.of(13, 14, 15)), TimeOfDay.TimeCategory.AFTERNOON.toString());
        assertEquals(timeservice.getTimeOfDay(LocalTime.of(19, 20, 21)), TimeOfDay.TimeCategory.EVENING.toString());
        assertEquals(timeservice.getTimeOfDay(LocalTime.of(2, 3, 4)), TimeOfDay.TimeCategory.NIGHT.toString());

    }
}
