package com.epam.dbservice;

import java.util.Date;
import java.util.List;

import com.epam.entities.RoadAccident;

public interface AccidentService {
	
	// scenario 1
    RoadAccident findOne(String accidentId);
    
    // scenario 2
    Iterable<RoadAccident> getAllAccidentsByRoadCondition(String roadConditions);
    
    // scenario 3
    Iterable<RoadAccident> getAllAccidentsByWeatherConditionAndYear(String weatherCondition, Integer year);
    
 // scenario 4
    List<RoadAccident> getAllAccidentsByDate(Date date);

    Boolean update(RoadAccident roadAccident) throws Exception;

}
