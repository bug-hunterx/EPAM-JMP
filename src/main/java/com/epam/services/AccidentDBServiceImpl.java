package com.epam.services;

import java.util.Date;
import java.util.List;

import com.epam.dal.JpaRoadAccidentDao;
import com.epam.repositories.AccidentRepository;
import com.epam.entities.RoadAccident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("accidents")
public class AccidentDBServiceImpl implements AccidentService {
	@Autowired
	private JpaRoadAccidentDao accidentDao;

	@Autowired
    private AccidentRepository accidentRepository;

	@RequestMapping(value = "/{accidentId}", method = RequestMethod.GET)
	public RoadAccident findOne(@PathVariable String accidentId) {
		return accidentDao.findOne(accidentId);
	}

	public List<RoadAccident> getAllAccidentsByRoadCondition(String roadConditions) {
		return accidentRepository.findByRoadSurfaceConditions_Label(roadConditions);
	}

	public List<RoadAccident> getAllAccidentsByWeatherConditionAndYear(
			String weatherCondition, Integer year) {
		return accidentDao.findByWeatherConditionsAndYear(weatherCondition, year);
	}

	public List<RoadAccident> getAllAccidentsByDate(Date date) {
		return accidentRepository.findByDate(date);
	}

	public Boolean update(RoadAccident roadAccident) throws Exception {
		try {
            accidentDao.update(roadAccident);
        } catch (Exception e) {
            throw new Exception("Couldn't update entity: " + e.toString());
        }

        return true;
	}
	
	

}
