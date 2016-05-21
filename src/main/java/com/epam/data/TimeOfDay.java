package com.epam.data;

public enum TimeOfDay {
    MORNING(6, 12),
    AFTERNOON(12, 18),
    EVENING(18, 24),
    NIGHT(0, 6);

    private int beginHour;
    private int endHour;

    private TimeOfDay(int beginHour, int endHour){
        this.beginHour = beginHour;
        this.endHour = endHour;
    }

    public static TimeOfDay of(int hour){
        for(TimeOfDay timeOfDay : values()){
            if(timeOfDay.beginHour <= hour && hour < timeOfDay.endHour){
                return timeOfDay;
            }
        }
        throw new IllegalArgumentException("Invalid hour: " + hour);
    }
}