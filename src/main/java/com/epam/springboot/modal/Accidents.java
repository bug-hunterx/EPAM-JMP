package com.epam.springboot.modal;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Date;
import java.time.format.DateTimeFormatter;

/**
 * Created by bill on 16-5-22.
 */
@Entity
@Table(name="Accidents")
public class Accidents {
    @Id
//    @GeneratedValue
    private String id;

    private double longitude;
    private double latitude;
    @Column(name="Police_Force")
    private Integer policeForce;
    @Column(name="Severity")
    private Integer severity;
    private int numberOfVehicles;
    private int numberOfCasualties;
    private Date date;
    private Integer dayOfWeek;
    private LocalTime time;
    private Integer districtAuthority;
    private Integer lightConditions;
    private Integer weatherConditions;
    private Integer roadSurfaceConditions;

    protected Accidents() {
    }

    public Accidents(String id, double longitude, double latitude, Integer policeForce, Integer severity, int numberOfVehicles, int numberOfCasualties, String date, Integer dayOfWeek, String time, Integer districtAuthority, Integer lightConditions, Integer weatherConditions, Integer roadSurfaceConditions) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.policeForce = policeForce;
        this.severity = severity;
        this.numberOfVehicles = numberOfVehicles;
        this.numberOfCasualties = numberOfCasualties;
        this.date = Date.valueOf(date);
        this.dayOfWeek = dayOfWeek;
        this.time = LocalTime.parse(time, DateTimeFormatter.ofPattern("H:mm"));
        this.districtAuthority = districtAuthority;
        this.lightConditions = lightConditions;
        this.weatherConditions = weatherConditions;
        this.roadSurfaceConditions = roadSurfaceConditions;
    }

    public Accidents(String id, Integer policeForce, Integer severity) {
        this.id = id;
        this.policeForce = policeForce;
        this.severity = severity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
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

    public Integer getWeatherConditions() {
        return weatherConditions;
    }

    public void setWeatherConditions(Integer weatherConditions) {
        this.weatherConditions = weatherConditions;
    }

    public Integer getRoadSurfaceConditions() {
        return roadSurfaceConditions;
    }

    public void setRoadSurfaceConditions(Integer roadSurfaceConditions) {
        this.roadSurfaceConditions = roadSurfaceConditions;
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

    public static void main(String[] args) {
/*
        Date date=new Date("20/05/2016");
        System.out.println(date);
//        DateTimeFormatter.ofPattern("dd/MM/yyyy")
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String ss = null;
        try {
            ss = sdf.parse("20/05/2016").toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(ss);
*/
    }
}
