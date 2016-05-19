package com.epam.mentoring.restapi.repository;

import com.epam.mentoring.restapi.modal.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by pengfrancis on 16/5/15.
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
