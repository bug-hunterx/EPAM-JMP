package com.epam.dbservice;

import java.util.Date;

import com.epam.entities.Accidents;

public interface AccidentService {

	// scenario 1
	Accidents findOne(String accidentId);

	// scenario 2
	Iterable<Accidents> getAllAccidentsByRoadCondition(int roadCondition);

	// scenario 3
	Iterable<Accidents> getAllAccidentsByWeatherConditionAndYear(int weatherCondition, String year);

	// scenario 4
	Iterable<Accidents> getAllAccidentsByDate(Date date);

	Accidents update(Accidents roadAccident);

	// scenario 4
	void updateTime(Accidents accidents);
}
