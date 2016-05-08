package com.epam.dataservice;

import com.epam.data.RoadAccident;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by rahul.mujnani on 5/3/2016.
 */
public class AccidentWriter implements Runnable {
    private BlockingQueue<CopyOnWriteArrayList<RoadAccident>> accidentDataQueue;
    private FileWriter accidentFileWriter;


    public AccidentWriter(BlockingQueue<CopyOnWriteArrayList<RoadAccident>> accidentDataQueue,String fileWriterPath)
            throws IOException {
        new File(fileWriterPath);
        this.accidentDataQueue = accidentDataQueue;
        this.accidentFileWriter = new FileWriter(fileWriterPath);
    }

    @Override
    public void run() throws ConcurrentModificationException {
        try {
            CopyOnWriteArrayList<RoadAccident> roadAccidents = accidentDataQueue.take();
            System.out.println("Writer  Thread [" + Thread.currentThread().getId() + "] started  ");
            while (roadAccidents != null && !roadAccidents.isEmpty()) {
                for (RoadAccident roadAccident : roadAccidents) {
                    System.out.println("Writing record: [ " + Thread.currentThread().getId() + "] " + roadAccident.getAccidentId());
                    this.accidentFileWriter.write(roadAccident.toString());
                    this.accidentFileWriter.write("\n");
                }
                this.accidentFileWriter.flush();
                System.out.println("Writer Thread[" + Thread.currentThread().getId() + "] completed ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
