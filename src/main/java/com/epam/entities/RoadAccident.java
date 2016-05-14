package com.epam.entities;

import com.epam.data.TimeOfDay;

import javax.persistence.*;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Contains information about one road accident
 */
@Entity
@Table(name = "accidents")
public class RoadAccident implements Serializable {
    @Id
    @Column(name = "Accident_Index")
    private String accidentId;

    @Transient
    private float longitude;

    @Transient
    private float latitude;

    @Transient
    private String policeForce;

    @Transient
    private String forceContact;

    @Transient
    private String accidentSeverity;

    @Transient
    private int numberOfVehicles;

    @Transient
    private int numberOfCasualties;

    @Transient
    private LocalDate date;

    @Transient
    private LocalTime time;

    @Transient
    private TimeOfDay timeOfDay;

    @Transient
    private String districtAuthority;

    @Transient
    private String lightConditions;

    @Transient
    private String weatherConditions;

    @Transient
    private String roadSurfaceConditions;

    RoadAccident(RoadAccidentBuilder builder){
        this.accidentId = builder.accidentId;
        this.longitude = builder.longitude;
        this.latitude = builder.latitude;
        this.policeForce = builder.policeForce;
        this.accidentSeverity = builder.accidentSeverity;
        this.numberOfVehicles = builder.numberOfVehicles;
        this.numberOfCasualties = builder.numberOfCasualties;
        this.date = builder.date;
        this.time = builder.time;
        this.districtAuthority = builder.districtAuthority;
        this.lightConditions = builder.lightConditions;
        this.weatherConditions = builder.weatherConditions;
        this.roadSurfaceConditions = builder.roadSurfaceConditions;
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

    public String getLightConditions() {
        return lightConditions;
    }

    public void setLightConditions(String lightConditions) {
        this.lightConditions = lightConditions;
    }

    public String getWeatherConditions() {
        return weatherConditions;
    }

    public void setWeatherConditions(String weatherConditions) {
        this.weatherConditions = weatherConditions;
    }

    public String getRoadSurfaceConditions() {
        return roadSurfaceConditions;
    }

    public void setRoadSurfaceConditions(String roadSurfaceConditions) {
        this.roadSurfaceConditions = roadSurfaceConditions;
    }

    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(TimeOfDay timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public String getForceContact() {
        return forceContact;
    }

    public void setForceContact(String forceContact) {
        this.forceContact = forceContact;
    }

    @Override
    public String toString() {
        return "RoadAccident{" +
                "accidentId='" + accidentId + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", policeForce='" + policeForce + '\'' +
                ", forceContact='" + forceContact + '\'' +
                ", accidentSeverity='" + accidentSeverity + '\'' +
                ", numberOfVehicles=" + numberOfVehicles +
                ", numberOfCasualties=" + numberOfCasualties +
                ", date=" + date +
                ", time=" + time +
                ", timeOfDay=" + timeOfDay +
                ", districtAuthority='" + districtAuthority + '\'' +
                ", lightConditions='" + lightConditions + '\'' +
                ", weatherConditions='" + weatherConditions + '\'' +
                ", roadSurfaceConditions='" + roadSurfaceConditions + '\'' +
                '}';
    }
}
