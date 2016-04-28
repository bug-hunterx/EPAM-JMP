package com.epam.processor;

import com.epam.data.RoadAccident;
import com.epam.dataservice.AccidentBatchLoaderRunnable;
import com.epam.dataservice.AccidentBatchProcessorRunnable;
import com.epam.dataservice.AccidentBatchWriterRunnable;

import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Nick on 27.04.2016.
 */
public class AccidentConcurrentParser {

    private static String filePath1 = "src/main/resources/DfTRoadSafety_Accidents_2009.csv";
    private static String filePath2 = "src/main/resources/DfTRoadSafety_Accidents_2010.csv";
    private static String filePath3 = "src/main/resources/DfTRoadSafety_Accidents_2011.csv";
    private static String filePath4 = "src/main/resources/DfTRoadSafety_Accidents_2012.csv";

    private static String outFilePath1 = "src/main/resources/out1.csv";
    private static String outFilePath2 = "src/main/resources/out2.csv";



    final static int CAPACITY = 600;

    public static void main(String args[]) throws InterruptedException{
        ExecutorService executor = Executors.newFixedThreadPool(7);

        BlockingQueue<List<RoadAccident>> accidentsConcurrentQueue = new ArrayBlockingQueue<List<RoadAccident>>(CAPACITY);

        BlockingQueue<List<RoadAccident>> morningConcurrentQueue = new ArrayBlockingQueue<List<RoadAccident>>(CAPACITY);
        BlockingQueue<List<RoadAccident>> eveningConcurrentQueue = new ArrayBlockingQueue<List<RoadAccident>>(CAPACITY);

        executor.execute(new AccidentBatchLoaderRunnable(400, accidentsConcurrentQueue, filePath1));

        executor.execute(new AccidentBatchProcessorRunnable(accidentsConcurrentQueue, morningConcurrentQueue, eveningConcurrentQueue));

        executor.execute(new AccidentBatchWriterRunnable(morningConcurrentQueue, outFilePath1));
        executor.execute(new AccidentBatchWriterRunnable(eveningConcurrentQueue, outFilePath2));

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
