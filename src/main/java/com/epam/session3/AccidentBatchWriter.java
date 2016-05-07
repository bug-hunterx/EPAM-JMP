package com.epam.session3;

import com.epam.data.RoadAccident;
import com.epam.data.TimeOfDay;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class AccidentBatchWriter implements Runnable{

    private BlockingQueue<List<RoadAccident>> inboundQueue;
    private FileWriter outputWriter;

    public AccidentBatchWriter(BlockingQueue<List<RoadAccident>> inboundQueue,
                               String destination) throws IOException{
        new File(destination).deleteOnExit();
        this.inboundQueue = inboundQueue;
        this.outputWriter = new FileWriter(destination);
    }

    @Override
    public void run() {
        try {
            long counter = 0;
            while(true){
                List<RoadAccident> roadAccidents = inboundQueue.take();
                counter += roadAccidents.size();
                for(RoadAccident roadAccident : roadAccidents){
                    this.outputWriter.write(roadAccident.toString());
                    this.outputWriter.write("\n");
                }
                this.outputWriter.flush();
                System.out.println("Writer Thread[" + Thread.currentThread().getId() + "] completed writing " + counter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
