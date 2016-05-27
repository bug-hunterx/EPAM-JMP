package com.epam.springboot.repository;

import com.epam.springboot.modal.RoadConditions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Bill on 2016/5/26.
 */
@Repository
public interface RoadConditionRepository extends JpaRepository<RoadConditions, String> {
}
