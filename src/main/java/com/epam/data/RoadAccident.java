package com.epam.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Contains information about one road accident
 */
@Entity
public class RoadAccident {
    @Id
    private String accidentId;
    private float longitude;
    private float latitude;
    private String policeForce;
    private String accidentSeverity;
    private int numberOfVehicles;
    private int numberOfCasualties;
    private LocalDate date;
    private LocalTime time;
    private int year;
    private String districtAuthority;
    private String lightCondition;
    private String weatherCondition;
    private String roadSurfaceCondition;

    public RoadAccident() {
    }

    RoadAccident(RoadAccidentBuilder builder){
        this.accidentId = builder.accidentId;
        this.longitude = builder.longitude;
        this.latitude = builder.latitude;
        this.policeForce = builder.policeForce;
        this.accidentSeverity = builder.accidentSeverity;
        this.numberOfVehicles = builder.numberOfVehicles;
        this.numberOfCasualties = builder.numberOfCasualties;
        this.date = builder.date;
        this.year = date.getYear();
        this.time = builder.time;
        this.districtAuthority = builder.districtAuthority;
        this.lightCondition = builder.lightConditions;
        this.weatherCondition = builder.weatherConditions;
        this.roadSurfaceCondition = builder.roadSurfaceConditions;
    }


    public String getAccidentId() {
        return accidentId;
    }

    public void setAccidentId(String accidentId) {
        this.accidentId = accidentId;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getPoliceForce() {
        return policeForce;
    }

    public void setPoliceForce(String policeForce) {
        this.policeForce = policeForce;
    }

    public String getAccidentSeverity() {
        return accidentSeverity;
    }

    public void setAccidentSeverity(String accidentSeverity) {
        this.accidentSeverity = accidentSeverity;
    }

    public int getNumberOfVehicles() {
        return numberOfVehicles;
    }

    public void setNumberOfVehicles(int numberOfVehicles) {
        this.numberOfVehicles = numberOfVehicles;
    }

    public int getNumberOfCasualties() {
        return numberOfCasualties;
    }

    public void setNumberOfCasualties(int numberOfCasualties) {
        this.numberOfCasualties = numberOfCasualties;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public DayOfWeek getDayOfWeek() {
        return date.getDayOfWeek();
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getDistrictAuthority() {
        return districtAuthority;
    }

    public void setDistrictAuthority(String districtAuthority) {
        this.districtAuthority= districtAuthority;
    }

    public String getLightCondition() {
        return lightCondition;
    }

    public void setLightCondition(String lightCondition) {
        this.lightCondition = lightCondition;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public String getRoadSurfaceCondition() {
        return roadSurfaceCondition;
    }

    public void setRoadSurfaceCondition(String roadSurfaceCondition) {
        this.roadSurfaceCondition = roadSurfaceCondition;
    }
    
    public String toString() {
    	return "RoadAccident:" +
    			"\nid:" + this.accidentId +
    			"\ndistrictAuthority:" + this.districtAuthority+
    			"\npoliceForce:" + this.policeForce +
    			"\nroadSurfaceCondition:" + this.roadSurfaceCondition +
    			"\nweatherCondition:" + this.weatherCondition;
    			
    			
    			
    } 
}
