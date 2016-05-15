package com.epam.dbrepositories;

import com.epam.data.RoadAccident;
import com.epam.entities.Accident;;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public interface AccidentRepository extends CrudRepository<Accident, String> {

    /*Find RoadAccident for the given Id*/
    Accident findOne(String accidentId);

    /*Find RoadAccident for the Given Road Surface Condition*/
    @Query(value = "SELECT acdnt.accidentIndex,acdnt.roadSurfaceCondition FROM Accident acdnt" +
            " WHERE acdnt.roadSurfaceCondition.code=:road_surface")
    Vector<Object> findByRoadSurfaceCondition(@Param("road_surface") Integer road_surface);

    /*Find RoadAccident for the Given Weather Condition*/
    @Query(value = "SELECT acdnt.accidentIndex,acdnt.weatherCondition FROM Accident acdnt " +
            "WHERE acdnt.weatherCondition.code=:weather_Condition and acdnt.Date LIKE CONCAT('%',:date,'%')")
    Vector<Object> findAccidentsByWeatherConditionAndYear(@Param("weather_Condition") Integer weatherCondition,
                                                          @Param("date") String date);

    /*Find RoadAccident for the Given Date*/
    @Query(value = "SELECT acdnt.accidentIndex,acdnt.Time FROM Accident acdnt WHERE acdnt.Date =:date")
    Vector<String> findAccidentsByDate(@Param("date") String date);

    /*Update RoadAccident for the Given accidentId*/
    @Transactional
    @Modifying
    @Query(value = "update Accident acdnt set acdnt.Time=:time " +
            "WHERE acdnt.accidentIndex=:accidentId")
    Integer updateTime(@Param("time") String time, @Param("accidentId") String accidentId);

}
