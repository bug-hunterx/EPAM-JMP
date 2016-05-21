package com.epam.dbrepositories;

import com.epam.entities.Accident;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public interface AccidentRepository extends CrudRepository<Accident, String> {

	// declare your query methods for default and if you want to execute any custom queries use @Query annotation.

    @Query(value = "SELECT * FROM Accidents a WHERE a.Road_Surface_Conditions=?1", nativeQuery = true)
    List<Accident> findByRoadSurfaceCondition(String roadSurfaceCondition);

    @Query(value = "SELECT * FROM Accidents a WHERE a.Weather_Conditions=?1 and year(a.date)=?2", nativeQuery = true)
    List<Accident> findByAccidentsByWeatherConditionAndYear(String weatherCondition, String year);

    @Query(value = "SELECT * FROM Accidents a WHERE a.date=?1", nativeQuery = true)
    List<Accident> findByAccidentsByDate(String date);

}
