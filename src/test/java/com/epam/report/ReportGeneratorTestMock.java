package com.epam.report;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.epam.processor.DataProcessor;

@RunWith(MockitoJUnitRunner.class)
public class ReportGeneratorTestMock {
	
	@Mock
	private DataProcessor dataProcessor;
	@InjectMocks
	private DataReportGenerator reportGenerator;
	
	
	@Test
	public void generateReportIfDataAvailable() {
		reportGenerator.roadConditionReport("reportTest");
	}
	
	@Test
	public void generateReportIfDataAvailableMock() {
		Map<String, Long> data = Mockito.mock(Map.class);
		Mockito.when(dataProcessor.getCountByRoadSurfaceCondition()).thenReturn(data);
		reportGenerator.roadConditionReport("fileName");
		Mockito.verify(dataProcessor, Mockito.times(1)).getCountByRoadSurfaceCondition();
		Mockito.verify(dataProcessor, Mockito.never()).getCountByRoadSurfaceCondition7();
	}
	
	@Test
	public void generateReportIfDataAvailableBDDMock() {
		Map<String, Long> data = Mockito.mock(Map.class);
		//given
		BDDMockito.given(dataProcessor.getCountByRoadSurfaceCondition()).willReturn(data);
	    //when
		reportGenerator.roadConditionReport("report");
	    //then
		Mockito.verify(dataProcessor, Mockito.times(1)).getCountByRoadSurfaceCondition();
		Mockito.verify(dataProcessor, Mockito.never()).getCountByRoadSurfaceCondition7();
	}
}
