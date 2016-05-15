package com.epam.dbservice;

import java.util.Date;
import java.util.List;

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

	public List<RoadAccident> getAllAccidentsByWeatherConditionAndYear(
			String weatherCondition, Integer year) {
		return accidentDao.findByWeatherConditionsAndYear(weatherCondition, year);
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
