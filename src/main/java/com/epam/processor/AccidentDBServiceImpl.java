package com.epam.processor;

import java.time.LocalTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.epam.dbrepositories.AccidentRepository;
import com.epam.dbservice.AccidentService;
import com.epam.entities.Accident;

public class AccidentDBServiceImpl implements AccidentService {
	@Autowired
	private AccidentRepository accidentRepository;
	
	public static String DAY_TIME_TYPE_MORNING = "MORNING";
	public static String DAY_TIME_TYPE_AFTERNOON = "AFTERNOON";
	public static String DAY_TIME_TYPE_EVENING = "EVENING";
	public static String DAY_TIME_TYPE_NIGHT = "NIGHT";
	
	public Accident findOne(String accidentId) {
		
		return accidentRepository.findOne(accidentId);
	}

	public Iterable<Accident> getAllAccidentsByRoadCondition(String RoadCondition) {		
		return accidentRepository.findByRoadCondition(RoadCondition);
	}

	public Iterable<Accident> getAllAccidentsByWeatherConditionAndYear(
			String weatherCondition, String year) {	
		return accidentRepository.findByWeatherConditionAndYear(weatherCondition,year);
	}

	public Iterable<Accident> getAllAccidentsByDate(Date date) {	
		return accidentRepository.findByDate(date);
	}

	public Boolean update(Accident accident) {
		accidentRepository.save(accident);
		return true;
	}
	
	public Boolean updateDayTime(Iterable<Accident> accidents) { 		
 		for (Accident accident : accidents){ 			
 			accident.setDayTimeType(getDayTimeType(accident.getTime())); 			
 		}
 		accidentRepository.save(accidents);
 		return true;
  	}
	
	private String getDayTimeType(LocalTime time){
		String dayTimeType = null;
			if (time != null) {
				int hour = time.getHour();
				if (hour >= 6 && hour < 12) {
					dayTimeType = DAY_TIME_TYPE_MORNING;
				} else if (hour >= 12 && hour < 18) {
					dayTimeType = DAY_TIME_TYPE_AFTERNOON;
				} else if (hour >= 18 && hour < 24) {
					dayTimeType = DAY_TIME_TYPE_EVENING;
				} else {
					dayTimeType = DAY_TIME_TYPE_NIGHT;
				}
			}
			return dayTimeType;
	}
	

}
