package com.epam.dataservice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.epam.data.RoadAccident;
import com.epam.dataservice.accident.AccidentProcessor;
import com.epam.dataservice.accident.AccidentReader;
import com.epam.dataservice.accident.AccidentWriter;

public class AccidentReaderProcessorWriterTest {

	private static int readerThreadCount = 2;
	private static int readerQueueCapacity = 10000;
	private static int processorThreadCount = 5;
	private static int daytimeQueueCapacity = 10000;
	private static int nighttimeQueueCapacity = 10000;
	private static List<String> accidentFileList = new ArrayList<String>();
	static {
		accidentFileList.add("src/main/resources/DfTRoadSafety_Accidents_2009.csv");
		accidentFileList.add("src/main/resources/DfTRoadSafety_Accidents_2010.csv");
		accidentFileList.add("src/main/resources/DfTRoadSafety_Accidents_2011.csv");
		accidentFileList.add("src/main/resources/DfTRoadSafety_Accidents_2012.csv");
		accidentFileList.add("src/main/resources/DfTRoadSafety_Accidents_2013.csv");
	}

	private static ExecutorService file2Queue(BlockingQueue<RoadAccident> readerQueue) {
		ExecutorService readerExecutor = Executors.newFixedThreadPool(readerThreadCount);
		for (String accidentFile : accidentFileList) {
			readerExecutor.execute(new AccidentReader(readerQueue, accidentFile));
		}
		return readerExecutor;
	}

	private static List<Thread> processData(BlockingQueue<RoadAccident> readerQueue,
			BlockingQueue<RoadAccident> daytimeQueue, BlockingQueue<RoadAccident> nighttimeQueue) {
		List<Thread> processorThreadList = new ArrayList<Thread>();
		for (int i = 0; i < processorThreadCount; i++) {
			Thread processorThread = new Thread(new AccidentProcessor(readerQueue, daytimeQueue, nighttimeQueue));
			processorThread.start();
			processorThreadList.add(processorThread);
		}
		return processorThreadList;
	}

	private static Thread write2File(BlockingQueue<RoadAccident> accidentQueue, String fileName) {
		Thread writerThread = new Thread(new AccidentWriter(accidentQueue, fileName));
		writerThread.start();
		return writerThread;
	}

	public static void main(String[] args) throws Exception {
		BlockingQueue<RoadAccident> readerQueue = new ArrayBlockingQueue<RoadAccident>(readerQueueCapacity);
		BlockingQueue<RoadAccident> daytimeQueue = new ArrayBlockingQueue<RoadAccident>(daytimeQueueCapacity);
		BlockingQueue<RoadAccident> nighttimeQueue = new ArrayBlockingQueue<RoadAccident>(nighttimeQueueCapacity);

		ExecutorService readerExecutor = file2Queue(readerQueue);
		List<Thread> processorThreadList = processData(readerQueue, daytimeQueue, nighttimeQueue);
		Thread daytimeWriterThread = write2File(daytimeQueue, "src/main/resources/DaytimeAccidents.csv");
		Thread nighttimeWriterThread = write2File(nighttimeQueue, "src/main/resources/NighttimeAccidents.csv");

		readerExecutor.shutdown();
		readerExecutor.awaitTermination(5, TimeUnit.MINUTES);
		while (!readerQueue.isEmpty())
			Thread.sleep(1000);
		for (Thread processorThread : processorThreadList)
			processorThread.interrupt();
		while (!daytimeQueue.isEmpty())
			Thread.sleep(1000);
		daytimeWriterThread.interrupt();
		while (!nighttimeQueue.isEmpty())
			Thread.sleep(1000);
		nighttimeWriterThread.interrupt();
	}

}
