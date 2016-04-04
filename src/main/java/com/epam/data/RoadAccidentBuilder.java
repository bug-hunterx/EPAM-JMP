package com.epam.data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by Tkachi on 2016/3/31.
 */
public class RoadAccidentBuilder {
    String accidentId;
    float longitude;
    float latitude;
    String policeForce;
    String accidentSeverity;
    int numberOfVehicles;
    int numberOfCasualties;
    LocalDate date;
    LocalTime time;
    String districtAuthority;
    String lightConditions;
    String weatherConditions;
    String roadSurfaceConditions;


    public RoadAccidentBuilder(String accidentId){
        this.accidentId = accidentId;
    }

    public RoadAccidentBuilder withLongitude(float longitude) {
        this.longitude = longitude;
        return this;
    }

    public RoadAccidentBuilder withLatitude(float latitude) {
        this.latitude = latitude;
        return this;
    }

    public RoadAccidentBuilder withPoliceForce(String policeForce) {
        this.policeForce = policeForce;
        return this;
    }

    public RoadAccidentBuilder withAccidentSeverity(String accidentSeverity) {
        this.accidentSeverity = accidentSeverity;
        return this;
    }

    public RoadAccidentBuilder withNumberOfVehicles(int numberOfVehicles) {
        this.numberOfVehicles = numberOfVehicles;
        return this;
    }

    public RoadAccidentBuilder withNumberOfCasualties(int numberOfCasualties) {
        this.numberOfCasualties = numberOfCasualties;
        return this;
    }

    public RoadAccidentBuilder withDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public RoadAccidentBuilder withTime(LocalTime time) {
        this.time = time;
        return this;
    }

    public RoadAccidentBuilder withDistrictAuthority(String districtAuthority) {
        this.districtAuthority = districtAuthority;
        return this;
    }

    public RoadAccidentBuilder withLightConditions(String lightConditions) {
        this.lightConditions = lightConditions;
        return this;
    }

    public RoadAccidentBuilder withWeatherConditions(String weatherConditions) {
        this.weatherConditions = weatherConditions;
        return this;
    }

    public RoadAccidentBuilder withRoadSurfaceConditions(String roadSurfaceConditions) {
        this.roadSurfaceConditions = roadSurfaceConditions;
        return this;
    }

    public RoadAccident build(){
        return new RoadAccident(this);
    }
}
