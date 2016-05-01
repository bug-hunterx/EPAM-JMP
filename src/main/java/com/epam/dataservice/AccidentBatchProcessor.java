package com.epam.dataservice;

import com.epam.data.RoadAccident;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bill on 16-5-1.
 */
public class AccidentBatchProcessor  implements Runnable {
    private static AtomicInteger serialNo = new AtomicInteger();
    private BlockingQueue<List<RoadAccident>> dataQueue;
    private String taskName;
    private Integer id;
    private AtomicBoolean done = new AtomicBoolean(false);

    public  AccidentBatchProcessor(BlockingQueue<List<RoadAccident>> dataQueue){
        id = serialNo.incrementAndGet();
        taskName = getClass().getSimpleName() + id;
        this.dataQueue = dataQueue;
    }

    @Override
    public void run() {
        int dataCounter = 0;
        List<RoadAccident> consumedData = null;
        try {
            while (!done.get()) {
                System.out.println(taskName + " trying to fetch data, Queue size=" + dataQueue.size());
                consumedData = dataQueue.take();
                if (consumedData != null ) {
                    if(!consumedData.isEmpty()) {
                        dataCounter += consumedData.size();
                        System.out.println(" Consumed " + dataCounter + " records from " + taskName);
                    } else {
                        System.out.println(taskName + " Get empty data");
                    }
                } else {
                    done.set(true);
                    System.out.println(taskName + " Quit");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
