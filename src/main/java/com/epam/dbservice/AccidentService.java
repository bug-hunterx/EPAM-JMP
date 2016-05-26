package com.epam.dbservice;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.epam.h5.entity.Accidents;

@Repository
public interface AccidentService extends CrudRepository<Accidents, String> {
	
	// scenario 1
    Accidents findOne(String accidentId);
    
    // scenario 2
    Iterable<Accidents> getAllAccidentsByRoadSurfaceCondition(String roadSurfaceCondition);
    
    // scenario 3
    Iterable<Accidents> getAllAccidentsByWeatherConditionAndPoliceForce(String weatherCondition,int policeForce);
    
    // scenario 4
    Iterable<Accidents> getAllAccidentsByDate(Date date);


}
