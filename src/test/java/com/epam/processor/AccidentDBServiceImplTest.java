package com.epam.processor;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.epam.entities.Accident;
import com.epam.db.HsqlInit;

public class AccidentDBServiceImplTest {
	private  AccidentDBServiceImpl accidentDBService = null;
	private HsqlInit hsqlInit;
	private Connection connection;
	
	
	@Before	
	public void setUp(){
//		hsqlInit = new HsqlInit();
//		connection = hsqlInit.initDatabase();
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/spring-config.xml");
		accidentDBService = (AccidentDBServiceImpl) context.getBean("accidentDBServiceImpl");
	}
	
	@Test
	public void testFindOne(){
		Accident accident = accidentDBService.findOne("200901BS70001");
		assertEquals("accident id is:","200901BS70001",accident.getAccidentId());
	}
}
