package com.epam.dataservice;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.Assert;

import com.epam.data.RoadAccident;

@RunWith(MockitoJUnitRunner.class) 
public class RoadAccidentServiceTest {
	
	private RoadAccidentService roadAccidentService;
	private BlockingQueue<RoadAccident> accidentReaderQueue;
	private BlockingQueue<RoadAccident> dayTimeQueue;
	private BlockingQueue<RoadAccident> nightTimeQueue;
	
	private PoliceForceService policeForceService;

	@Before
	public void setUp() throws Exception {
		accidentReaderQueue = new ArrayBlockingQueue<RoadAccident>(5000);
		dayTimeQueue = new ArrayBlockingQueue<RoadAccident>(5000);
		nightTimeQueue = new ArrayBlockingQueue<RoadAccident>(5000);
		policeForceService = new PoliceForceService();
		
		roadAccidentService = new RoadAccidentService(accidentReaderQueue, dayTimeQueue, nightTimeQueue, policeForceService);
	}

	@Test
	public void testReadFromRoadAccientFileTo() {
		ExecutorService readerExecutor = roadAccidentService.readFromRoadAccientFileTo(accidentReaderQueue);
		Assert.notNull(readerExecutor);
	}

	@Test
	public void testStartRoadAccidentService() {
		List<Thread> serviceThreadList = roadAccidentService.startRoadAccidentService(accidentReaderQueue, dayTimeQueue, nightTimeQueue);
		Assert.notNull(serviceThreadList);
		Assert.isTrue(serviceThreadList.size() > 0);
	}
	
}
