package com.epam.entities;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Transactional
@Entity
@Table(name = "accidents")
public class Accident implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "accident_Index", nullable = false)
    private String accidentIndex;

    @Column(name = "Longitude", nullable = false, length = 10)
    private Float longitude;
    @Column(name = "Latitude", nullable = false, length = 10)
    private Float latitude;
    @Column(name = "Police_Force", nullable = false)
    private Integer policeForce;
    @Column(name = "Accident_Severity", nullable = false)
    private Integer accidentSeverity;
    @Column(name = "Number_of_Vehicles", nullable = false)
    private Integer numberOfVehicles;
    @Column(name = "Number_of_Casualties", nullable = false)
    private Integer numberOfCasualties;
    @Column(name = "Date", nullable = false)
    //@Temporal(TemporalType.DATE)
    private String Date;
    @Column(name = "Day_of_Week  ", nullable = false)
    private Integer dayOfWeek;
    @Column(name = "Time", nullable = false)
    private String Time;
    @Column(name = "Local_Authority_District", nullable = false)
    private Integer localAuthorityDistrict;
    @Column(name = "Light_Conditions", nullable = false)
    private Integer lightCondition;

    @JoinColumn(name = "Weather_Conditions")
    @OneToOne
    private WeatherCondition weatherCondition;

    @JoinColumn(name = "Road_Surface_Conditions")
    @OneToOne
    private RoadSurfaceCondition roadSurfaceCondition;

    public String getAccidentIndex() {
        return accidentIndex;
    }

    public void setAccidentIndex(String accidentIndex) {
        this.accidentIndex = accidentIndex;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Integer getPoliceForce() {
        return policeForce;
    }

    public void setPoliceForce(Integer policeForce) {
        this.policeForce = policeForce;
    }

    public Integer getAccidentSeverity() {
        return accidentSeverity;
    }

    public void setAccidentSeverity(Integer accidentSeverity) {
        this.accidentSeverity = accidentSeverity;
    }

    public Integer getNumberOfVehicles() {
        return numberOfVehicles;
    }

    public void setNumberOfVehicles(Integer numberOfVehicles) {
        this.numberOfVehicles = numberOfVehicles;
    }

    public Integer getNumberOfCasualties() {
        return numberOfCasualties;
    }

    public void setNumberOfCasualties(Integer numberOfCasualties) {
        this.numberOfCasualties = numberOfCasualties;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public Integer getLocalAuthorityDistrict() {
        return localAuthorityDistrict;
    }

    public void setLocalAuthorityDistrict(Integer localAuthorityDistrict) {
        this.localAuthorityDistrict = localAuthorityDistrict;
    }

    public Integer getLightCondition() {
        return lightCondition;
    }

    public void setLightCondition(Integer lightCondition) {
        this.lightCondition = lightCondition;
    }

    public WeatherCondition getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(WeatherCondition weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public RoadSurfaceCondition getRoadSurfaceCondition() {
        return roadSurfaceCondition;
    }

    public void setRoadSurfaceCondition(RoadSurfaceCondition roadSurfaceCondition) {
        this.roadSurfaceCondition = roadSurfaceCondition;
    }
}
