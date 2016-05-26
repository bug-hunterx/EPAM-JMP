package com.epam.h5.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epam.h5.entity.Accidents;

@Repository
public interface AccidentRepository extends JpaRepository<Accidents, String> {
	
	// scenario 1
//	@Query(value = "SELECT * FROM Accidents WHERE accidentId = :accidentId")
    Accidents findOne(@Param("accidentId") String accidentId);
    
    // scenario 2
//	@Query(value = "SELECT * FROM Accidents WHERE roadSurfaceCondition = :roadSurfaceCondition")
//    Iterable<Accidents> getAllAccidentsByRoadSurfaceCondition(String roadSurfaceCondition);
    
    // scenario 3
//	@Query(value = "SELECT * FROM Accidents WHERE weatherCondition = :weatherCondition and policeForce = :policeForce")
//    Iterable<Accidents> getAllAccidentsByWeatherConditionAndPoliceForce(String weatherCondition,int policeForce);
    
    // scenario 4
//	@Query(value = "SELECT * FROM Accidents WHERE date = :date")
//    Iterable<Accidents> getAllAccidentsByDate(Date date);


}
