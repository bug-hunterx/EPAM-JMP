package com.epam.dbservice;

import java.util.Date;

import com.epam.data.RoadAccident;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccidentService extends CrudRepository<RoadAccident, String> {
	
	// scenario 1
    RoadAccident findOne(String accidentId);
    
    // scenario 2
    Iterable<RoadAccident> getAllAccidentsByRoadSurfaceCondition(String roadSurfaceCondition);
    
    // scenario 3
    Iterable<RoadAccident> getAllAccidentsByWeatherConditionAndYear(String weatherCondition,int year);
    
 // scenario 4
    Iterable<RoadAccident> getAllAccidentsByDate(Date date);


}
