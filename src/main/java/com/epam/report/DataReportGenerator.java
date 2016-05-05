package com.epam.report;

import java.util.List;
import java.util.concurrent.*;

import com.epam.data.RoadAccident;
import com.epam.dataservice.AccidentBatchLoaderRunnable;
import com.epam.dataservice.AccidentBatchProcessorRunnable;
import com.epam.dataservice.AccidentBatchWriterRunnable;

public class DataReportGenerator {

	List<String> inputFilesList;
	String morningFileName;
	String eveningFileName;

	final static int BLOCKING_QUEUE_CAPACITY = 600;
	final static int BATCH_SIZE = 400;

	public DataReportGenerator(List<String> inputFilesList, String morningFileName, String eveningFileName) {
		this.inputFilesList = inputFilesList;
		this.morningFileName = morningFileName;
		this.eveningFileName = eveningFileName;
	}
	

	public void generateReport() throws InterruptedException {

		ExecutorService executor = Executors.newFixedThreadPool(inputFilesList.size() + 3);

		BlockingQueue<List<RoadAccident>> accidentsConcurrentQueue = new ArrayBlockingQueue<List<RoadAccident>>(BLOCKING_QUEUE_CAPACITY);

		BlockingQueue<List<RoadAccident>> morningConcurrentQueue = new ArrayBlockingQueue<List<RoadAccident>>(BLOCKING_QUEUE_CAPACITY);
		BlockingQueue<List<RoadAccident>> eveningConcurrentQueue = new ArrayBlockingQueue<List<RoadAccident>>(BLOCKING_QUEUE_CAPACITY);

		for(String fileName : inputFilesList) {
			executor.execute(new AccidentBatchLoaderRunnable(BATCH_SIZE, accidentsConcurrentQueue, fileName));
		}

		executor.execute(new AccidentBatchProcessorRunnable(accidentsConcurrentQueue, morningConcurrentQueue, eveningConcurrentQueue));

		executor.execute(new AccidentBatchWriterRunnable(morningConcurrentQueue, morningFileName));
		executor.execute(new AccidentBatchWriterRunnable(eveningConcurrentQueue, eveningFileName));

		executor.shutdown();

		if(executor.awaitTermination(30, TimeUnit.SECONDS)){
			System.out.println("task completed");
		} else {
			System.out.println("Shutting down");
			executor.shutdownNow();
		};
		System.out.println("That's all folks!");
	}
	
}
