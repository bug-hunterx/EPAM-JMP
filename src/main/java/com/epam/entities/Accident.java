package com.epam.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Accidents")
public class Accident {
	@Id
	@GeneratedValue
	@Column(name="accidentId")
	private String accidentId;
    
	@Column(name="longitude")
	private float longitude;
	
	@Column(name="latitude")
    private float latitude;
	
	@Column(name="policeForce")
    private String policeForce;
    
	@Column(name="accidentSeverity")
	private String accidentSeverity;
    
	@Column(name="numberOfVehicles")
	private int numberOfVehicles;
    
	@Column(name="numberOfCasualties")
	private int numberOfCasualties;
    
	@Column(name="date")
	private LocalDate date;
    
	@Column(name="time")
	private LocalTime time;
    
	@Column(name="districtAuthority")
	private String districtAuthority;
	
	@Column(name="lightCondition")
    private String lightCondition;
    
	@Column(name="weatherCondition")
    private String weatherCondition;
    
	@Column(name="roadSurfaceCondition")
    private String roadSurfaceCondition;	
	
	@Column(name="dayTimeType")
	private String dayTimeType;
		
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

	public String getLightCondition() {
		return lightCondition;
	}

	public void setLightCondition(String lightCondition) {
		this.lightCondition = lightCondition;
	}

	public String getWeatherCondition() {
		return weatherCondition;
	}

	public void setWeatherCondition(String weatherCondition) {
		this.weatherCondition = weatherCondition;
	}

	public String getRoadSurfaceCondition() {
		return roadSurfaceCondition;
	}

	public void setRoadSurfaceCondition(String roadSurfaceCondition) {
		this.roadSurfaceCondition = roadSurfaceCondition;
	}

	public String getDayTimeType() {
		return dayTimeType;
	}

	public void setDayTimeType(String dayTimeType) {
		this.dayTimeType = dayTimeType;
	}
	
	@Override
	public String toString() {
	return "Accident [accidentIndex=" + accidentId + ", longitude=" + longitude + ", latitude=" + latitude
	 				+ ", accidentSeverity=" + accidentSeverity + ", numberOfVehicles=" + numberOfVehicles
	 				+ ", numberOfCasualties=" + numberOfCasualties + ", date=" + date 
	 				//+ ", dayOfWeek=" + dayOfWeek
	 				+ ", time=" + time 
	 				//+", localAuthorityDistrict=" + localAuthorityDistrict 
	 				+ ", lightCondition="
	 				+ lightCondition + ", weatherCondition=" + weatherCondition + ", roadSurfaceCondition="
	 				+ roadSurfaceCondition + ", dayTimeType=" + dayTimeType + "]";

	}
}
