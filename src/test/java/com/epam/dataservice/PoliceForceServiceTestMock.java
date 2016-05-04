package com.epam.dataservice;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PoliceForceServiceTestMock {

	@Mock
	PoliceForceService policeForceService;

	@Test
	public void testGetContactNo() {
		Mockito.when(policeForceService.getContactNo("North Yorkshire")).thenReturn("1234");
		Mockito.when(policeForceService.getContactNo("North Yorkshire1")).thenReturn("123");

		assertEquals("1234", policeForceService.getContactNo("North Yorkshire"));
		assertEquals("123", policeForceService.getContactNo("North Yorkshire1"));
		
		Mockito.verify(policeForceService, Mockito.atLeast(1)).getContactNo("North Yorkshire1");
		Mockito.verify(policeForceService, Mockito.atMost(1)).getContactNo("North Yorkshire1");
		Mockito.verify(policeForceService).getContactNo("North Yorkshire");
	}

}
