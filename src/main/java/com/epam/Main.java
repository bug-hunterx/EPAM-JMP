package com.epam;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.epam.data.RoadAccident;
import com.epam.dataservice.AccidentBatchLoader;


/**
 * Created by Tkachi on 2016/4/3.
 */
public class Main {
    
    private static final int DEFAULT_THREAD_POOL_SIZE = 5;
    private static final int DEFAULT_BATCH_SIZE = 40000;
    private static final int DEFAULT_PRODUCER_SIZE = 2;
    private static final int DEFAULT_CONSUMER_SIZE = 2;
    private static final int DEFAULT_STORAGE_QUEUE_SIZE = 10;
    private static final String OUTPUT_FILE_PATH_DAYTIME="src/main/resources/DaytimeAccidents.csv";
    private static final String OUTPUT_FILE_PATH_NIGHTTIME="src/main/resources/NighttimeAccidents.csv";
    private static CSVFormat csvFormat = CSVFormat.DEFAULT.withRecordSeparator('\n');
 
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        String[] inputFilesList = args[0].split(",");
        LinkedList<String> inputFileQueue = new LinkedList<String>();
        inputFileQueue.addAll(Arrays.asList(inputFilesList));
        
        int threadPoolSize = 0;
        try{
        	threadPoolSize = Integer.parseInt(args[1]);
        }catch(Exception e){
        	threadPoolSize = DEFAULT_THREAD_POOL_SIZE;
        }
        
        int batchSize = 0;
        try{
        	batchSize = Integer.parseInt(args[2]);
        }catch(Exception e){
        	batchSize = DEFAULT_BATCH_SIZE;
        }
        
        int producerSize = 0;
        try{
        	producerSize = Integer.parseInt(args[3]);
        }catch(Exception e){
        	producerSize = DEFAULT_PRODUCER_SIZE; 
        }
        
        int consumerSize = 0;
        try{
        	consumerSize = Integer.parseInt(args[4]);
        }catch(Exception e){
        	consumerSize = DEFAULT_CONSUMER_SIZE; 
        }
        handleMultipleFilesWithCallable(inputFileQueue,threadPoolSize,batchSize,producerSize,consumerSize);
    }
    
    public static void handleMultipleFilesWithCallable(LinkedList<String> inputFileQueue,int threadPoolSize, int batchSize,int producerSize, int consumerSize) throws ExecutionException, InterruptedException, IOException {
        
        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);

        BlockingQueue<List<RoadAccident>> dataQueue = new ArrayBlockingQueue<List<RoadAccident>>(DEFAULT_STORAGE_QUEUE_SIZE);        
        FileWriter fileWriter1 = new FileWriter(OUTPUT_FILE_PATH_DAYTIME);
        CSVPrinter csvWriterDayTime = new CSVPrinter(fileWriter1,csvFormat);
        printCSVHeader(csvWriterDayTime);
        
        FileWriter fileWriter2 = new FileWriter(OUTPUT_FILE_PATH_NIGHTTIME);
        CSVPrinter csvWriterNightTime = new CSVPrinter(fileWriter2,csvFormat);
        printCSVHeader(csvWriterNightTime);
        //start consumer threads
        System.out.println("Starting Consumers");
        for(int i=0;i<consumerSize;i++){
        	executor.execute(new DataCSVFileWriter(dataQueue, csvWriterDayTime,csvWriterNightTime));
        }    
            
       

        //Create 2 reader task and start
        System.out.println("Starting readers");
        List<FutureTask<Integer>> futureTaskList = new ArrayList<FutureTask<Integer>>(); 
        
        for (int i = 0; i < producerSize; i++) {
			if (inputFileQueue.isEmpty()) {
				Thread.sleep(1000*5);
				break;
			}
			FutureTask<Integer> readerTask = new FutureTask<Integer>(
					new AccidentBatchLoader(batchSize, dataQueue,
							inputFileQueue.remove()));
			futureTaskList.add(readerTask);
			executor.execute(readerTask);

		}      
               
        while(!inputFileQueue.isEmpty()){
        	List<FutureTask<Integer>> newFutureTaskList = new ArrayList<FutureTask<Integer>>();
        	for(FutureTask<Integer> task:futureTaskList){
        		if(task.isDone()){
        			if (inputFileQueue.isEmpty()) {
        				break;
        			}
        			FutureTask<Integer> readerTask = new FutureTask<Integer>(
        					new AccidentBatchLoader(batchSize, dataQueue,
        							inputFileQueue.remove()));
        			newFutureTaskList.add(readerTask);
        			executor.execute(readerTask);
        		}else{
        			newFutureTaskList.add(task);
        		}        		
        	}   
        	futureTaskList.clear();//clean memory at once
    		futureTaskList = newFutureTaskList;
    		Thread.sleep(1000*5); //process 1 round and sleep
        }
        
        while(!dataQueue.isEmpty()){
            Thread.sleep(1000*5);
        }
        endDataProcessing(executor,fileWriter1,fileWriter2,csvWriterDayTime,csvWriterNightTime);
    }
    
    //graceful shutdown here
    private static void endDataProcessing(ExecutorService executor,FileWriter fileWriter1,FileWriter fileWriter2,CSVPrinter CSVPrinter1,CSVPrinter CSVPrinter2) throws IOException, InterruptedException{
        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.MINUTES);        
        }finally{
        	CSVPrinter1.close();
        	CSVPrinter2.close();
        	fileWriter1.close();
        	fileWriter2.close();        	
        }
        System.out.println("Data processing finished");
    }
    
    private static void printCSVHeader(CSVPrinter csvPrinter) throws IOException{
    	List<String> columnNames = new ArrayList<String>();
    	columnNames.add("accidentId");
    	columnNames.add("longitude");
    	columnNames.add("latitude");
    	columnNames.add("policeForce");
    	columnNames.add("forceContact");
    	columnNames.add("accidentSeverity");
    	columnNames.add("numberOfVehicles");
    	columnNames.add("numberOfCasualties");
    	columnNames.add("date");
    	columnNames.add("timeosDay");
    	columnNames.add("time");
    	columnNames.add("districtAuthority");
    	columnNames.add("lightConditions");
    	columnNames.add("weatherConditions");
    	columnNames.add("roadSurfaceConditions");    	
    	csvPrinter.printRecord(columnNames);
    }

}
