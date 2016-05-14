package com.epam.dal;

import com.epam.entities.Accident;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Alexey on 12.05.2016.
 */
@Component("accidentDao")
@Repository
public class JpaAccidentDao extends AbstractJpaDao<Accident> {
    public JpaAccidentDao() {
        super();
        setClazz(Accident.class);
    }
}
