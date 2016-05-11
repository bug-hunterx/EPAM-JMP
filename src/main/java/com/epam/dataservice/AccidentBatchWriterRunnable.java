package com.epam.dataservice;

import com.epam.data.RoadAccident;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.FileWriter;

import java.io.IOException;
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

            while (!batch.contains(RoadAccident.RA_POISON_PILL)) {

                for (RoadAccident accident : batch) {
                    roadAccidentPrinter.printRecord(accident.toListString());
                }
                System.out.println("Flushed " + batch.size() + " to file " + dataFileName);
                roadAccidentPrinter.flush();
                batch = getNextBatch();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                roadAccidentPrinter.close();
                System.out.println("Closed " + dataFileName + " for writing");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<RoadAccident> getNextBatch() throws InterruptedException {
        return dataQueue.take();
    }

}
