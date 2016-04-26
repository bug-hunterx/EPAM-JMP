package com.epam.dataservice.accident;

import java.io.FileReader;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.epam.data.RoadAccident;
import com.epam.dataservice.RoadAccidentParser;

public class AccidentReader implements Runnable {

	private BlockingQueue<RoadAccident> readerQueue;
	private String accidentFile;
	private RoadAccidentParser roadAccidentParser;

	public AccidentReader(BlockingQueue<RoadAccident> readerQueue, String accidentFile) {
		this.readerQueue = readerQueue;
		this.accidentFile = accidentFile;
		roadAccidentParser = new RoadAccidentParser();
	}

	@Override
	public void run() {
		try {
			System.out.println("start reading file " + this.accidentFile);
			CSVParser accidentFileParser = new CSVParser(new FileReader(accidentFile), CSVFormat.EXCEL.withHeader());
			Iterator<CSVRecord> accidentFileIterator = accidentFileParser.iterator();
			while (accidentFileIterator.hasNext()) {
				RoadAccident accident = roadAccidentParser.parseRecord(accidentFileIterator.next());
				if (accident != null)
					readerQueue.put(accident);
			}
			accidentFileParser.close();
			System.out.println("end reading file " + this.accidentFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}