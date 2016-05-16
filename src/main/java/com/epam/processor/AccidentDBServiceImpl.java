package com.epam.processor;

import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hsqldb.lib.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.epam.dbrepositories.AccidentRepository;
import com.epam.dbservice.AccidentService;
import com.epam.entities.Accident;

public class AccidentDBServiceImpl implements AccidentService {
	
	@Autowired
	private AccidentRepository accidentRepository;

	public Accident findOne(String accidentId) {
		Iterable<Accident> accidents = accidentRepository.findAll();
		Iterator iter = (Iterator) accidents.iterator();
		while(iter.hasNext()) {
			Accident accident = (Accident) iter.next();
			if(accidentId.equals(accident.getAccidentIndex())) {
				System.out.println("Find by accidentId=" + accident);
				return accident;
			}
		}
		return null;
	}

	public Iterable getAllAccidentsByRoadCondition() {
		return accidentRepository.findCountByRoadSurfaceConditions();
	}

	public Iterable getAllAccidentsByWeatherConditionAndYear(
			String weatherCondition, String year) {
		return accidentRepository.findCoundByWeatherConditionsAndYear(weatherCondition, year);
	}

	public Iterable<Accident> getAllAccidentsByDate(Date date) {
		return accidentRepository.findByDate(date);
		
	}

	public Boolean update(Accident accident) {
		accidentRepository.save(accident);
		return true;
	}
	
	@Override
	public Boolean update(Iterable<Accident> accidents) {
		Iterator iter = (Iterator) accidents.iterator();
		while(iter.hasNext()) {
			Accident accident = (Accident) iter.next();
			String timeOfDay = getTimeOfDay(accident.getTime());
			accident.setDayTime(timeOfDay);
		}
		
		accidentRepository.save(accidents);
		return true;
	}

	private String getTimeOfDay(String time) {
		String[] timeArr = time.split(":");
		int hour = Integer.valueOf(timeArr[0]);
		String timeOfDay = "";
		if(hour < 6)
			timeOfDay = "NIGHT";
		else if(hour < 12)
			timeOfDay = "MORNING";
		else if(hour < 18)
			timeOfDay = "AFTERNOON";
		else if(hour < 24)
			timeOfDay = "EVENING";
		else
			timeOfDay = "INVALIDTIME";
		
		return timeOfDay;
	}

	public static void main(String[] args) {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("spring/spring-config.xml");
		ctx.refresh();
		AccidentDBServiceImpl accidentDBServiceImpl = (AccidentDBServiceImpl) ctx.getBean("accidentDBServiceImpl");
		accidentDBServiceImpl.findOne("200901BS70002");
	}

}
