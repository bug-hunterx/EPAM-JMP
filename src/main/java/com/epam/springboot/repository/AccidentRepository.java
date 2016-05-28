package com.epam.springboot.repository;

import com.epam.data.RoadAccident;
import com.epam.springboot.modal.Accidents;
import com.epam.springboot.modal.RoadConditions;
import com.epam.springboot.modal.WeatherConditions;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Created by bill on 16-5-22.
 */
@Repository
public interface AccidentRepository extends JpaRepository<Accidents, String> {

    List<Accidents> findByRoadSurfaceConditions(RoadConditions roadCondition);

    Integer countByRoadSurfaceConditions(RoadConditions roadCondition);

    List<Accidents> findByDate(Date date);
    List<Accidents> findByDateBetween(Date date1, Date date2);

    List<Accidents>  findByRoadSurfaceConditionsAndDateBetween(RoadConditions roadCondition, Date date1, Date date2);
    Integer countByWeatherConditionsAndDateBetween(WeatherConditions weatherConditions, Date date1, Date date2);

    //(clearAutomatically = true)
    @Modifying
    @Query("UPDATE Accidents r SET r.time = :timeOfDay WHERE r.id = :accidentId") // Use java entity name
    int updateTime(@Param("accidentId") String id, @Param("timeOfDay") String timeOfDay);

}
