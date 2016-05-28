package com.epam.springboot.repository;

import com.epam.data.RoadAccident;
import com.epam.springboot.modal.Accidents;
import com.epam.springboot.modal.RoadConditions;
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

//    @Query(value="select * from Accidents r where r.Police_Force=?1",nativeQuery=true)
    List<Accidents> findByRoadSurfaceConditions(RoadConditions roadCondition);

    Integer countByRoadSurfaceConditions(RoadConditions roadCondition);

    List<Accidents> findByDate(Date date);
    List<Accidents> findByDateBetween(Date date1, Date date2);

    List<Accidents>  findByRoadSurfaceConditionsAndDateBetween(RoadConditions roadCondition, Date date1, Date date2);
    Integer countByRoadSurfaceConditionsAndDateBetween(RoadConditions roadCondition, Date date1, Date date2);
/*

    // scenario 4
    List<Accidents> getAllAccidentsByDate(Date date);

    Boolean update(Accidents roadAccident);
*/
}
