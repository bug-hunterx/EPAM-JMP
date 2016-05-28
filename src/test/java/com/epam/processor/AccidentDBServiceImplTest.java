package com.epam.processor;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.epam.db.HsqlInit;
import com.epam.entities.Accident; 

public class AccidentDBServiceImplTest {

	HsqlInit hsqlInit;
	Connection connection;
	public AccidentDBServiceImpl accidentDBServiceImpl = null;

	@Before
	public void setUp() throws Exception {
		hsqlInit = new HsqlInit();
		connection = hsqlInit.initDatabase();
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/spring-config.xml");
		accidentDBServiceImpl = (AccidentDBServiceImpl) context.getBean("accidentDBServiceImpl");

	}

	@After
	public void tearDown() throws Exception {
	
	}

	@Test
	public void testFindOne() {
		Accident roadAccident = accidentDBServiceImpl.findOne("200901BS70002");
		assertThat(roadAccident.getAccidentIndex(), equalTo("200901BS70002"));
		assertThat(roadAccident.getLongitude(), equalTo(-0.199248f));
		assertThat(roadAccident.getAccidentSeverity(), is(2));
	}
	
	@Test
	public void testGetAllAccidentsByRoadCondition() {
		Iterable<?> accidentsByRoadCondition = accidentDBServiceImpl.getAllAccidentsByRoadCondition(2);
		
		Iterator<?> it = accidentsByRoadCondition.iterator();
		while(it.hasNext()) {
			Accident accident = (Accident) it.next();
			assertThat(accident.getRoadSurfaceConditions(), is(2));
			break;
		}
	}
	
	@Test
	public void testGetAllAccidentsByWeatherConditionAndYear() {
		Iterable<?> accidentsByRoadCondition = accidentDBServiceImpl.getAllAccidentsByWeatherConditionAndYear("Raining no high winds", "2009");
		Iterator<?> it = accidentsByRoadCondition.iterator();
		while(it.hasNext()) {
			Accident accident = (Accident) it.next();
			assertThat(accident.getWeatherConditions(), is(2));
			assertThat(accident.getDate().getYear(), is(2009));
			break;
		}
	}
	
	@Test
	public void testgetAllAccidentsByDate() {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.YEAR, 2009);
		date.set(Calendar.MONTH, 1);
		date.set(Calendar.DAY_OF_MONTH, 1);
		List<Accident> accidents = accidentDBServiceImpl.getAllAccidentsByDate(date.getTime());
		assertThat(accidents.size(), is(155));
	}
	
	@Test
	public void testUpdate() {
		Accident roadAccident = accidentDBServiceImpl.findOne("200901BS70002");
		assertThat(roadAccident.getAccidentSeverity(), is(2));
		
		roadAccident.setAccidentSeverity(3);
		roadAccident = accidentDBServiceImpl.update(roadAccident);
		assertThat(roadAccident.getAccidentIndex(), equalTo("200901BS70002"));
		assertThat(roadAccident.getAccidentSeverity(), is(3));
	}
	
	@Test
	public void testBatchUpdateTimeOfDay() {
		boolean updateResult = false;  
		List<Accident> accidentByDate = accidentDBServiceImpl.getAllAccidentsByDate(new Date(2009,01,04));  
		updateResult = accidentDBServiceImpl.update(accidentByDate);
		 
		assertThat(updateResult, is(true)); 
	}

}
