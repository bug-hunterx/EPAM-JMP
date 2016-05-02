package com.epam.data;

import java.time.LocalTime;

/**
 * Created by bill on 16-5-2.
 */
public class EnrichRoadAccident extends RoadAccident {
    private String ForceContact;
    private TimeOfDay timeOfDay;

    public EnrichRoadAccident(RoadAccidentBuilder builder) {
        super(builder);
    }

/*
    public EnrichRoadAccident(RoadAccident roadAccident) {
        super(builder);
    }
*/

    public String getForceContact() {
        return ForceContact;
    }

    public void setForceContact(String forceContact) {
        ForceContact = forceContact;
    }

    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(LocalTime time) {
        this.timeOfDay = TimeOfDay.getTimeOfDay(time);
    }
}
