package com.epam.dataservice;

import com.epam.data.RoadAccident;
import com.epam.data.RoadAccidentBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bill on 16-5-1.
 */
public class HomeWork3 {
    private final int MAX_READ_THREADS = 2;
    private final int MAX_PROC_THREADS = 4;
    public static final int MAX_ENRICH_THREADS = 100;
    private final int MAX_WRITE_THREADS = 2;
    public static final int MAX_BATCH_SIZE = 40000;
    public static final String outputFileNames[] = {"NighttimeAccidents.csv", "DaytimeAccidents.csv"};

    private LinkedList<String> fileList;
    private BlockingQueue<List<RoadAccident>> readQueue;
    private ExecutorService readExecutor;
    private List<Future<Integer>> readTaskList = new ArrayList<>();

    private ExecutorService procExecutor;

    private List<BlockingQueue<RoadAccident>> writeQueues = new ArrayList<>(MAX_WRITE_THREADS);
    private ExecutorService writeExecutor;

    public HomeWork3(LinkedList<String> fileList) {
        this.fileList = fileList;
    }

    public void startTask() {
        readQueue = new ArrayBlockingQueue<List<RoadAccident>>(MAX_PROC_THREADS);
        for (int i = 0; i < MAX_WRITE_THREADS; i++) {
            writeQueues.add( new ArrayBlockingQueue<RoadAccident>(MAX_BATCH_SIZE/2) );
        }

        readExecutor = Executors.newFixedThreadPool(MAX_READ_THREADS);
        for (String file : fileList ) {
            FutureTask<Integer> readTask = new FutureTask<Integer>(
                    new AccidentBatchLoader(MAX_BATCH_SIZE,readQueue,file));
            readTaskList.add(readTask);
            readExecutor.submit(readTask);
        }

        procExecutor = Executors.newFixedThreadPool(MAX_PROC_THREADS);
        for (int i = 0; i < MAX_PROC_THREADS; i++) {
            procExecutor.submit(new AccidentBatchProcessor(readQueue, writeQueues));
        }

        writeExecutor = Executors.newFixedThreadPool(MAX_WRITE_THREADS);
        for (int i = 0; i < MAX_WRITE_THREADS; i++) {
            writeExecutor.submit(new AccidentBatchWriter(writeQueues.get(i),outputFileNames[i]));
        }
        System.out.println("All task submitted");
    }

    public void run() {
        startTask();

        try {
            readQueueFinish();
            endDatLoading(procExecutor);
            for (int i = 0; i < MAX_WRITE_THREADS; i++) {
                System.out.println("Send quit message to writeQueue, count: " + Integer.toString(i+1));
                writeQueues.get(i).put( new RoadAccidentBuilder(null).build() );
            }
            endDatLoading(writeExecutor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readQueueFinish() throws Exception {
        Boolean taskStatus;
        while(!readTaskList.isEmpty()) {
            Iterator<Future<Integer>> iter = readTaskList.iterator();
            while (iter.hasNext()) {
                if ( iter.next().isDone() ) {
                    iter.remove();
                }
            }
            TimeUnit.SECONDS.sleep(1);
        }

        readExecutor.submit(new Runnable() {
            @Override
            public void run() {
                List<RoadAccident> emptyAccidentList = new ArrayList<RoadAccident>(1);
                for (int i = 0; i < MAX_PROC_THREADS; i++) {
                    System.out.println("Send quit message to readQueue, count: " + Integer.toString(i+1));
                    try {
                        readQueue.put(emptyAccidentList);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        readExecutor.shutdown();
        try {
            taskStatus = readExecutor.awaitTermination(5, TimeUnit.MINUTES);
            if (taskStatus) {
                System.out.println("Data loading finished");
            } else {
                System.out.println("Data loading task timeout");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception{
    }

    private static class ReadTerminator implements Runnable {
        public static List<RoadAccident> emptyAccidentList = new ArrayList<RoadAccident>(1);
        private BlockingQueue<List<RoadAccident>> dataQueue;

        public ReadTerminator(BlockingQueue<List<RoadAccident>> dataQueue) {
            this.dataQueue = dataQueue;
        }

        @Override
        public void run() {
            try {
                System.out.println("Send quit message");
                dataQueue.put(emptyAccidentList);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private static void endDatLoading(ExecutorService executor){
        executor.shutdown();
        try {
            if (executor.awaitTermination(10, TimeUnit.SECONDS)) {
                System.out.println("All task finished for " + executor.getClass().getSimpleName());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        System.out.println("Data loading finished");
    }

}
