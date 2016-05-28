package com.epam.dataservice;

import java.io.File;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

import com.epam.data.RoadAccident;

public class RoadAccidentServiceIntegrationTest {
	
	private RoadAccidentService roadAccidentService;
	private BlockingQueue<RoadAccident> accidentReaderQueue;
	private BlockingQueue<RoadAccident> dayTimeQueue;
	private BlockingQueue<RoadAccident> nightTimeQueue;
	private PoliceForceService policeForceService;
	
	@Before
	public void setUp() {
		accidentReaderQueue = new ArrayBlockingQueue<RoadAccident>(5000);
		dayTimeQueue = new ArrayBlockingQueue<RoadAccident>(5000);
		nightTimeQueue = new ArrayBlockingQueue<RoadAccident>(5000);
		policeForceService = new PoliceForceService();
		
		roadAccidentService = new RoadAccidentService(accidentReaderQueue, dayTimeQueue, nightTimeQueue, policeForceService);
	}

	@Test
	public void testLoadRoadAccidentFilesAndWriteTo2DifferentFilesByDayTimeField() throws InterruptedException {
		ExecutorService readerExecutor = roadAccidentService.readFromRoadAccientFileTo(accidentReaderQueue);
		Assert.notNull(readerExecutor);
		List<Thread> serviceThreadList = roadAccidentService.startRoadAccidentService(accidentReaderQueue, dayTimeQueue, nightTimeQueue);
		Assert.notNull(serviceThreadList);
		Assert.isTrue(serviceThreadList.size() > 0);
		Thread daytimeWriterThread = roadAccidentService.writeRoadAccidentToFile(dayTimeQueue, "src/main/resources/DaytimeAccidents.csv");
		Assert.notNull(daytimeWriterThread);
		Thread nighttimeWriterThread = roadAccidentService.writeRoadAccidentToFile(nightTimeQueue,"src/main/resources/NighttimeAccidents.csv");
		Assert.notNull(nighttimeWriterThread);

		readerExecutor.shutdown();
		readerExecutor.awaitTermination(5, TimeUnit.MINUTES);
		while (!accidentReaderQueue.isEmpty()) {
			Thread.sleep(1000);
		}
		for (Thread processorThread : serviceThreadList)
			processorThread.interrupt();
		while (!dayTimeQueue.isEmpty())
			Thread.sleep(1000);
		daytimeWriterThread.interrupt();
		while (!nightTimeQueue.isEmpty())
			Thread.sleep(1000);
		nighttimeWriterThread.interrupt();

		Assert.isTrue(new File("src/main/resources/DaytimeAccidents.csv").exists());
		Assert.isTrue(new File("src/main/resources/NighttimeAccidents.csv").exists());
		}
		
}
