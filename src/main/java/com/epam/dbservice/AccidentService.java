package com.epam.dbservice;

import java.util.Date;

import com.epam.entities.Accident;

public interface AccidentService {
	
	// scenario 1
    Accident findOne(String accidentId);
    
    // scenario 2
    Iterable<Accident> getAllAccidentsByRoadCondition();
    
    // scenario 3
    Iterable<Accident> getAllAccidentsByWeatherConditionAndYear(String weatherCondition,String year);
    
 // scenario 4
    Iterable<Accident> getAllAccidentsByDate(Date date);

    Boolean update(Accident accident);
    
    Boolean update(Iterable<Accident> accidents);

}
