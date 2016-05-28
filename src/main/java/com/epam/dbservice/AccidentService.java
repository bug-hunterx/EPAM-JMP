package com.epam.dbservice;

import java.util.Date;

import com.epam.entities.Accident;

public interface AccidentService {
	
	// scenario 1
    Accident findOne(String accidentId);
    
    // scenario 2
    Iterable<Accident> getAllAccidentsByRoadCondition(int roadCondition);
    
    // scenario 3
    Iterable<Accident> getAllAccidentsByWeatherConditionAndYear(String weatherCondition,String year);
    
 // scenario 4
    Iterable<Accident> getAllAccidentsByDate(Date date);

    Accident update(Accident accident);
    
    boolean update(Iterable<Accident> accidents);

}
