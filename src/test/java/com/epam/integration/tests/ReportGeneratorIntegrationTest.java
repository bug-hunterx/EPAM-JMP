package com.epam.integration.tests;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.epam.data.AccidentsDataLoader;
import com.epam.data.RoadAccident;
import com.epam.processor.DataProcessor;
import com.epam.report.DataReportGenerator;

@Category(IntegrationTest.class)
public class ReportGeneratorIntegrationTest {
	private static DataReportGenerator generator;
	
	private static final String ACCIDENTS_CSV = "src/main/resources/DfTRoadSafety_Accidents_2009.csv";
	private static final String REPORT_FOLDER = "C:/Olga/JMP/code/";
	 
	@BeforeClass
	public static void init() {
		AccidentsDataLoader accidentsDataLoader = new AccidentsDataLoader();
        List<RoadAccident> accidents = accidentsDataLoader.loadRoadAccidents(ACCIDENTS_CSV);
        DataProcessor dataProcessor = new DataProcessor(accidents);
		generator = new DataReportGenerator(dataProcessor);
	}
	
	@Test
	public void testReportGenerator() {
		String fileName = "reportIT";
		generator.roadConditionReport(fileName);
		File f = new File(REPORT_FOLDER+fileName+".txt");
		Assert.assertTrue(f.exists() && !f.isDirectory());
	}

}
