package com.epam.dbservice;

import java.util.Date;

import com.epam.dal.JpaRoadAccidentDao;
import com.epam.entities.RoadAccident;
import org.springframework.beans.factory.annotation.Autowired;

public class AccidentDBServiceImpl implements AccidentService {
	@Autowired
	private JpaRoadAccidentDao accidentDao;

	public RoadAccident findOne(String accidentId) {
		return accidentDao.findOne(accidentId);
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
