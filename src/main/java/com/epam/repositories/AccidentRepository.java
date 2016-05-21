package com.epam.repositories;

import com.epam.entities.RoadAccident;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "repo_accidents", path = "repo_accidents")
public interface AccidentRepository extends CrudRepository<RoadAccident, String> {
    List<RoadAccident> findByRoadSurfaceConditions_Label(String label);

    List<RoadAccident> findByDate(Date date);
}
