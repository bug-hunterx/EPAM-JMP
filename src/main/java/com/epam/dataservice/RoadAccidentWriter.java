package com.epam.dataservice;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.epam.data.RoadAccident;

public class RoadAccidentWriter implements Runnable {

	private String accidentFile;
	
	private BlockingQueue<RoadAccident> accidentWrierQueue;
	
	public RoadAccidentWriter(String accidentFile, BlockingQueue<RoadAccident> accidentReaderQueue) {
		this.accidentFile = accidentFile;
		this.accidentWrierQueue = accidentReaderQueue;
	}

	@Override
	public void run() {
		CSVPrinter csvPrinter = null;
		try {
			FileWriter accidentFileWriter = new FileWriter(this.accidentFile);
			csvPrinter = new CSVPrinter(accidentFileWriter, CSVFormat.DEFAULT.withRecordSeparator("\n"));
			csvPrinter.printRecord("Accident_Index", "Longitude", "Latitude", "DayTime");
			while (true) {
				RoadAccident roadAccident = accidentWrierQueue.take();
				csvPrinter.printRecord(roadAccident.getAccidentId(), roadAccident.getLongitude(), roadAccident.getLatitude(), roadAccident.getDayTime());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				csvPrinter.flush();
				csvPrinter.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	public void setAccidentFile(String accidentFile) {
		this.accidentFile = accidentFile;
	}

	public void setAccidentReaderQueue(BlockingQueue<RoadAccident> accidentReaderQueue) {
		this.accidentWrierQueue = accidentReaderQueue;
	}

}
