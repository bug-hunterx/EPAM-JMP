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

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "Weather_Conditions")  // Column name in table Accidents
    private WeatherConditions weatherConditions;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "Road_Surface_Conditions")  // Column name in table Accidents
    private RoadConditions roadSurfaceConditions;

    @Column(name="Date")
    @Temporal(TemporalType.DATE)
    private java.util.Date date;

    @Column(name="Time")
//    @Temporal(TemporalType.TIME)
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

    public Accidents(String id, WeatherConditions weatherConditions, RoadConditions roadSurfaceConditions, Date date) {
        this.id = id;
        this.weatherConditions = weatherConditions;
        this.roadSurfaceConditions = roadSurfaceConditions;
        this.date = date;
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

    public Accidents(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public LocalTime getLocalTime() {
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
