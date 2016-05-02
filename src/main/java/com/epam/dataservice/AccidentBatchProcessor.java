package com.epam.dataservice;

import com.epam.data.RoadAccident;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bill on 16-5-1.
 */
public class AccidentBatchProcessor  implements Runnable {
    private static AtomicInteger serialNo = new AtomicInteger();
// For record Enrichment
    private static PoliceForceService policeForceService = new PoliceForceService();
    private static ExecutorService enrichExecutor = Executors.newFixedThreadPool(HomeWork3.MAX_ENRICH_THREADS);
    private CompletionService<RoadAccident> enrichCompletionService = new ExecutorCompletionService<RoadAccident>(enrichExecutor);

    private String taskName = getClass().getSimpleName() + serialNo.incrementAndGet();
    private BlockingQueue<List<RoadAccident>> readQueue;
    private List<BlockingQueue<RoadAccident>> writeQueues;

    public  AccidentBatchProcessor(BlockingQueue<List<RoadAccident>> readQueue, List<BlockingQueue<RoadAccident>> writeQueues){
        this.readQueue = readQueue;
        this.writeQueues = writeQueues;
    }

    @Override
    public void run() {
        int dataCounter = 0;
        List<RoadAccident> consumedData = null;
        Boolean done = false;
        try {
            while (!done) {
                consumedData = readQueue.take();
                if (consumedData != null ) {
                    if(!consumedData.isEmpty()) {
                        dataCounter += consumedData.size();
                        System.out.println(" Consumed " + dataCounter + " records from " + taskName);
                        enrichRoadAccident(consumedData);
                    } else {
                        done = true;
                        System.out.println(taskName + " Get empty data," + " Quit");
                    }
                } else {
                    System.out.println(taskName + " Abort");
                    break;
                }
            }
//            System.out.println(taskName + " Finished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void enrichRoadAccident(List<RoadAccident> consumedData) {
        for (RoadAccident record : consumedData) {
            enrichCompletionService.submit(new enrichRecord(record));
        }
        for (int i = 0; i < consumedData.size(); i++) {
            try {
                RoadAccident record = enrichCompletionService.take().get();
                int index = record.getTimeOfDay().getCategory();
                writeQueues.get(index).put(record);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private class enrichRecord implements Callable<RoadAccident> {
        private RoadAccident record;
        public enrichRecord(RoadAccident record) {
            this.record = record;
        }

        @Override
        public RoadAccident call() {
            String contactNo = null;
            try {
                contactNo = policeForceService.getContactNo(record.getPoliceForce());
            } catch (RuntimeException e) {
                System.out.println("Catch policeForceService.getContactNo exception");
                e.printStackTrace();
                contactNo = null;
            }
            record.setForceContact(contactNo);
            record.setTimeOfDay(record.getTime());
//            System.out.println(taskName + "  policeForceService=" + contactNo);
            return record;
        }
    }

}
