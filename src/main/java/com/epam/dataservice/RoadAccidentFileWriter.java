package com.epam.dataservice;


import com.epam.data.RoadAccident;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class RoadAccidentFileWriter implements Runnable {

    private BlockingQueue<List<RoadAccident>> inboundQueue;
    private FileWriter outputWriter;

    public RoadAccidentFileWriter(BlockingQueue<List<RoadAccident>> inboundQueue, String fileDestination) throws IOException {
        new File(fileDestination).deleteOnExit();
        this.inboundQueue = inboundQueue;
        this.outputWriter = new FileWriter(fileDestination);
    }

    @Override
    public void run() {
        try {
            long accidentCounter = 0;
            while(true){
                List<RoadAccident> roadAccidents = inboundQueue.take();
                accidentCounter += roadAccidents.size();
                for(RoadAccident roadAccident : roadAccidents){
                    outputWriter.write("roadeAccident: "+roadAccident.toString());
                    System.out.println(roadAccident.toString());
                    outputWriter.write("\n");
                }
                outputWriter.flush();
                System.out.println("Writed accident ["+ accidentCounter +"], into file");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
