package com.epam.springboot.repository;

import com.epam.springboot.modal.WeatherConditions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by bill on 16-5-28.
 */
@Repository
public interface WeatherConditionRepository extends JpaRepository<WeatherConditions, Integer> {
}
