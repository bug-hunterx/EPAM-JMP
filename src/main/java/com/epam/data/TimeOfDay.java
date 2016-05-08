package com.epam.data;
import java.time.LocalTime;

import java.time.LocalTime;



/**
 * Created by rahul.mujnani on 5/3/2016.
 */
public class TimeOfDay {

    public enum TimeCategory{
        MORNING, AFTERNOON, EVENING, NIGHT, INVALID_TIME;
    }

    public static String getTimeOfDay(LocalTime localtime){
        if (localtime.getHour() < 6) return TimeCategory.NIGHT.toString();
        else if (localtime.getHour() < 12) return TimeCategory.MORNING.toString();
        else if (localtime.getHour() < 18) return TimeCategory.AFTERNOON.toString();
        else if (localtime.getHour() < 24)  return TimeCategory.EVENING.toString();
        else return TimeCategory.INVALID_TIME.toString();

    }
}

