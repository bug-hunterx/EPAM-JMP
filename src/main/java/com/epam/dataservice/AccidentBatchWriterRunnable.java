package com.epam.dataservice;

import com.epam.data.RoadAccident;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.FileWriter;

import java.util.List;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Nick on 27.04.2016.
 */
public class AccidentBatchWriterRunnable implements Runnable {

    private Integer batchSize;
    private BlockingQueue<List<RoadAccident>> dataQueue;
    private String dataFileName;
    private CSVPrinter roadAccidentPrinter;

    public AccidentBatchWriterRunnable(BlockingQueue<List<RoadAccident>> dataQueue, String dataFileName) {
        this.dataQueue = dataQueue;
        this.dataFileName = dataFileName;
    }

    @Override
    public void run() {
        try {
            List<RoadAccident> batch = getNextBatch();
            BufferedWriter out = new BufferedWriter(new FileWriter(dataFileName));
            roadAccidentPrinter = new CSVPrinter(out, CSVFormat.EXCEL.withHeader());
            System.out.println("Opened " + dataFileName + " for writing");

            while (batch != null && !batch.isEmpty()) {

                    for(RoadAccident accident: batch) {
                        roadAccidentPrinter.printRecord(accident.toListString());
                    }
                    System.out.println("Flushed " + batch.size() + " to file " + dataFileName);
                roadAccidentPrinter.flush();
                    batch = getNextBatch();
            }
            System.out.println("Closed " + dataFileName + " for writing");
            roadAccidentPrinter.close();

        } catch (Exception e) {

        }
    }

    private List<RoadAccident> getNextBatch() throws InterruptedException
    {
        return dataQueue.take();
    }

}
