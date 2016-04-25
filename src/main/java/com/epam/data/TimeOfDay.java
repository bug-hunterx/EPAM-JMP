package com.epam.data;

import java.time.LocalTime;

/**
 * Created by Alexey on 25.04.2016.
 */
public enum TimeOfDay {
    MORNING ("06:00", "11:59:59"),
    AFTERNOON ("12:00", "17:59:59"),
    EVENING ("18:00", "23:59:59"),
    NIGHT ("00:00", "05:59:59");

    private final LocalTime start;
    private final LocalTime end;

    TimeOfDay(String startTime, String endTime) {
        start = LocalTime.parse(startTime);
        end = LocalTime.parse(endTime);
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }
}
