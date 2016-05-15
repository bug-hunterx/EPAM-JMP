package com.epam.dal;

import com.epam.entities.RoadAccident;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Created by Alexey on 12.05.2016.
 */
@Repository
public class JpaRoadAccidentDao extends AbstractJpaDao<RoadAccident> {

    public List<RoadAccident> findByWeatherConditionsAndYear(String weatherConditions, Integer year) {
        TypedQuery<RoadAccident> query = entityManager
                .createQuery("select a from RoadAccident a " +
                        "where a.date between :minDate and :maxDate and a.weatherConditions.label=:label", RoadAccident.class)
                .setParameter("minDate", Date.from(LocalDate.of(year, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .setParameter("maxDate", Date.from(LocalDate.of(year, 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .setParameter("label", weatherConditions);

        return query.getResultList();
    }
}
