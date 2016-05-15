package com.epam.integration.tests;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.epam.Main;


@Category(IntegrationTest.class)
public class AccidentsFileGeneratorIntegrationTest {	
	private static final String REPORT_FOLDER = "src/main/resources/";
	
	
	@Test
	public void testGenerateAccidentFile_ProducerThread_more_than_files() throws IOException, ExecutionException, InterruptedException{
		String[] params = new String[3]; 
		params[0]="src/main/resources/DfTRoadSafety_Accidents_2011.csv,src/main/resources/DfTRoadSafety_Accidents_2012.csv";
		//params[0]="src/main/resources/DfTRoadSafety_Accidents_2010.csv";
		params[1]="5";
		params[2]="8000";
		Main.main(params);
		File f = new File(REPORT_FOLDER+"DaytimeAccidents.csv");
		Assert.assertTrue(f.exists() && !f.isDirectory() && f.length()>0);
		f = new File(REPORT_FOLDER+"NighttimeAccidents.csv");
		Assert.assertTrue(f.exists() && !f.isDirectory() && f.length()>0);
		
	}
	
	@Test
	public void testGenerateAccidentFile_ProducerThread_less_than_files() throws IOException, ExecutionException, InterruptedException{
		String[] params = new String[4]; 
		params[0]="src/main/resources/DfTRoadSafety_Accidents_2011.csv,src/main/resources/DfTRoadSafety_Accidents_2012.csv";		
		params[1]="5";
		params[2]="8000";
		params[3]="1";
		File f1 = new File(REPORT_FOLDER+"DaytimeAccidents.csv");
		File f2 = new File(REPORT_FOLDER+"NighttimeAccidents.csv");
		if(f1!=null && !f1.isDirectory()){
			f1.delete();
		}
		if(f2!=null && !f2.isDirectory()){
			f2.delete();
		}
		
		Main.main(params);
		
		
		File f = new File(REPORT_FOLDER+"DaytimeAccidents.csv");
		Assert.assertTrue(f.exists() && !f.isDirectory() && f.length()>0);
		f = new File(REPORT_FOLDER+"NighttimeAccidents.csv");
		Assert.assertTrue(f.exists() && !f.isDirectory() && f.length()>0);
		
	}
}
