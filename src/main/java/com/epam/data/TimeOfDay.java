package com.epam.data;

import java.time.LocalTime;

/**
 * Created by bill on 16-5-2.
 *
 */

public enum TimeOfDay {
    MORNING, AFTERNOON, EVENING, NIGHT;

    public static TimeOfDay getTimeOfDay(LocalTime time) {
        int hour = time.getHour();
        switch (hour/6) {
            case 0:
                return TimeOfDay.NIGHT;
            case 1:
                return TimeOfDay.MORNING;
            case 2:
                return TimeOfDay.AFTERNOON;
            case 3:
            default:
                return TimeOfDay.EVENING;
        }
    }

    public int getCategory() {
        if(this.equals(TimeOfDay.MORNING) | this.equals(TimeOfDay.AFTERNOON)) {
            return 1;
        } else {
            return 0;
        }
    }
}

/*
        MORNING - 6 am to 12 pm
        AFTERNOON - 12 pm to 6 pm
        EVENING - 6 pm to 12 am
        NIGHT - 12 am to 6 am
    MORNING,
    AFTERNOON(AFTERNOON"),
    EVENING("EVENING"),
    NIGHT("NIGHT");
    MORNING,
    AFTERNOON,
              EVENING,
            NIGHT;
*/
