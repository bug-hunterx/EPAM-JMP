package com.epam.report;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Map;

import com.epam.data.RoadAccident;
import com.epam.processor.DataProcessor;

public class DataReportGenerator {

	private static final String REPORT_FOLDER = "C:/Olga/JMP/code/";
	private DataProcessor dataProcessor;
	
	public DataReportGenerator(DataProcessor dataProcessor) {
		this.dataProcessor = dataProcessor;
	}
	
	public int roadConditionReport(String fileName) {
		String index = "200901BS70021";
        RoadAccident data = dataProcessor.getAccidentByIndex7(index);
		//Map<String, Long> data = dataProcessor.getCountByRoadSurfaceCondition();
		generateReport(fileName, data.toString());
		
		return data.hashCode();
	}
	
	private void generateReport(String name, Object data) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(REPORT_FOLDER + name + ".txt", true)));
		    out.println(data);
		    out.close();
	         System.out.printf("Serialized data is saved in "+ REPORT_FOLDER + name + ".txt");
	      } catch(IOException i) {
	          i.printStackTrace();
	      }
	}
	
}
