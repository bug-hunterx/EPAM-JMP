package com.epam.dataservice;

import java.time.LocalTime;

/**
 * Created by Nick on 27.04.2016.
 */
public enum DayTime {
    MORNING, AFTERNOON, EVENING, NIGHT;

    public static DayTime getDayTime(LocalTime time) {
        if (time.getHour() < 6) return NIGHT;
        if (time.getHour() < 12) return MORNING;
        if (time.getHour() < 18) return AFTERNOON;
        return EVENING;
    }
}
