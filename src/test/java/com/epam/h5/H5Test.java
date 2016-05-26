package com.epam.h5;

import java.sql.Connection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.epam.db.HsqlInit;
import com.epam.h5.entity.Accidents;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-config.xml")
public class H5Test {

	@Autowired
	DBOperator operator;
	
    HsqlInit hsqlInit;
    Connection connection;

    @Before
    public void init(){
        hsqlInit = new HsqlInit();
        connection = hsqlInit.initDatabase();
    }
    
    @After
    public void shutdown(){
        hsqlInit.stopDatabase();
    }
    
    
	@Test
	public void testFindOne(){
		String accidentId = "2010000";
		Accidents accidents = operator.findOne(accidentId);
		
		assertNotNull(accidents);
	}
	
}
