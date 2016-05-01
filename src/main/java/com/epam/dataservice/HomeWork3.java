package com.epam.dataservice;

import com.epam.data.RoadAccident;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bill on 16-5-1.
 */
public class HomeWork3 {
    private final int MAX_READ_THREADS = 2;
    private final int MAX_PROC_THREADS = 2;
    private final int MAX_BATCH_SIZE = 40000;

    private LinkedList<String> fileList;
    private BlockingQueue<List<RoadAccident>> readQueue;
    private ExecutorService readExecutor;
    private List<Future<Integer>> readTaskList = new ArrayList<>();

    private ExecutorService procExecutor;

    public HomeWork3(LinkedList<String> fileList) {
        this.fileList = fileList;
        readQueue = new ArrayBlockingQueue<List<RoadAccident>>(MAX_PROC_THREADS);
    }

    public void buildReadTask() {
        readExecutor = Executors.newFixedThreadPool(MAX_READ_THREADS);
        procExecutor = Executors.newFixedThreadPool(MAX_PROC_THREADS);
    }

    public void buildTask() {
        buildReadTask();
    }

    public void run() {
        for (String file : fileList ) {
            FutureTask<Integer> readTask = new FutureTask<Integer>(
                    new AccidentBatchLoader(40000,readQueue,file));
            readTaskList.add(readTask);
            readExecutor.submit(readTask);
//            readExecutor.execute(readerTask);
        }
        for (int i = 0; i < MAX_PROC_THREADS; i++) {
            procExecutor.submit(new AccidentBatchProcessor(readQueue));
        }
        System.out.println("All task submitted");

        Boolean taskStatus;
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

/*
        for (int i = 0; i < MAX_PROC_THREADS; i++) {
            try {
                readQueue.put(null);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
*/
        System.out.println("Waiting readQueua empty=" + readQueue.isEmpty() + " Remain: "+ readQueue.size());
        while (!readQueue.isEmpty()) {
            try {
                System.out.println("readQueua empty=" + readQueue.isEmpty() + " Remain: "+ readQueue.size());
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Stopping readQueua empty=" + readQueue.isEmpty() + " Remain: "+ readQueue.size());
        endDatLoading(procExecutor);
    }

    public static void main(String[] args) throws Exception{
        //handle2FilesWithRunnable();
        //handle2FilesWithCallable();
        //handleMultipleFilesWithCallable();
    }

    private static void endDatLoading(ExecutorService executor){
        executor.shutdown();
        try {
            if (executor.awaitTermination(1, TimeUnit.MINUTES)) {
                System.out.println("All task finished");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Data loading finished");
    }
    private static class SimpleCallable implements Callable<Integer> {
        static AtomicInteger sno = new AtomicInteger();
        private Integer batchSize;
        private BlockingQueue<List<RoadAccident>> dataQueue;
        private String dataFileName;

        public SimpleCallable(int batchSize, BlockingQueue<List<RoadAccident>> dataQueue, String dataFileName){
            this.batchSize = batchSize;
            this.dataQueue = dataQueue;
            this.dataFileName = dataFileName;
            this.batchSize = sno.incrementAndGet();
        }

        @Override
        public Integer call() throws Exception {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000/5);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Task " + batchSize + "=> " + dataFileName + " Stage: " + Integer.toString(i) );
            }
            return batchSize;
        }
    }
}
