package com.epam.processor;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.epam.dbrepositories.AccidentRepository;
import com.epam.dbservice.AccidentService;
import com.epam.entities.Accidents;

public class AccidentDBServiceImpl implements AccidentService {

	@Autowired
	private AccidentRepository accidentRepository;

	public Accidents findOne(String accidentId) {
		return accidentRepository.findOne(accidentId);
	}

	public List<Accidents> getAllAccidentsByRoadCondition(int roadCondition) {
		return accidentRepository.findByRoadCondition(roadCondition);
	}

	public List<Accidents> getAllAccidentsByWeatherConditionAndYear(int weatherCondition, String year) {
		return accidentRepository.findByWeatherConditionsAndYear(weatherCondition, year);
	}

	public List<Accidents> getAllAccidentsByDate(Date date) {
		return accidentRepository.findByDate(date);
	}

	public Accidents update(Accidents roadAccident) {
		return accidentRepository.save(roadAccident);
	}

	public static String TIME_MORNING = "MORNING";
	public static String TIME_AFTERNOON = "AFTERNOON";
	public static String TIME_EVENING = "EVENING";
	public static String TIME_NIGHT = "NIGHT";

	public void updateTime(Iterable<Accidents> accidents) {
		Calendar calendar=Calendar.getInstance();
		for (Accidents accident : accidents){
			calendar.setTime(accident.getDate());
			if (calendar.get(Calendar.HOUR_OF_DAY) >= 18) {
				accident.setTime(TIME_NIGHT);
			} else if (calendar.get(Calendar.HOUR_OF_DAY) >= 12) {
				accident.setTime(TIME_AFTERNOON);
			} else if (calendar.get(Calendar.HOUR_OF_DAY) >= 6) {
				accident.setTime(TIME_MORNING);
			} else {
				accident.setTime(TIME_EVENING);
			}
		}
	}

}
