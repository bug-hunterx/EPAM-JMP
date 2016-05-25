package com.epam.dbservice;

import java.util.Date;
import com.epam.entities.Accident;

public interface AccidentService {
	
	// scenario 1
	Accident findOne(String accidentId);
    
    // scenario 2
    Iterable getAllAccidentsByRoadCondition(String RoadCondition);
    
    // scenario 3
    Iterable getAllAccidentsByWeatherConditionAndYear(String weatherCondition,String year);
    
    // scenario 4
    Iterable<Accident> getAllAccidentsByDate(Date date);

    Boolean update(Accident accident);
    
    // scenario 4
    Boolean updateDayTime(Iterable<Accident> accidents);

}
