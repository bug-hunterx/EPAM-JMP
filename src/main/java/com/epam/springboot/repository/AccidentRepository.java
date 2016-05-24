package com.epam.springboot.repository;

import com.epam.data.RoadAccident;
import com.epam.springboot.modal.Accidents;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Created by bill on 16-5-22.
 */
@Repository
public interface AccidentRepository extends JpaRepository<Accidents, String> {
    // scenario 1
    Accidents findOne(String accidentId);

    // scenario 2
//    @Query(value="select * from Accidents r where r.Police_Force=?1",nativeQuery=true)
    List<Accidents> findByRoadSurfaceConditions(Integer RoadCondition);

    // scenario 3
//    @Query("select * from Accidents r where r.weatherConditions=?1 and YEAR(date)"
//    List<Accidents> findByWeatherConditionsAndYear(String weatherConditions,String year);

    // scenario 4
    List<Accidents> findByDate(LocalDate date);
/*
    // scenario 3
    List<Accidents> getAllAccidentsByWeatherConditionAndYear(String weatherCondition,String year);

    // scenario 4
    List<Accidents> getAllAccidentsByDate(Date date);

    Boolean update(Accidents roadAccident);
*/
}
