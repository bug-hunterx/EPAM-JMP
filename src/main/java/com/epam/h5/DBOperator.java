package com.epam.h5;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.epam.dbservice.AccidentService;
import com.epam.h5.entity.Accidents;

public class DBOperator {
	
	@Autowired
	AccidentService service;
	
	// scenario 1
    public Accidents findOne(String accidentId){
    	return service.findOne(accidentId);
    }
    
    // scenario 2
    public Iterable<Accidents> getAllAccidentsByRoadSurfaceCondition(String roadSurfaceCondition){
    	return service.getAllAccidentsByRoadSurfaceCondition(roadSurfaceCondition);
    }
    
    // scenario 3
    public Iterable<Accidents> getAllAccidentsByWeatherConditionAndPoliceForce(String weatherCondition,int policeForce){
    	return service.getAllAccidentsByWeatherConditionAndPoliceForce(weatherCondition, policeForce);
    }
    
    // scenario 4
    public Iterable<Accidents> getAllAccidentsByDate(Date date){
    	return service.getAllAccidentsByDate(date);
    }

}
