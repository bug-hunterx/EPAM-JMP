package com.epam.dbrepositories;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epam.entities.Accident;

@Repository
public interface AccidentRepository extends CrudRepository<Accident, String> {

	// declare your query methods for default and if you want to execute any custom queries use @Query annotation.
	@Query(value="SELECT a FROM Accidents a WHERE a.roadSurfaceCondtion = :roadSurfaceCondtion")
	Iterable<Accident> findByRoadCondition(@Param("roadSurfaceCondtion") String roadSurfaceCondtion);
	@Query(value="SELECT a FROM Accidents a WHERE a.weatherCondition = :weatherCondition AND to_char(date, 'YYYY') = :year")
	Iterable<Accident> findByWeatherConditionAndYear(@Param("weatherCondition") String weatherCondition,@Param("year") String year);
	@Query(value="SELECT a FROM Accidents a where a.date = :date",nativeQuery=true)
	Iterable<Accident> findByDate(@Param("date") Date date);
	
}
