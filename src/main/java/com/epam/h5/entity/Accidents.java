package com.epam.h5.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "accidents")
public class Accidents {
	
    @Id
    @Column(name = "Accident_Index")
    private String accidentId;
    
    @Column(name = "Longitude")
    private float longitude;
    
    @Column(name = "Latitude")
    private float latitude;
    
    @Column(name = "Police_Force")
    private String policeForce;
    
    @Column(name = "Accident_Severity")
    private String accidentSeverity;
    
    @Column(name = "Number_of_Vehicles")
    private int numberOfVehicles;
    
    @Column(name = "Number_of_Casualties")
    private int numberOfCasualties;
    
    @Column(name = "Date")
    private LocalDate date;
    
    @Column(name = "Day_of_Week")
    private int dayOfWeek;
    
    @Column(name = "Time")
    private String time;
    
    @Column(name = "Local_Authority_District")
    private int districtAuthority;
    
    @Column(name = "Light_Conditions")
    private int lightCondition;
    
    @Column(name = "Weather_Conditions")
    private int weatherCondition;
    
    @Column(name = "Road_Surface_Conditions")
    private int roadSurfaceCondition;

    public Accidents() {}

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

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getDistrictAuthority() {
		return districtAuthority;
	}

	public void setDistrictAuthority(int districtAuthority) {
		this.districtAuthority = districtAuthority;
	}

	public int getLightCondition() {
		return lightCondition;
	}

	public void setLightCondition(int lightCondition) {
		this.lightCondition = lightCondition;
	}

	public int getWeatherCondition() {
		return weatherCondition;
	}

	public void setWeatherCondition(int weatherCondition) {
		this.weatherCondition = weatherCondition;
	}

	public int getRoadSurfaceCondition() {
		return roadSurfaceCondition;
	}

	public void setRoadSurfaceCondition(int roadSurfaceCondition) {
		this.roadSurfaceCondition = roadSurfaceCondition;
	}

	@Override
	public String toString() {
		return "Accidents [accidentId=" + accidentId + ", longitude="
				+ longitude + ", latitude=" + latitude + ", policeForce="
				+ policeForce + ", accidentSeverity=" + accidentSeverity
				+ ", numberOfVehicles=" + numberOfVehicles
				+ ", numberOfCasualties=" + numberOfCasualties + ", date="
				+ date + ", dayOfWeek=" + dayOfWeek + ", time=" + time
				+ ", districtAuthority=" + districtAuthority
				+ ", lightCondition=" + lightCondition + ", weatherCondition="
				+ weatherCondition + ", roadSurfaceCondition="
				+ roadSurfaceCondition + "]";
	}
    
}
