package com.epam.dataservice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.epam.data.RoadAccident;

public class RoadAccidentService implements Runnable {

	private static String DAY_TIME_MORNING = "MORNING";
	private static String DAY_TIME_AFTERNOON = "AFTERNOON";
	private static String DAY_TIME_EVENING = "EVENING";
	private static String DAY_TIME_NIGHT = "NIGHT";

	private static int NUMBER_OF_THREAD = 6;
	
	private BlockingQueue<RoadAccident> accidentReaderQueue;
	private BlockingQueue<RoadAccident> daytimeQueue;
	private BlockingQueue<RoadAccident> nighttimeQueue;
	private PoliceForceService policeForceService;

	private static List<String> roadAccidentFiles = new ArrayList<String>();

	static {
		roadAccidentFiles.add("src/main/resources/DfTRoadSafety_Accidents_2009.csv");
		roadAccidentFiles.add("src/main/resources/DfTRoadSafety_Accidents_2010.csv");
		roadAccidentFiles.add("src/main/resources/DfTRoadSafety_Accidents_2011.csv");
		roadAccidentFiles.add("src/main/resources/DfTRoadSafety_Accidents_2012.csv");
	}
	
	public RoadAccidentService(BlockingQueue<RoadAccident> accidentReaderQueue,
			BlockingQueue<RoadAccident> daytimeQueue, BlockingQueue<RoadAccident> nighttimeQueue,
			PoliceForceService policeForceService) {
		this.accidentReaderQueue = accidentReaderQueue;
		this.daytimeQueue = daytimeQueue;
		this.nighttimeQueue = nighttimeQueue;
		this.policeForceService = policeForceService;
	}

	public static ExecutorService readFromRoadAccientFileTo(BlockingQueue<RoadAccident> readerQueue) {
		ExecutorService readerExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREAD);
		for (String accidentFile : roadAccidentFiles) {
			readerExecutor.execute(new RoadAccidentReader(accidentFile, readerQueue));
		}
		return readerExecutor;
	}
	
	public static Thread writeRoadAccidentToFile(BlockingQueue<RoadAccident> accidentQueue, String fileName) {
		Thread writerThread = new Thread(new RoadAccidentWriter(fileName, accidentQueue));
		writerThread.start();
		return writerThread;
	}  

	public static List<Thread> startRoadAccidentService(BlockingQueue<RoadAccident> readerQueue,  
			BlockingQueue<RoadAccident> dayTimeQueue, BlockingQueue<RoadAccident> nightTimeQueue) {
		List<Thread> serviceThreadList = new ArrayList<Thread>();
		PoliceForceService policeForceService = new PoliceForceService();
		for (int i = 0; i < NUMBER_OF_THREAD; i++) {
			Thread serviceThread = new Thread(new RoadAccidentService(readerQueue, dayTimeQueue, nightTimeQueue, policeForceService));
			serviceThread.start();
			serviceThreadList.add(serviceThread);
		}
		return serviceThreadList;
	} 

	
	@Override
	public void run() {
		loadAccidentDataIntoQByDayTime();
	}

	private void loadAccidentDataIntoQByDayTime() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				RoadAccident roadAccident = accidentReaderQueue.take();
				roadAccident.setForceContact(policeForceService.getContactNo(roadAccident.getPoliceForce()));
				if (roadAccident.getTime().getHour() >= 18) {
					roadAccident.setDayTime(DAY_TIME_NIGHT);
					nighttimeQueue.put(roadAccident);
				} else if (roadAccident.getTime().getHour() >= 12) {
					roadAccident.setDayTime(DAY_TIME_AFTERNOON);
					daytimeQueue.put(roadAccident);
				} else if (roadAccident.getTime().getHour() >= 6) {
					roadAccident.setDayTime(DAY_TIME_MORNING);
					daytimeQueue.put(roadAccident);
				} else {
					roadAccident.setDayTime(DAY_TIME_EVENING);
					nighttimeQueue.put(roadAccident);
				}
			}
		} catch (InterruptedException e) {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setAccidentReaderQueue(BlockingQueue<RoadAccident> accidentReaderQueue) {
		this.accidentReaderQueue = accidentReaderQueue;
	}

	public void setDaytimeQueue(BlockingQueue<RoadAccident> daytimeQueue) {
		this.daytimeQueue = daytimeQueue;
	}

	public void setNighttimeQueue(BlockingQueue<RoadAccident> nighttimeQueue) {
		this.nighttimeQueue = nighttimeQueue;
	}

	public void setPoliceForceService(PoliceForceService policeForceService) {
		this.policeForceService = policeForceService;
	}
	
	public static void main(String[] args) throws InterruptedException {
//		BlockingQueue<RoadAccident> accidentReaderQueue = new ArrayBlockingQueue<RoadAccident>(5000);
//		BlockingQueue<RoadAccident> daytimeQueue = new ArrayBlockingQueue<RoadAccident>(5000);
//		BlockingQueue<RoadAccident> nighttimeQueue = new ArrayBlockingQueue<RoadAccident>(5000);
//
//		ExecutorService readerExecutor = readFromRoadAccientFileTo(accidentReaderQueue);
//		List<Thread> serviceThreadList = startRoadAccidentService(accidentReaderQueue, daytimeQueue, nighttimeQueue);
//		Thread daytimeWriterThread = writeRoadAccidentToFile(daytimeQueue, "src/main/resources/DaytimeAccidents.csv");
//		Thread nighttimeWriterThread = writeRoadAccidentToFile(nighttimeQueue,
//				"src/main/resources/NighttimeAccidents.csv");
//
//		readerExecutor.shutdown();
//		readerExecutor.awaitTermination(5, TimeUnit.MINUTES);
//		while (!accidentReaderQueue.isEmpty())
//			Thread.sleep(1000);
//		for (Thread processorThread : serviceThreadList)
//			processorThread.interrupt();
//		while (!daytimeQueue.isEmpty())
//			Thread.sleep(1000);
//		daytimeWriterThread.interrupt();
//		while (!nighttimeQueue.isEmpty())
//			Thread.sleep(1000);
//		nighttimeWriterThread.interrupt();

	}
}
