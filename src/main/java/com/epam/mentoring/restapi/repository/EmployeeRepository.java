package com.epam.mentoring.restapi.repository;

import com.epam.mentoring.restapi.modal.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by pengfrancis on 16/5/19.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
