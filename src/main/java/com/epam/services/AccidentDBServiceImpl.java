package com.epam.services;

import java.util.Date;
import java.util.List;

import com.epam.dal.JpaRoadAccidentDao;
import com.epam.repositories.AccidentRepository;
import com.epam.entities.RoadAccident;
import com.epam.web.exception.BadRequestError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("accidents")
public class AccidentDBServiceImpl implements AccidentService {
	@Autowired
	private JpaRoadAccidentDao accidentDao;

	@Autowired
    private AccidentRepository accidentRepository;

    @RequestMapping(method = RequestMethod.POST)
    public void add(@RequestBody RoadAccident accident) {
        accidentRepository.save(accident);
    }

    @RequestMapping(method = RequestMethod.GET)
	public Iterable<RoadAccident> getList(@RequestParam(name = "page", defaultValue = "1")  Integer page,
                                      @RequestParam(name = "pageSize", defaultValue = "0") Integer pageSize) {
		return pageSize > 0 ?
                accidentRepository.findAll(new PageRequest(page, pageSize)).getContent() :
                accidentRepository.findAll();
	}

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

    @RequestMapping(value = "/on/{date}")
	public List<RoadAccident> getAllAccidentsByDate(
            @PathVariable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
		return accidentRepository.findByDate(date);
	}

    @RequestMapping(value = "/{accidentId}", method = RequestMethod.PUT)
	public Boolean update(@PathVariable String accidentId, @RequestBody RoadAccident roadAccident) throws Exception {
		if(!accidentId.equals(roadAccident.getAccidentId())) {
            throw new BadRequestError("Wrong id!");
        }

        try {
            accidentDao.update(roadAccident);
        } catch (Exception e) {
            throw new Exception("Couldn't update entity: " + e.toString());
        }

        return true;
	}

    @RequestMapping(value = "/{accidentId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String accidentId) {
        accidentRepository.delete(accidentId);
    }
	
	

}
