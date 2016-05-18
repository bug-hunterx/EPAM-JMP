package com.epam.dbrepositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epam.entities.Accident;

@Repository
public interface AccidentRepository extends CrudRepository<Accident, String> {
	
	String FIND_COUNT_BY_ROADE_SURFACE_CONDITIONS = "SELECT a FROM Accident a WHERE a.roadSurfaceConditions := roadCondition"; 
	String FIND_COUNT_BY_WEATHER_CONDITIONS_AND_YEAR = "SELECT a FROM Accident a WHERE a.weatherConditions= :weatherCondition AND a.date like '%:year%'";
	
	// declare your query methods for default and if you want to execute any custom queries use @Query annotation.
	@Query(value=FIND_COUNT_BY_ROADE_SURFACE_CONDITIONS)
	Iterable findByRoadSurfaceConditions(@Param("roadCondition") int roadCondition);
	
	@Query(value=FIND_COUNT_BY_WEATHER_CONDITIONS_AND_YEAR)
	Iterable findByWeatherConditionsAndYear(@Param("weatherCondition")int weatherCondition, @Param("year")String year);
	
	@Query(value="SELECT a.* FROM accidents a WHERE date = :date", nativeQuery=true)
	List<Accident> findByDate(@Param("date") Date date);

}
