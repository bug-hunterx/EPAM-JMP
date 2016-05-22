package com.epam.springboot.repository;

import com.epam.data.RoadAccident;
import com.epam.springboot.modal.Accidents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by bill on 16-5-22.
 */
@Repository
public interface AccidentRepository extends JpaRepository<Accidents, String> {
    // scenario 1
    Accidents findOne(String accidentId);

    // scenario 2
    @Query(value="select * from Accidents r where r.Police_Force=?1",nativeQuery=true)
    public List<Accidents> getAllAccidentsByRoadCondition(Integer RoadCondition);
}
