package com.epam.dataservice.accident;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.epam.data.RoadAccident;

public class AccidentWriter implements Runnable {

	private BlockingQueue<RoadAccident> accidentQueue;
	private String accidentFile;

	public AccidentWriter(BlockingQueue<RoadAccident> accidentQueue, String accidentFile) {
		this.accidentQueue = accidentQueue;
		this.accidentFile = accidentFile;
	}

	@Override
	public void run() {
		CSVPrinter csvPrinter = null;
		try {
			FileWriter accidentFileWriter = new FileWriter(this.accidentFile);
			csvPrinter = new CSVPrinter(accidentFileWriter, CSVFormat.DEFAULT.withRecordSeparator("\n"));
			csvPrinter.printRecord("Accident_Index", "Longitude", "Latitude", "Accident_Hour");
			while (true) {
				RoadAccident accident = accidentQueue.take();
				csvPrinter.printRecord(accident.getAccidentId(), accident.getLongitude(), accident.getLatitude(), accident.getTime().getHour());
			}
		} catch (InterruptedException e) {
			//will interrupt when queue is empty and no more data come in
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
}