package com.epam.dataservice;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class PoliceForceServiceTest {
private	static PoliceForceService testService; 
@BeforeClass
public static void init(){
	testService = new PoliceForceService();
}

@Test
public void testGetContactNo(){
	assertEquals("Get contact no with valid police name:","131638621",testService.getContactNo("Metropolitan Police"));
	
	assertEquals("Get contact no with valid police name:","1316386214",testService.getContactNo("South Yorkshire"));
	
	assertEquals("Get contact no with inValid police name:","13163862",testService.getContactNo("Steven Yu"));
	
}
}
