package com.epam.springboot.repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Map;

/**
 * Created by bill on 16-5-28.
 */
public interface AccidentService {
    Map<String, Integer> getAccidentCountGroupByRoadCondition();
}
