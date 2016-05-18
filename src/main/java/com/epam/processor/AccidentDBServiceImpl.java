package com.epam.processor;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hsqldb.lib.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import com.epam.data.AccidentsDataLoader;
import com.epam.dbrepositories.AccidentRepository;
import com.epam.dbservice.AccidentService;
import com.epam.entities.Accident;

public class AccidentDBServiceImpl implements AccidentService {

	@Autowired
	private AccidentRepository accidentRepository;

	private AccidentsDataLoader accidentDataLoader;

	public AccidentsDataLoader getLoader() {
		return accidentDataLoader;
	}

	public void setLoader(AccidentsDataLoader accidentDataLoader) {
		this.accidentDataLoader = accidentDataLoader;
	}

	public Accident findOne(String accidentId) {
		return accidentRepository.findOne(accidentId);
	}

	public Iterable getAllAccidentsByRoadCondition(int roadCondition) {
		return accidentRepository.findByRoadSurfaceConditions(roadCondition);
	}

	public Iterable getAllAccidentsByWeatherConditionAndYear(String weatherCondition, String year) {
		Map<Integer, String> weatherConditions = this.getLoader().getWeatherConditions();
		int weather = -1;
		for (Map.Entry<Integer, String> entry : weatherConditions.entrySet()) {
			if (weatherConditions.equals(entry.getValue())) {
				weather = entry.getKey();
				break;
			}
		}
		return accidentRepository.findByWeatherConditionsAndYear(weather, year);
	}

	public List<Accident> getAllAccidentsByDate(Date date) {
		return accidentRepository.findByDate(date);

	}

	public Accident update(Accident accident) {
		return accidentRepository.save(accident);
	}

	@Override
	public boolean update(Iterable<Accident> accidents) {
		Iterator iter = (Iterator) accidents.iterator();
		while (iter.hasNext()) {
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
		if (hour < 6)
			timeOfDay = "NIGHT";
		else if (hour < 12)
			timeOfDay = "MORNING";
		else if (hour < 18)
			timeOfDay = "AFTERNOON";
		else if (hour < 24)
			timeOfDay = "EVENING";
		else
			timeOfDay = "INVALIDTIME";

		return timeOfDay;
	}

}
