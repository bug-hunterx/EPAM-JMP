package com.epam.dataservice;


import com.epam.data.RoadAccident;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class RoadAccidentFileReader implements Runnable {
    private ConcurrentLinkedQueue<String> filenameQueue;
    private ExecutorService executorService;
    private BlockingQueue<List<RoadAccident>> incomingQueue;
    private int batchSize;
    private String dataDir;

    public RoadAccidentFileReader(ConcurrentLinkedQueue<String> filenameQueue, ExecutorService executorService, BlockingQueue<List<RoadAccident>> incomingQueue, int batchSize, String dataDir) {
        this.filenameQueue = filenameQueue;
        this.executorService = executorService;
        this.incomingQueue = incomingQueue;
        this.batchSize = batchSize;
        this.dataDir = dataDir;
    }


    @Override
    public void run() {
        try {
            while (!filenameQueue.isEmpty()) {
                String filename = filenameQueue.poll();
                System.out.println("Start reading " + filename);
                Future<Integer> readerTask = executorService.submit(new AccidentBatchLoader(batchSize, incomingQueue, dataDir + "/" + filename));
                Integer counter = readerTask.get();
                System.out.println("Done of reading " + filename +" counter is " + counter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
}
}
