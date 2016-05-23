package com.epam.h3;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public enum TimeOfDay {
	MORNING("MORNING"),
	AFTERNOON("AFTERNOON"),
	EVENING("EVENING"),
	NIGHT("NIGHT");
	
	private TimeOfDay(String name){
		this.name = name;
	}
	
	private static LocalTime MORNING_TIME_START = LocalTime.parse("6:00 AM", DateTimeFormatter.ofPattern("h:mm a"));
	private static LocalTime MORNING_TIME_END = LocalTime.parse("12:00 PM", DateTimeFormatter.ofPattern("h:mm a"));
	private static LocalTime AFTERNOON_TIME_END = LocalTime.parse("6:00 PM", DateTimeFormatter.ofPattern("h:mm a"));
	private static LocalTime EVENING_TIME_END = LocalTime.parse("11:59 PM", DateTimeFormatter.ofPattern("h:mm a"));
	
	private String name;

	public static TimeOfDay valueOfTime(LocalTime time){
		if(MORNING_TIME_START.compareTo(time) < 0 && MORNING_TIME_END.compareTo(time) >= 0){
			return TimeOfDay.MORNING;
		}else if(MORNING_TIME_END.compareTo(time) < 0 && AFTERNOON_TIME_END.compareTo(time) >= 0){
			return TimeOfDay.AFTERNOON;
		}else if(AFTERNOON_TIME_END.compareTo(time) < 0 && EVENING_TIME_END.compareTo(time) >= 0){
			return TimeOfDay.EVENING;
		}
		
		return TimeOfDay.NIGHT;
	}
	
	@Override
	public String toString(){
		return this.name;
	}
	
}
