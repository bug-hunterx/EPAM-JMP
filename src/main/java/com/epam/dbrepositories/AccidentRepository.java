package com.epam.dbrepositories;

import com.epam.entities.RoadAccident;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AccidentRepository extends CrudRepository<RoadAccident, Integer> {
    List<RoadAccident> findByRoadSurfaceConditions_Label(String label);

    List<RoadAccident> findByDate(Date date);
}
