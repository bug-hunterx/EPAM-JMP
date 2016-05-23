package com.epam.h4;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

import com.epam.dataservice.PoliceForceService;
import com.epam.h3.ReportGenerator;
import com.epam.h3.TimeOfDay;

@RunWith(MockitoJUnitRunner.class)
public class Homework4Test {

	@Mock
	private PoliceForceService policeForceService;
	
	@Test
	public void getContactNoTest(){
		String policeForceName = "Cumbria";
		String contactNo = policeForceService.getContactNo(policeForceName);
		Mockito.verify(policeForceService, Mockito.times(1)).getContactNo(policeForceName); 
	}

	@Test
	public void getValueOfTime(){
		LocalTime morning = LocalTime.parse("10:00 AM", DateTimeFormatter.ofPattern("h:mm a"));
		LocalTime afternoon = LocalTime.parse("2:00 PM", DateTimeFormatter.ofPattern("h:mm a"));
		LocalTime evening = LocalTime.parse("8:00 PM", DateTimeFormatter.ofPattern("h:mm a"));
		LocalTime night = LocalTime.parse("3:00 AM", DateTimeFormatter.ofPattern("h:mm a"));
		
		assertEquals("MORNING",TimeOfDay.valueOfTime(morning).toString());  
		assertEquals("AFTERNOON",TimeOfDay.valueOfTime(afternoon).toString());
		assertEquals("EVENING",TimeOfDay.valueOfTime(evening).toString());
		assertEquals("NIGHT",TimeOfDay.valueOfTime(night).toString());
	}
	
	@Test
	public void reportGeneratorIntegrationTest(){
		List<String> pathList = new ArrayList<>();
		pathList.add("src/main/resources/DfTRoadSafety_Accidents_2009");
		ReportGenerator.fillData(pathList);
		ReportGenerator.generator();
	}
	
	
}
