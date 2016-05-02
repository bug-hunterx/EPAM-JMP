package com.epam.dataservice;

import com.epam.data.RoadAccident;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bill on 16-5-2.
 */
public class AccidentBatchWriter implements Runnable {
    private static AtomicInteger serialNo = new AtomicInteger();
    private String taskName = this.getClass().getSimpleName() + serialNo.incrementAndGet();
    private BlockingQueue<RoadAccident> dataQueue;
    private String dataFileName;

    public AccidentBatchWriter(BlockingQueue<RoadAccident> dataQueue, String dataFileName) {
        this.dataQueue = dataQueue;
        this.dataFileName = "src/main/resources/" + dataFileName;
    }

    @Override
    public void run() {
        Boolean done = false;
        int dataCounter = 0;

        System.out.println(taskName + " Write to file: " + dataFileName);
        try (PrintWriter csv = new PrintWriter(dataFileName)) {
            // CSV Header
            while (!done) {
                RoadAccident record = dataQueue.take();
                if(record.getAccidentId() != null) {
                    // write to file
                    csv.println(record.toCSV());
                    dataCounter++;
                } else {
                    done = true;
                    System.out.println(taskName + " Quit, Total write: " + dataCounter);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
