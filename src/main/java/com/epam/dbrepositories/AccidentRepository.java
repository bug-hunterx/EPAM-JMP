package com.epam.dbrepositories;

import com.epam.entities.Accident;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface AccidentRepository extends CrudRepository<Accident, String> {

	// declare your query methods for default and if you want to execute any custom queries use @Query annotation.


}
