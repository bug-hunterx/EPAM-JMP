package com.epam.dataservice;

import com.epam.data.RoadAccident;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Nick on 27.04.2016.
 */
public class AccidentBatchProcessorRunnable implements Runnable {

    BlockingQueue<List<RoadAccident>> dataQueue;
    BlockingQueue<List<RoadAccident>> resultQueue1;
    BlockingQueue<List<RoadAccident>> resultQueue2;
    CountDownLatch counter;

    public AccidentBatchProcessorRunnable(BlockingQueue<List<RoadAccident>> dataQueue, BlockingQueue<List<RoadAccident>> resultQueue1, BlockingQueue<List<RoadAccident>> resultQueue2, CountDownLatch counter) {
        this.dataQueue = dataQueue;
        this.resultQueue1 = resultQueue1;
        this.resultQueue2 = resultQueue2;
        this.counter = counter;
    }

    @Override
    public void run() {

        List<RoadAccident> currentBatch = new ArrayList<>();
        List<RoadAccident> morningBatch;
        List<RoadAccident> eveningBatch;

        try {

            while (counter.getCount() > 0 || dataQueue.size() > 0) {
                morningBatch = new ArrayList<>();
                eveningBatch = new ArrayList<>();
                currentBatch = dataQueue.take();

                if (currentBatch.contains(RoadAccident.RA_POISON_PILL)) {
                    counter.countDown();
                } else {
                    //Stupid processing here
                    for (RoadAccident current : currentBatch) {
                        String no = current.getPoliceForce();
                        current.setDayTime(DayTime.getDayTime(current.getTime()));
                        //
                        if (current.getDayTime() == DayTime.MORNING || current.getDayTime() == DayTime.AFTERNOON) {
                            morningBatch.add(current);
                        } else {
                            if (current.getDayTime() == DayTime.EVENING || current.getDayTime() == DayTime.NIGHT) {
                                eveningBatch.add(current);
                            }

                        }
                    }

                    if (morningBatch.size() > 0) {
                        resultQueue1.add(morningBatch);
                        System.out.println("Added " + morningBatch.size() + " to morning acc, total " + resultQueue1.size());
                    }

                    if (eveningBatch.size() > 0) {
                        resultQueue2.add(eveningBatch);
                        System.out.println("Added " + eveningBatch.size() + " to evening acc, total " + resultQueue2.size());
                    }

                    System.out.println("Consumer: queue size" + dataQueue.size());

                }

            }

            //Poisoning threads
            morningBatch = new ArrayList<>();

            morningBatch.add(RoadAccident.RA_POISON_PILL);

            resultQueue1.add(morningBatch);
            resultQueue2.add(morningBatch);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
