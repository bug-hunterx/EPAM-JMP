package com.epam.mentoring.restapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epam.mentoring.restapi.modal.Employee;

/**
 * Created by pengfrancis on 16/5/19.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Query(value = "SELECT * FROM Employee WHERE name := name")
	List<Employee> findByName(@Param("name") String name);

}
