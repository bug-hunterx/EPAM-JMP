package com.epam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.csv.CSVPrinter;

import com.epam.data.RoadAccident;
import com.epam.data.RoadAccident.DAYTIMETYPE;

public class DataCSVFileWriter implements Runnable {
	private BlockingQueue<List<RoadAccident>> dataQueue;
	private CSVPrinter csvPrinterDayTime;
	private CSVPrinter csvPrinterNightTime;

	public DataCSVFileWriter(BlockingQueue<List<RoadAccident>> dataQueue,
			CSVPrinter csvPrinterDayTime, CSVPrinter csvPrinterNightTime) {
		this.dataQueue = dataQueue;
		this.csvPrinterDayTime = csvPrinterDayTime;
		this.csvPrinterNightTime = csvPrinterNightTime;
	}

	// TODO impl the logic to write data into outputstream or use csv writer
	@Override
	public void run() {

		List<RoadAccident> consumedData = null;
		try {
			consumedData = dataQueue.take();

			while (consumedData != null && !consumedData.isEmpty()) {			
				
				for (RoadAccident roadAccident : consumedData) {
					if (roadAccident.getTimeosDay() != null) {
						if (roadAccident.getTimeosDay() == DAYTIMETYPE.MORNING
								|| roadAccident.getTimeosDay() == DAYTIMETYPE.AFTERNOON) {
							writeRoadAccidentData(csvPrinterDayTime,
									roadAccident);
						} else {
							writeRoadAccidentData(csvPrinterNightTime,
									roadAccident);
						}
					}
				}
				consumedData = dataQueue.take();
			}
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}

	// TODO implement this method
	private void writeRoadAccidentData(CSVPrinter csvPrinter,
			RoadAccident roadAccident) throws IOException {
		List<Object> valueList = new ArrayList<Object>();
		valueList.add(roadAccident.getAccidentId());
		valueList.add(roadAccident.getLongitude());
		valueList.add(roadAccident.getLatitude());
		valueList.add(roadAccident.getPoliceForce());		
		valueList.add(roadAccident.getForceContact());
		valueList.add(roadAccident.getAccidentSeverity());
		valueList.add(roadAccident.getNumberOfVehicles());
		valueList.add(roadAccident.getNumberOfCasualties());
		valueList.add(roadAccident.getDate());
		valueList.add(roadAccident.getTimeosDay());
		valueList.add(roadAccident.getTime());
		valueList.add(roadAccident.getDistrictAuthority());
		valueList.add(roadAccident.getLightConditions());
		valueList.add(roadAccident.getWeatherConditions());
		valueList.add(roadAccident.getRoadSurfaceConditions());
		
		
		csvPrinter.printRecord(valueList);	
		
	}
}