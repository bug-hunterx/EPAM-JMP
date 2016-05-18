package com.epam.dataservice;

import com.epam.data.RoadAccident;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by Tanmoy on 4/26/2016.
 */
public class AccidentBatchLoaderTest {
    private static String filePath1 = "src/main/resources/DfTRoadSafety_Accidents_2009.csv";
    private static String filePath2 = "src/main/resources/DfTRoadSafety_Accidents_2010.csv";
    private static String filePath3 = "src/main/resources/DfTRoadSafety_Accidents_2011.csv";
    private static String filePath4 = "src/main/resources/DfTRoadSafety_Accidents_2012.csv";

    public static void handle2FilesWithRunnable() {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        BlockingQueue<List<RoadAccident>> dataQueue1 = new ArrayBlockingQueue<List<RoadAccident>>(2);
        BlockingQueue<List<RoadAccident>> dataQueue2 = new ArrayBlockingQueue<List<RoadAccident>>(2);

        AccidentBatchLoaderRunnable readerTask1 = new AccidentBatchLoaderRunnable(10000,dataQueue1,filePath1);
        AccidentBatchLoaderRunnable readerTask2 = new AccidentBatchLoaderRunnable(10000,dataQueue2,filePath2);

        executor.execute(readerTask1);
        executor.execute(readerTask2);
        System.out.println("Started readers");

        executor.execute(new DataConsumer(dataQueue1, filePath1));
        executor.execute(new DataConsumer(dataQueue2, filePath2));

        System.out.println("started consumers");

        endDatLoading(executor);

    }

    public static void handle2FilesWithCallable() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        /**
         * We want to process 2 files at a time so using 2 blocking queue
         * to hold the result
         */
        BlockingQueue<List<RoadAccident>> dataQueue1 = new ArrayBlockingQueue<List<RoadAccident>>(2);
        BlockingQueue<List<RoadAccident>> dataQueue2 = new ArrayBlockingQueue<List<RoadAccident>>(2);


        //start consumer threads
        executor.execute(new DataConsumer(dataQueue1, "dataQueue1"));
        executor.execute(new DataConsumer(dataQueue2, "dataQueue2"));
        System.out.println("Started Consumers");

        //Create 2 reader task and start
        FutureTask<Integer> readerTask1 = new FutureTask<Integer>(new AccidentBatchLoader(40000,dataQueue1,filePath1));
        FutureTask<Integer> readerTask2 = new FutureTask<Integer>(new AccidentBatchLoader(40000,dataQueue2,filePath2));

        System.out.println("Starting 2 readers");
        executor.execute(readerTask1);
        executor.execute(readerTask2);

        readerTask1.get();
        readerTask2.get();

        endDatLoading(executor);
    }

    private static void endDatLoading(ExecutorService executor){
        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Data loading finished");
    }

    public static void handleMultipleFilesWithCallable() throws ExecutionException, InterruptedException {

        LinkedList<String> fileQueue = new LinkedList<>();
        fileQueue.add(filePath3);
        fileQueue.add(filePath4);
        fileQueue.add(filePath1);
        fileQueue.add(filePath2);

        ExecutorService executor = Executors.newFixedThreadPool(4);

        /**
         * We want to process 2 files at a time so using 2 blocking queue
         * to hold the result
         */
        BlockingQueue<List<RoadAccident>> dataQueue1 = new ArrayBlockingQueue<List<RoadAccident>>(2);
        BlockingQueue<List<RoadAccident>> dataQueue2 = new ArrayBlockingQueue<List<RoadAccident>>(2);


        //start consumer threads
        executor.execute(new DataConsumerNonEnding(dataQueue1, "dataQueue1"));
        executor.execute(new DataConsumerNonEnding(dataQueue2, "dataQueue2"));
        System.out.println("Started Consumers");

        //Create 2 reader task and start
        FutureTask<Integer> readerTask1 = new FutureTask<Integer>(new AccidentBatchLoader(40000,dataQueue1,fileQueue.remove()));
        FutureTask<Integer> readerTask2 = new FutureTask<Integer>(new AccidentBatchLoader(40000,dataQueue2,fileQueue.remove()));

        System.out.println("Starting 2 readers");
        executor.execute(readerTask1);
        executor.execute(readerTask2);

        while(!fileQueue.isEmpty()){
            if(readerTask1.isDone()){
                readerTask1 = new FutureTask<Integer>(new AccidentBatchLoader(40000,dataQueue1,fileQueue.remove()));
                executor.execute(readerTask1);
            }
            if(readerTask2.isDone()){
                readerTask2 = new FutureTask<Integer>(new AccidentBatchLoader(40000,dataQueue2,fileQueue.remove()));
                executor.execute(readerTask2);
            }
            Thread.sleep(1000*5);
        }

        while(!dataQueue1.isEmpty() || !dataQueue2.isEmpty()){
            Thread.sleep(1000*5);
        }
        endDatLoading(executor);
    }

    public static void main(String[] args) throws Exception{
        //handle2FilesWithRunnable();
       //handle2FilesWithCallable();
        handleMultipleFilesWithCallable();
    }


    private static class DataConsumer implements Runnable{

        private BlockingQueue<List<RoadAccident>> dataQueue;
        private String fileName;

        public  DataConsumer(BlockingQueue<List<RoadAccident>> dataQueue, String fileName ){
            this.dataQueue = dataQueue;
            this.fileName = fileName;
        }

        @Override
        public void run() {
            int dataCounter = 0;
            List<RoadAccident> consumedData = null;
            try {
                consumedData = dataQueue.take();
                dataCounter += consumedData.size();

                while(consumedData != null && !consumedData.isEmpty()){
                    System.out.println(" Consumed " + dataCounter + " records from " + fileName);
                    consumedData = dataQueue.take();
                    dataCounter += consumedData.size();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class DataConsumerNonEnding implements Runnable{

        private BlockingQueue<List<RoadAccident>> dataQueue;
        private String fileName;

        public  DataConsumerNonEnding(BlockingQueue<List<RoadAccident>> dataQueue, String fileName ){
            this.dataQueue = dataQueue;
            this.fileName = fileName;
        }

        @Override
        public void run() {
            int dataCounter = 0;
            List<RoadAccident> consumedData = null;
            try {
                consumedData = dataQueue.take();
                dataCounter += consumedData.size();

                while(true){
                    System.out.println(" Consumed " + dataCounter + " records from " + fileName);
                    consumedData = dataQueue.take();
                    dataCounter += consumedData.size();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
