package com.epam.h3;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import au.com.bytecode.opencsv.CSVWriter;

import com.epam.data.RoadAccident;
import com.epam.dataservice.PoliceForceService;
import com.epam.dataservice.RoadAccidentParser;

public class AccidentBatchWriteRunnable implements Runnable{

    private BlockingQueue<List<RoadAccident>> dataQueue;
    private String dataFileName;
    private RoadAccidentParser roadAccidentParser;
	
	public AccidentBatchWriteRunnable(BlockingQueue<List<RoadAccident>> dataQueue, String dataFileName) {
        this.dataQueue = dataQueue;
        this.dataFileName = dataFileName;
        roadAccidentParser = new RoadAccidentParser();
	}
	
	@Override
	public void run() {
		System.out.println("--->>> Write Thread:[" + Thread.currentThread().getId() + "] Started!");
		boolean isRunning = true;
		boolean hasWriteHead = false;
		
		try {
			while(isRunning){
				System.out.println("--->>> Started To Get Data From Queue");
				List<RoadAccident> list = dataQueue.poll(30, TimeUnit.SECONDS);
				if (null != list && list.size() > 0) {
					if(!hasWriteHead){
						writeHead();
						hasWriteHead = true;
					}
					System.out.println("Get Data From Queue");
					writeData(list);
				} else {
					isRunning = false;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("--->>> Get Data From Queue Error,Stop Write Thread[" + Thread.currentThread().getId() + "]");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("--->>> Write Data From Queue Error,Stop Write Thread[" + Thread.currentThread().getId() + "]");		
		} finally {
			Thread.currentThread().interrupt();
			System.out.println("--->>> Data Write Successful. Quit Write Thread:[" + Thread.currentThread().getId() + "]");
		}	
	}
	
	private void writeHead()throws IOException{
		CSVWriter writer = new CSVWriter(new FileWriter(dataFileName, true));
		String[] line = CVSHead.getTitleList().toArray(new String[CVSHead.getTitleList().size()]);
		writer.writeNext(line); 
		writer.flush();
	}
	
	private void writeData(List<RoadAccident> list) throws IOException{	
		CSVWriter writer = new CSVWriter(new FileWriter(dataFileName, true));  	
		String[] line = new String[CVSHead.getTitleList().size()];
		PoliceForceService service = new PoliceForceService();
		
		for (RoadAccident roadAccident : list) {		
			line[0] = roadAccident.getAccidentId();
			line[1] = String.valueOf(roadAccident.getLongitude());
			line[2] = String.valueOf(roadAccident.getLatitude());
			line[3] = roadAccident.getPoliceForce();
			line[4] = roadAccident.getAccidentSeverity();
			line[5] = String.valueOf(roadAccident.getNumberOfVehicles());
			line[6] = String.valueOf(roadAccident.getNumberOfCasualties());
			line[7] = roadAccident.getDate().format(DateTimeFormatter.ofPattern("M/d/yyyy"));
			line[8] = roadAccident.getDate().format(DateTimeFormatter.ofPattern("e"));
			line[9] = roadAccident.getTime().format(DateTimeFormatter.ofPattern("H:mm"));
			line[10] = roadAccident.getDistrictAuthority();			
			line[11] = roadAccident.getLightConditions();
			line[12] = roadAccident.getWeatherConditions();
			line[13] = roadAccident.getRoadSurfaceConditions();
			line[14] = service.getContactNo(roadAccident.getPoliceForce());
			line[15] = "Type";
			
			writer.writeNext(line); 
			writer.flush();
		}
		writer.close(); 			
	}

}
