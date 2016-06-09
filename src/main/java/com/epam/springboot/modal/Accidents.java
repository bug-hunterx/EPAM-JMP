package com.epam.springboot.modal;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by bill on 16-5-22.
 */
@Entity
@Table(name="Accidents")
public class Accidents {

    @Id
    @Column(name="Accident_Index")
    private String id;

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "Weather_Conditions")  // Column name in table Accidents
    private WeatherConditions weatherConditions;

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "Road_Surface_Conditions")  // Column name in table Accidents
    private RoadConditions roadSurfaceConditions;

    @Column(name="Date")
    @Temporal(TemporalType.DATE)
    private java.util.Date date;

    @Column(name="Time")
    private String time;

    private double longitude = 0.0;
    private double latitude = 0.0;
    @Column(name="Police_Force")
    private Integer policeForce = 1;
    @Column(name="Accident_Severity")
    private Integer severity = 1;
    @Column(name="Number_of_Vehicles")
    private Integer numberOfVehicles = 0;
    @Column(name="Number_of_Casualties")
    private Integer numberOfCasualties = 0;
    @Column(name="Day_of_Week")
    private Integer dayOfWeek;
    @Column(name="Local_Authority")
    private Integer districtAuthority = 1;
    @Column(name="Light_Conditions")
    private Integer lightConditions = -1;

    protected Accidents() {
    }

    public Accidents(String id) {
        this.id = id;
    }

    public Accidents(String id, WeatherConditions weatherConditions, RoadConditions roadSurfaceConditions, Date date) {
        this.id = id;
        this.weatherConditions = weatherConditions;
        this.roadSurfaceConditions = roadSurfaceConditions;
        this.date = date;
    }

    public Accidents(String id, WeatherConditions weatherConditions, RoadConditions roadSurfaceConditions, Date date, String time, double longitude, double latitude, Integer policeForce, Integer severity, Integer numberOfVehicles, Integer numberOfCasualties, Integer dayOfWeek, Integer districtAuthority, Integer lightConditions) {
        this.id = id;
        this.weatherConditions = weatherConditions;
        this.roadSurfaceConditions = roadSurfaceConditions;
        this.date = date;
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;
        this.policeForce = policeForce;
        this.severity = severity;
        this.numberOfVehicles = numberOfVehicles;
        this.numberOfCasualties = numberOfCasualties;
        this.dayOfWeek = dayOfWeek;
        this.districtAuthority = districtAuthority;
        this.lightConditions = lightConditions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setWeatherConditions(WeatherConditions weatherConditions) {
        this.weatherConditions = weatherConditions;
    }
    public WeatherConditions getWeatherConditions() {
        return weatherConditions;
    }

    public void setRoadSurfaceConditions(RoadConditions roadSurfaceConditions) {
        this.roadSurfaceConditions = roadSurfaceConditions;
    }

    public RoadConditions getRoadSurfaceConditions() {
        return roadSurfaceConditions;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Integer getPoliceForce() {
        return policeForce;
    }

    public void setPoliceForce(Integer policeForce) {
        this.policeForce = policeForce;
    }

    public Integer getSeverity() {
        return severity;
    }

    public void setSeverity(Integer severity) {
        this.severity = severity;
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

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getDistrictAuthority() {
        return districtAuthority;
    }

    public void setDistrictAuthority(Integer districtAuthority) {
        this.districtAuthority = districtAuthority;
    }

    public Integer getLightConditions() {
        return lightConditions;
    }

    public void setLightConditions(Integer lightConditions) {
        this.lightConditions = lightConditions;
    }

    @Transient
    // use getLocalTime will cause DBUnit fail
    public LocalTime convertLocalTime() {
        LocalTime localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("H:mm"));
        return localTime;
    }


    @Override
    public String toString() {
        return "Accidents{" +
                "id='" + id + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", policeForce=" + policeForce +
                ", severity=" + severity +
                ", numberOfVehicles=" + numberOfVehicles +
                ", numberOfCasualties=" + numberOfCasualties +
                ", date=" + date +
                ", dayOfWeek=" + dayOfWeek +
                ", time=" + time +
                ", districtAuthority=" + districtAuthority +
                ", lightConditions=" + lightConditions +
                ", weatherConditions=" + weatherConditions +
                ", roadSurfaceConditions=" + roadSurfaceConditions +
                '}' + "\n";
    }

}
