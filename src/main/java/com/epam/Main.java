package com.epam;

import java.io.IOException;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.epam.dbrepositories.AccidentRepository;
import com.epam.dbservice.AccidentService;
import com.epam.entities.Accidents;
import com.epam.processor.AccidentDBServiceImpl;

/**
 * Created by Tkachi on 2016/4/3.
 */
@SpringBootApplication
public class Main {

	private static Long oneHour = 60 * 60 * 1000L;

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication();
		ApplicationContext ctx = SpringApplication.run(Main.class, args);
		AccidentRepository accRepository = (AccidentRepository) ctx.getBean(AccidentRepository.class);
		AccidentService accService = (AccidentDBServiceImpl) ctx.getBean(AccidentDBServiceImpl.class);

		save(accService);
		
		//Scenario 1. Find all the accidents by ID(Note: We can use findOne method which will accept the Accident ID as PK).
		System.out.println("Scenario 1 output: find index 2:: " + accService.findOne("2"));
		//Scenario 2. Find all the accidents count groupby all roadsurface conditions .
		System.out.println("Scenario 2 output: find by road condition 3:: " + accService.getAllAccidentsByRoadCondition(3));
		//Scenario 3. Find all the accidents count groupby accident year and weather condition .( For eg: in year 2009 we need to know the number of accidents based on each weather condition).
		System.out.println("Scenario 3 output: find by weather condition 4 and year 1970:: " + accService.getAllAccidentsByWeatherConditionAndYear(4, "1970"));
		//Scenario 4. On a given date,  fetch all the accidents and update the Time based on the below rules
		accService.updateTime(accService.findOne("1"));
		System.out.println("Scenario 4 output: update time :: " + accService.findOne("1"));
	}

	private static void save(AccidentService accImpl) {
		accImpl.update(new Accidents("1", 1, 1, new Date(1 * oneHour)));
		accImpl.update(new Accidents("2", 2, 2, new Date(7 * oneHour)));
		accImpl.update(new Accidents("3", 3, 3, new Date(13 * oneHour)));
		accImpl.update(new Accidents("4", 4, 4, new Date(19 * oneHour)));
	}

}
