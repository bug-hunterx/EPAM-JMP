package com.epam.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Accidents")
public class Accident {

    @Id
    @Column(name = "Accident_Index")
    private String accidentId;
    @Column
    private String longitude;
    @Column
    private String latitude;
    @Column
    private String date;
    @Column
    private String Time;
    @Column(name = "Weather_Conditions")
    private String weatherConditions;
    @Column(name = "Road_Surface_Conditions")
    private String roadSurfaceConditions;

    public String getAccidentId() {
        return accidentId;
    }

    public void setAccidentId(String accidentId) {
        this.accidentId = accidentId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
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
}
