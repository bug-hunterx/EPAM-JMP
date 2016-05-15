package com.epam.dbrepositories;

@Repository
public interface AccidentRepository extends CrudRepository<Accident, Integer> {

	// declare your query methods for default and if you want to execute any custom queries use @Query annotation.
}
