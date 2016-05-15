package com.epam.dbservice;

import java.util.Date;

import com.epam.data.RoadAccident;
import com.epam.entities.Accident;

public interface AccidentService {

    Accident findOne(String accidentId);

    Iterable<RoadAccident> getAllAccidentsByRoadCondition(Integer label);

    Iterable<RoadAccident> getAllAccidentsByWeatherConditionAndYear(Integer weatherCondition, String year);

    Iterable<RoadAccident> getAllAccidentsByDate(Date date);

    Boolean update(RoadAccident roadAccident);

}
