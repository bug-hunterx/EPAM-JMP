package com.epam.processor;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

import com.epam.data.TimeOfDay;
import com.epam.data.RoadAccident;
import com.epam.dbrepositories.AccidentRepository;
import com.epam.dbservice.AccidentService;
import com.epam.entities.Accident;
import com.epam.entities.RoadSurfaceCondition;
import com.epam.entities.WeatherCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AccidentDBServiceImpl implements AccidentService {

	@Autowired
	private AccidentRepository accidentRepository;
	List<RoadAccident> roadAccidentsList = new ArrayList<RoadAccident>();
	Boolean status = false;
	@Autowired
	RoadAccident roadAccident;
	@Autowired
	TimeOfDay timeDay ;


    public AccidentRepository getAccidentRepository() {
        return accidentRepository;
    }

    public void setAccidentRepository(AccidentRepository accidentRepository) {
        this.accidentRepository = accidentRepository;
    }

    /**
     * @param accidentId:Unique Accident Id of Accident
     * @return Accident Record for a particular accident Id
     */
    public Accident findOne(String accidentId) {
        Accident accident = getAccidentRepository().findOne(accidentId);
        return accident;
    }

    /**
     * @param label:Road Surface Condition Type
     * @return RoadAccident Records on Road Surface Condition Type
     */
    public Iterable<RoadAccident> getAllAccidentsByRoadCondition(Integer label) {
        List<RoadAccident> roadAccidentsList = new ArrayList<>();
        Vector<Object> accidentByRoadCondition = getAccidentRepository().findByRoadSurfaceCondition(label);
        return parseRoadSurafceIterableToList(accidentByRoadCondition);
    }

    private List<RoadAccident> parseRoadSurafceIterableToList(Vector<Object> roadAccidentIterable){
        Iterator iteratorRoadCondition = roadAccidentIterable.iterator();
        while (iteratorRoadCondition.hasNext()) {
            Object[] iterableRoadSurface = (Object[]) iteratorRoadCondition.next();
            String accidentId = String.valueOf(iterableRoadSurface[0]);
            RoadSurfaceCondition roadSurfaceIterable = (RoadSurfaceCondition) iterableRoadSurface[1];

            roadAccident.setAccidentId(accidentId);
            roadAccident.setRoadSurfaceConditions(roadSurfaceIterable.getLabel());
            roadAccidentsList.add(roadAccident);
        }
        return roadAccidentsList;
    }
    /**
     * @param weatherCondition:Type Of Weather Condition
     * @param year:Year             Of accident occured
     * @return RoadAccident Iterable on Weather Condition & Year
     */
    public Iterable<RoadAccident> getAllAccidentsByWeatherConditionAndYear(
            Integer weatherCondition, String year) {
        Vector<Object> accidentByWeatherCondition = getAccidentRepository().findAccidentsByWeatherConditionAndYear(
                weatherCondition, year);
        return parseWeatherIterableToList(accidentByWeatherCondition);
    }


    private List<RoadAccident> parseWeatherIterableToList(Vector<Object> roadAccidentIterable){
        Iterator iteratorWeatherCondition = roadAccidentIterable.iterator();
        while (iteratorWeatherCondition.hasNext()) {
            Object[] iterableWeatherCondition = (Object[]) iteratorWeatherCondition.next();
            String accidentId = String.valueOf(iterableWeatherCondition[0]);
            WeatherCondition weatherConditionIterable = (WeatherCondition) iterableWeatherCondition[1];

            roadAccident.setAccidentId(accidentId);
            roadAccident.setWeatherConditions(weatherConditionIterable.getLabel());
            roadAccidentsList.add(roadAccident);
        }
        return roadAccidentsList;
    }
    /**
     * Method to get Road Accident by Date
     *
     * @param date:Fetch Accident on the given date
     * @return: Iterables Of RoadAccident
     */
    public Iterable<RoadAccident> getAllAccidentsByDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Vector<String> accidentByRoadCondition = getAccidentRepository().findAccidentsByDate(dateFormat.format(date));


        Iterator iteratorAccidentByDate = accidentByRoadCondition.iterator();
        while (iteratorAccidentByDate.hasNext()) {
            Object[] iterableObjectAccident = (Object[]) iteratorAccidentByDate.next();
            String accidentId = (String) iterableObjectAccident[0];
            String time = (String) iterableObjectAccident[1];

            roadAccident.setAccidentId(accidentId);
            roadAccident.setTime(LocalTime.parse(time));
            roadAccidentsList.add(roadAccident);
        }
        return roadAccidentsList;
    }

    /**
     * Update Accident for the given Input
     *
     * @param roadAccident:RoadAccident instance containing Accident Id
     * @return Boolean "TRUE" if Accident Table is updated
     */
    public Boolean update(RoadAccident roadAccident) {
        String timeRoadAccident = timeDay.getTimeOfDay(roadAccident.getTime());
        Integer stat = getAccidentRepository().updateTime(roadAccident.getAccidentId(), timeRoadAccident);
        if (stat == 0) status = true;
        return status;
    }
}