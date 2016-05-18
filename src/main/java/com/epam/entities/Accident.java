package com.epam.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="accidents")
public class Accident {

	@Id
	@GeneratedValue
	@Column(name="Accident_Index")
	private String accidentIndex;
	
	@Column(name="Longitude")
	private float longitude;
	
	@Column(name="Latitude")
	private float latitude;
	
	@Column(name="Accident_Severity")
	private int accidentSeverity;
	
	@Column(name="Number_of_Vehicles")
	private int numberOfVehicles;
	
	@Column(name="Number_of_Casualties")
	private int numberOfCasualties;
	
	@Column(name="Date")
	private Date date;
	
	@Column(name="Day_of_Week")
	private int dayOfWeek;
	
	@Column(name="Time")
	private String time;
	
	@Column(name="Local_Authority_District")
	private int localAuthorityDistrict;
	
	@Column(name="Light_Conditions")
	private int lightConditions;
	
	@Column(name="Weather_Conditions")
	private int weatherConditions;
	
	@Column(name="Road_Surface_Conditions")
	private int roadSurfaceConditions;
	
	@Column(name="Day_Time")
	private String dayTime;

	public String getAccidentIndex() {
		return accidentIndex;
	}

	public void setAccidentIndex(String accidentIndex) {
		this.accidentIndex = accidentIndex;
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

	public int getAccidentSeverity() {
		return accidentSeverity;
	}

	public void setAccidentSeverity(int accidentSeverity) {
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
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

	public int getLocalAuthorityDistrict() {
		return localAuthorityDistrict;
	}

	public void setLocalAuthorityDistrict(int localAuthorityDistrict) {
		this.localAuthorityDistrict = localAuthorityDistrict;
	}

	public int getLightConditions() {
		return lightConditions;
	}

	public void setLightConditions(int lightConditions) {
		this.lightConditions = lightConditions;
	}

	public int getWeatherConditions() {
		return weatherConditions;
	}

	public void setWeatherConditions(int weatherConditions) {
		this.weatherConditions = weatherConditions;
	}

	public int getRoadSurfaceConditions() {
		return roadSurfaceConditions;
	}

	public void setRoadSurfaceConditions(int roadSurfaceConditions) {
		this.roadSurfaceConditions = roadSurfaceConditions;
	}

	public String getDayTime() {
		return dayTime;
	}

	public void setDayTime(String dayTime) {
		this.dayTime = dayTime;
	}

	@Override
	public String toString() {
		return "Accident [accidentIndex=" + accidentIndex + ", longitude=" + longitude + ", latitude=" + latitude
				+ ", accidentSeverity=" + accidentSeverity + ", numberOfVehicles=" + numberOfVehicles
				+ ", numberOfCasualties=" + numberOfCasualties + ", date=" + date + ", dayOfWeek=" + dayOfWeek
				+ ", time=" + time + ", localAuthorityDistrict=" + localAuthorityDistrict + ", lightConditions="
				+ lightConditions + ", weatherConditions=" + weatherConditions + ", roadSurfaceConditions="
				+ roadSurfaceConditions + ", dayTime=" + dayTime + "]";
	}
}


