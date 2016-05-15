package com.epam.dataservice;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class PoliceForceServiceTest {
	private static PoliceForceService testService;

	@BeforeClass
	public static void init() {
		testService = new PoliceForceService();
	}

	@Test
	public void testGetContactNo_valid_case1() {
		assertEquals("Get contact no with valid police name:", "131638621",
				testService.getContactNo("Metropolitan Police"));

	}

	@Test
	public void testGetContactNo_valid_case2() {
		assertEquals("Get contact no with valid police name:", "1316386214",
				testService.getContactNo("South Yorkshire"));
	}

	@Test
	public void testGetContactNo_Invalid() {
		assertEquals("Get contact no with inValid police name:", "13163862",
				testService.getContactNo("Steven Yu"));
	}

	@Test
	public void testGetContactNo_null() {
		assertEquals("Get contact no with null police name:", "13163862",
				testService.getContactNo(null));
	}

}
