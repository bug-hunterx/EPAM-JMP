package com.epam.dataservice;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Random;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.BeforeClass;
import org.junit.Test;

public class IntegrationTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		AccidentReaderProcessorWriterTest.main(null);
	}

	@Test
	public void testAccidentReaderProcessorWriter4Daytime() {
		try {
			Reader reader = new FileReader("src/main/resources/DaytimeAccidents.csv");
			CSVParser records = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
			int i = 0;
			for (CSVRecord record : records) {
				Integer hour = Integer.valueOf(record.get(3));
				assertTrue("record #" + i + " invalid", hour >= 6 && hour < 18);
				i++;
			}
			records.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAccidentReaderProcessorWriter4Nighttime() {
		try {
			Reader reader = new FileReader("src/main/resources/NighttimeAccidents.csv");
			CSVParser records = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
			int i = 0;
			for (CSVRecord record : records) {
				Integer hour = Integer.valueOf(record.get(3));
				assertTrue("record #" + i + " invalid", hour < 6 || hour >= 18);
				i++;
			}
			records.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
