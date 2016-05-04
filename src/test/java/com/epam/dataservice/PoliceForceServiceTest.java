package com.epam.dataservice;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class PoliceForceServiceTest {

	private static PoliceForceService policeForceService;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		policeForceService = new PoliceForceService();
	}
	
	@Test
	public void testGetContactNo() {
		assertEquals("1316386212", policeForceService.getContactNo("North Yorkshire"));
		assertEquals("1316386213", policeForceService.getContactNo("West Yorkshire"));
		assertEquals("1316386214", policeForceService.getContactNo("South Yorkshire"));
		assertEquals("1316386216", policeForceService.getContactNo("Humberside"));
		assertEquals("1316386217", policeForceService.getContactNo("Cleveland"));
		assertEquals("1316386220", policeForceService.getContactNo("West Midlands"));
		assertEquals("1316386221", policeForceService.getContactNo("Staffordshire"));
		assertEquals("1316386222", policeForceService.getContactNo("West Mercia"));
		assertEquals("1316386223", policeForceService.getContactNo("Warwickshire"));
		assertEquals("1316386230", policeForceService.getContactNo("Derbyshire"));
		assertEquals("1316386231", policeForceService.getContactNo("Nottinghamshire"));
		assertEquals("1316386232", policeForceService.getContactNo("Lincolnshire"));
		assertEquals("1316386233", policeForceService.getContactNo("Leicestershire"));
	}

}
