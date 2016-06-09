package com.epam.springboot.repository;

import com.epam.springboot.modal.Accidents;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Map;

/**
 * Created by bill on 16-5-28.
 */
public interface AccidentService {
    Map<String, Integer> getAccidentCountGroupByRoadCondition();
    Map<String, Integer> getAccidentCountGroupByWeatherConditionAndYear(String year);
    int updateAccidentTimeByDate(String strDate);
    Accidents createAccident(Accidents accidents);
}
