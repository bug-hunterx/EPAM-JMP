package com.epam.dbservice;

import java.util.Date;
import java.util.List;

import com.epam.data.RoadAccident;
import com.epam.entities.Accident;

public interface AccidentService {
	
	// scenario 1
    Accident findOne(String accidentId);
    
    // scenario 2
    List<Accident> getAllAccidentsByRoadCondition(String roadCondition);
    
    // scenario 3
    List<Accident> getAllAccidentsByWeatherConditionAndYear(String weatherCondition,String year);
    
    // scenario 4
    List<Accident> getAllAccidentsByDate(Date date);

    Boolean update(Accident accident);

}
