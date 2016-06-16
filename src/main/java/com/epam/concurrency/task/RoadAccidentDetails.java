package com.epam.concurrency.task;

import com.epam.data.RoadAccident;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by Tanmoy on 6/17/2016.
 */
public class RoadAccidentDetails {

    private String accidentId;
    private float longitude;
    private float latitude;
    private String policeForce;
    private String accidentSeverity;
    private int numberOfVehicles;
    private int numberOfCasualties;
    private LocalDate date;
    private LocalTime time;
    private String districtAuthority;
    private String lightConditions;
    private String weatherConditions;
    private String roadSurfaceConditions;

    private String policeForceContact;

    public RoadAccidentDetails(RoadAccident roadAccident){
        accidentId = roadAccident.getAccidentId();
        longitude = roadAccident.getLongitude();
        latitude = roadAccident.getLatitude();
        policeForce = roadAccident.getPoliceForce();
        accidentSeverity = roadAccident.getAccidentSeverity();
        numberOfVehicles = roadAccident.getNumberOfVehicles();
        numberOfCasualties = roadAccident.getNumberOfCasualties();
        date = roadAccident.getDate();
        time = roadAccident.getTime();
        districtAuthority = roadAccident.getDistrictAuthority();
        lightConditions = roadAccident.getLightConditions();
        weatherConditions = roadAccident.getWeatherConditions();
        roadSurfaceConditions = roadAccident.getRoadSurfaceConditions();
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
        this.districtAuthority = districtAuthority;
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

    public String getPoliceForceContact() {
        return policeForceContact;
    }

    public void setPoliceForceContact(String policeForceContact) {
        this.policeForceContact = policeForceContact;
    }
}
