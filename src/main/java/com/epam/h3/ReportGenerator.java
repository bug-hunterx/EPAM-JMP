package com.epam.h3;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.epam.data.RoadAccident;
import com.epam.dataservice.AccidentBatchLoaderRunnable;

public class ReportGenerator {

	private static List<String> pathList;
	private static int dealCount = 10000;

	private static boolean validate(){
		if(null == pathList || pathList.size() == 0){
			System.out.println("--->>> Please Fill Data First.");
			return false;
		}
		return true;
	}
	
	
	public static void fillData(List<String> filePathList){
		pathList = filePathList;
	}
	
	public static void generator(){		
		if(!validate()){	
			return;
		}
		
		//start 
		System.out.println("----->>> Started!");
		try {
			ExecutorService executor = Executors.newFixedThreadPool(pathList.size()*2);
			for(String filePath : pathList){
				BlockingQueue<List<RoadAccident>> dataQueue = new ArrayBlockingQueue<List<RoadAccident>>(1);
				AccidentBatchLoaderRunnable readerTask = new AccidentBatchLoaderRunnable(dealCount,dataQueue,filePath+".csv");
				AccidentBatchWriteRunnable writeTask = new AccidentBatchWriteRunnable(dataQueue,filePath);
				executor.execute(readerTask);
				executor.execute(writeTask);
			}		
			//shut down
			executor.shutdown();
			executor.awaitTermination(1, TimeUnit.MINUTES);
		} catch (OutOfMemoryError e) {
			System.out.println("--->>> Memory Is Out!!!");
			replay();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		System.out.println("Data Generate Finished ");
	}

	private static void replay(){
		dealCount = dealCount/2;
		generator();
	}
	
}
