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
//    private static final long serialVersionUID = 1L;

    @Id
//    @GeneratedValue
    @Column(name="Accident_Index")
    private String id;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "Road_Surface_Conditions")  // Column name ins table Accidents
    private RoadConditions roadSurfaceConditions;

    @Column(name="Date")
    @Temporal(TemporalType.DATE)
    private java.util.Date date;
/*
    private double longitude = 0.0;
    private double latitude = 0.0;
    @Column(name="Police_Force")
    private Integer policeForce = 1;
    @Column(name="Severity")
    private Integer severity = 1;
    private int numberOfVehicles = 0;
    private int numberOfCasualties = 0;
    private LocalDate date;
    private Integer dayOfWeek;
    @Temporal(TemporalType.TIME)
    private java.util.Date time;
//    private LocalTime time;
    private Integer districtAuthority = 1;
    private Integer lightConditions = -1;
    private Integer weatherConditions = -1;
//    private RoadConditions roadConditions;
*/

    protected Accidents() {
    }

    public Accidents(String id, RoadConditions roadSurfaceConditions, Date date) {
        this.id = id;
        this.roadSurfaceConditions = roadSurfaceConditions;
        this.date = date;
    }

    public void setRoadSurfaceConditions(RoadConditions roadSurfaceConditions) {
        this.roadSurfaceConditions = roadSurfaceConditions;
    }

    public RoadConditions getRoadSurfaceConditions() {
        return roadSurfaceConditions;
    }
/*
    public Accidents(String id, double longitude, double latitude, Integer policeForce, Integer severity, int numberOfVehicles, int numberOfCasualties, String date, Integer dayOfWeek, String time, Integer districtAuthority, Integer lightConditions, Integer weatherConditions, Integer roadSurfaceConditions) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.policeForce = policeForce;
        this.severity = severity;
        this.numberOfVehicles = numberOfVehicles;
        this.numberOfCasualties = numberOfCasualties;
//        this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.dayOfWeek = dayOfWeek;
//        this.time = LocalTime.parse(time, DateTimeFormatter.ofPattern("H:mm"));
        this.districtAuthority = districtAuthority;
        this.lightConditions = lightConditions;
        this.weatherConditions = weatherConditions;
        this.roadSurfaceConditions = roadSurfaceConditions;
    }
*/

    public Accidents(String id) {
        this.id = id;
//        this.date = LocalDate.now();
//        this.time = LocalTime.now();
//        this.dayOfWeek = 1; //Todo this.date.getDayOfWeek();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public Integer getPoliceForce() {
//        return policeForce;
//    }
//
//    public void setPoliceForce(Integer policeForce) {
//        this.policeForce = policeForce;
//    }
//
//    public Integer getSeverity() {
//        return severity;
//    }
//
//    public void setSeverity(Integer severity) {
//        this.severity = severity;
//    }
//
//    public double getLongitude() {
//        return longitude;
//    }
//
//    public void setLongitude(double longitude) {
//        this.longitude = longitude;
//    }
//
//    public double getLatitude() {
//        return latitude;
//    }
//
//    public void setLatitude(double latitude) {
//        this.latitude = latitude;
//    }
//
//    public int getNumberOfVehicles() {
//        return numberOfVehicles;
//    }
//
//    public void setNumberOfVehicles(int numberOfVehicles) {
//        this.numberOfVehicles = numberOfVehicles;
//    }
//
//    public int getNumberOfCasualties() {
//        return numberOfCasualties;
//    }
//
//    public void setNumberOfCasualties(int numberOfCasualties) {
//        this.numberOfCasualties = numberOfCasualties;
//    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


/*
    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
*/

/*
    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
*/
/*

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
*/

    @Override
    public String toString() {
        return "Accidents{" +
                "id='" + id + '\'' +
//                ", longitude=" + longitude +
//                ", latitude=" + latitude +
//                ", policeForce=" + policeForce +
//                ", severity=" + severity +
//                ", numberOfVehicles=" + numberOfVehicles +
//                ", numberOfCasualties=" + numberOfCasualties +
                ", date=" + date +
//                ", dayOfWeek=" + dayOfWeek +
////                ", time=" + time +
//                ", districtAuthority=" + districtAuthority +
//                ", lightConditions=" + lightConditions +
//                ", weatherConditions=" + weatherConditions +
                ", roadSurfaceConditions=" + roadSurfaceConditions +
                '}' + "\n";
    }

    /*public static void main(String[] args) {
        Date date=new Date("2/05/2016");
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
    }*/
}
