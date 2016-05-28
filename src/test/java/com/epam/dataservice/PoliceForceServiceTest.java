package com.epam.dataservice;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class PoliceForceServiceTest {
	
	private PoliceForceService policeForceService;
	
	@Before
	public void setUp() {
		policeForceService = new PoliceForceService();
	}

	@Test
	public void testInvalidForceNameThenReturnPhonePrefixOnly() {
		String forceName = "NightOneOne";
		String phonePrefix = "13163862";
		
		assertThat(policeForceService.getContactNo(forceName), equalTo(phonePrefix));
	}
	
	@Test
	public void testNullForceNameThenReturnPhonePrefixOnly() {
		String forceName = "NightOneOne";
		String phonePrefix = "13163862";
		
		assertThat(policeForceService.getContactNo(forceName), equalTo(phonePrefix));
	}
	
	@Test
	public void testValidForceNameThenReturnContactNoWithPhonePrefix() {
		String forceName = "Bedfordshire";
		String phonePrefix = "13163862";
		String contactNo = "40";
		
		assertThat(policeForceService.getContactNo(forceName), equalTo(phonePrefix + contactNo));
	}

}
