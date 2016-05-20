package com.epam.h3;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import au.com.bytecode.opencsv.CSVWriter;

import com.epam.data.RoadAccident;
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
		
		try {
			while(isRunning){
				System.out.println("--->>> Started To Get Data From Queue");
				List<RoadAccident> list = dataQueue.poll(5, TimeUnit.SECONDS);
				if (null != list && list.size() > 0) {
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
	
	private void writeData(List<RoadAccident> list) throws IOException{		
//		CSVWriter writer = new CSVWriter(new FileWriter(dataFileName, true));
		String[] lines = (String[])list.toArray();
		for(String line : lines) {
		    System.out.println(line);
	    }
//	    writer.close(); 	
	}

}
