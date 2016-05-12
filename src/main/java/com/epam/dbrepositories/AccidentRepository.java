package com.epam.dbrepositories;

import com.epam.entities.Accident;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccidentRepository extends CrudRepository<Accident, Integer> {

	// declare your query methods for default and if you want to execute any custom queries use @Query annotation.
}
