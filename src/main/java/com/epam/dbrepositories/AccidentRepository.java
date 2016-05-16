package com.epam.dbrepositories;

import java.util.Date;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epam.entities.Accident;

@Repository
public interface AccidentRepository extends CrudRepository<Accident, Integer> {
	
	String FIND_COUNT_BY_ROADE_SURFACE_CONDITIONS = "SELECT a.roadSurfaceConditions, COUNT(a) FROM Accident a GROUP BY a.roadSurfaceConditions"; 
	String FIND_COUNT_BY_WEATHER_CONDITIONS_AND_YEAR = "SELECT a.date, a.roadSurfaceConditions, COUNT(a) FROM Accident a WHERE a.weatherConditions= :weatherCondition AND a.date like '%:year%' GROUP BY a.roadSurfaceConditions";
//	String FIND_BY_DATE = "SELECT a FROM Accident WHERE a.date =:date";
	
	// declare your query methods for default and if you want to execute any custom queries use @Query annotation.
	@Query(value=FIND_COUNT_BY_ROADE_SURFACE_CONDITIONS)
	Iterable findCountByRoadSurfaceConditions();
	
	@Query(value=FIND_COUNT_BY_WEATHER_CONDITIONS_AND_YEAR)
	Iterable findCoundByWeatherConditionsAndYear(@Param("weatherCondition")String weatherCondition, @Param("year")String year);
	
	@Query(value="SELECT a.* FROM accidents a WHERE date = :date", nativeQuery=true)
	Iterable<Accident> findByDate(@Param("date") Date date);

}
