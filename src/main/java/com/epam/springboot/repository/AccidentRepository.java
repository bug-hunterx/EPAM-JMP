package com.epam.springboot.repository;

import com.epam.springboot.modal.Accidents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by bill on 16-5-22.
 */
@Repository
public interface AccidentRepository extends JpaRepository<Accidents, String> {
}
