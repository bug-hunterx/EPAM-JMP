package com.epam.h5;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.epam.h5.dao.AccidentRepository;
import com.epam.h5.entity.Accidents;

@Component
public class DBOperator {
	
	@Resource
	AccidentRepository dao;
	
    public Accidents findOne(String accidentId){
    	return dao.findOne(accidentId);
    }
    
//    public Iterable<Accidents> getAllAccidentsByRoadSurfaceCondition(String roadSurfaceCondition){
//    	return dao.getAllAccidentsByRoadSurfaceCondition(roadSurfaceCondition);
//    }
//    
//    public Iterable<Accidents> getAllAccidentsByWeatherConditionAndPoliceForce(String weatherCondition,int policeForce){
//    	return dao.getAllAccidentsByWeatherConditionAndPoliceForce(weatherCondition, policeForce);
//    }
//    
//    public Iterable<Accidents> getAllAccidentsByDate(Date date){
//    	return dao.getAllAccidentsByDate(date);
//    }

}
