package com.epam.dataservice;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.epam.data.RoadAccident;

public class RoadAccidentReader implements Runnable {
	
	private String accidentFile;
	
	private BlockingQueue<RoadAccident> accidentReaderQueue;
	
	private RoadAccidentParser roadAccidentParser;

	public RoadAccidentReader(String accidentFile, BlockingQueue<RoadAccident> accidentReaderQueue) {
		this.accidentFile = accidentFile;
		this.accidentReaderQueue = accidentReaderQueue;
		this.roadAccidentParser = new RoadAccidentParser();
	}

	@Override
	public void run() {
		try {
			Reader reader = new FileReader(accidentFile);
			Iterable<CSVRecord> roadAccidentRecords = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
			for(CSVRecord record : roadAccidentRecords) {
				RoadAccident roadAccident = roadAccidentParser.parseRecord(record);
				accidentReaderQueue.add(roadAccident);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.printf("Loaded %d accidents information into blocking queue.\n", accidentReaderQueue.size());
	}
	
	public void setAccidentFile(String accidentFile) {
		this.accidentFile = accidentFile;
	}

	public void setAccidentReaderQueue(BlockingQueue<RoadAccident> accidentReaderQueue) {
		this.accidentReaderQueue = accidentReaderQueue;
	}

	public void setRoadAccidentParser(RoadAccidentParser roadAccidentParser) {
		this.roadAccidentParser = roadAccidentParser;
	}

}
