package com.epam.processor;

import java.util.Date;

import com.epam.data.RoadAccident;
import com.epam.dbrepositories.AccidentRepository;
import com.epam.dbservice.AccidentService;
import com.epam.entities.Accident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AccidentDBServiceImpl implements AccidentService {

    @Autowired
    private AccidentRepository accidentRepository;

	public Accident findOne(String accidentId) {
		return accidentRepository.findOne(accidentId);
	}

	public Iterable getAllAccidentsByRoadCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterable getAllAccidentsByWeatherConditionAndYear(
			String weatherCondition, String year) {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterable<RoadAccident> getAllAccidentsByDate(Date date) {
		// TODO Auto-generated method stub
        return null;
	}

	public Boolean update(RoadAccident roadAccident) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
