package com.epam.processor;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

import com.epam.data.RoadAccident;
import com.epam.data.TimeOfDay;
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

	public List<Accident> getAllAccidentsByRoadCondition(String roadCondition) {
		return accidentRepository.findByRoadSurfaceCondition(roadCondition);
	}

	public List<Accident> getAllAccidentsByWeatherConditionAndYear(String weatherCondition, String year) {
		return accidentRepository.findByAccidentsByWeatherConditionAndYear(weatherCondition, year);
	}

	public List<Accident> getAllAccidentsByDate(Date date) {
        List<Accident> accidents = accidentRepository.findByAccidentsByDate(new SimpleDateFormat("yyyy-MM-dd").format(date));
        accidents.forEach(accident -> {
            update(accident);
        });
        return accidents;
	}

	public Boolean update(Accident accident) {
        String timeOfDay = calculateTimeOfDay(accident);
        accident.setTime(timeOfDay);
        accidentRepository.save(accident);
		return true;
	}

    private String calculateTimeOfDay(Accident accident){
        LocalTime time = LocalTime.parse(accident.getTime(), DateTimeFormatter.ofPattern("H:mm"));
        TimeOfDay timeOfDay = TimeOfDay.of(time.getHour());
        return timeOfDay.toString();
    }

}
