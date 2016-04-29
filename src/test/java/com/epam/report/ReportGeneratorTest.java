package com.epam.report;

import java.util.HashMap;
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

//import com.epam.data.AccidentsDataLoader;
//import com.epam.data.RoadAccident;
//import com.epam.processor.DataProcessor;

@RunWith(MockitoJUnitRunner.class)
public class ReportGeneratorTest {
	/*private static final String ACCIDENTS_CSV = "src/main/resources/DfTRoadSafety_Accidents_2009.csv";
	private static DataProcessor dataProcessor;
	private static DataReportGenerator reportGenerator;

    @BeforeClass
    public static void loadData(){
        AccidentsDataLoader accidentsDataLoader = new AccidentsDataLoader();

        List<RoadAccident> accidents = accidentsDataLoader.loadRoadAccidents(ACCIDENTS_CSV);
        dataProcessor = new DataProcessor(accidents);
        reportGenerator = new DataReportGenerator(dataProcessor);
    }
*/
	@Mock
	private DataProcessor dataProcessor;
	@InjectMocks
	private DataReportGenerator reportGenerator;
	
	@Test
	public void generateReportIfDataAvailableMock() {
		Map<String, Long> data = createData();
		Mockito.when(dataProcessor.getCountByRoadSurfaceCondition()).thenReturn(data );
		reportGenerator.roadConditionReport("mockReportV2");
		Mockito.verify(dataProcessor, Mockito.times(1)).getCountByRoadSurfaceCondition();
		Mockito.verify(dataProcessor, Mockito.never()).getCountByRoadSurfaceCondition7();
	}
	
	private Map<String, Long> createData() {
		HashMap data = new HashMap<>();
		data.put("key", 1000L);
		data.put("key2", 2000L);
		data.put("key3", 3000L);
		data.put("key4", 4000L);
		return data;
	}

	@Test
	public void generateReportIfDataAvailableBDDMock() {
		Map<String, Long> data = Mockito.mock(Map.class);
		
		//given
		BDDMockito.given(dataProcessor.getCountByRoadSurfaceCondition()).willReturn(data);
	    //when
		reportGenerator.roadConditionReport("BDDMockReport");
	    //then
		Mockito.verify(dataProcessor, Mockito.times(1)).getCountByRoadSurfaceCondition();
		Mockito.verify(dataProcessor, Mockito.never()).getCountByRoadSurfaceCondition7();
	}
}
