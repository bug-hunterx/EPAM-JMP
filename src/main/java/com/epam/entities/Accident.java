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
