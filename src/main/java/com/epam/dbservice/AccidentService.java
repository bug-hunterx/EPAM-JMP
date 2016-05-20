package com.epam.dbservice;

import java.util.Date;

import com.epam.data.RoadAccident;
import com.epam.entities.Accident;

public interface AccidentService {
	
	// scenario 1
    Accident findOne(String accidentId);
    
    // scenario 2
    Iterable<RoadAccident> getAllAccidentsByRoadCondition();
    
    // scenario 3
    Iterable<RoadAccident> getAllAccidentsByWeatherConditionAndYear(String weatherCondition,String year);
    
 // scenario 4
    Iterable<RoadAccident> getAllAccidentsByDate(Date date);

    Boolean update(RoadAccident roadAccident);

}
