package com.epam.session3;

import com.epam.data.RoadAccident;
import com.epam.dataservice.AccidentBatchLoader;
import com.epam.dataservice.PoliceForceService;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class Session3Processor {

    private PoliceForceService policeForceService;
    private ExecutorService executorService;

    private ConcurrentLinkedQueue<String> filenameQueue;
    private BlockingQueue<List<RoadAccident>> incomingQueue;
    private BlockingQueue<List<RoadAccident>> daytimeOutgoingQueue;
    private BlockingQueue<List<RoadAccident>> nighttimeOutgoingQueue;

    private int sizeOfReaderThreads;
    private int sizeOfEnricherThreads;
    private int batchSize;
    private String dataDir;

    public Session3Processor(int sizeOfIncomingQueue,
                             int sizeOfOutgoingQueue,
                             int sizeOfThreadPool,
                             int sizeOfReaderThreads,
                             int sizeOfEnricherThreads,
                             int sizeOfBatch,
                             String dataDir){
        this.sizeOfReaderThreads = sizeOfReaderThreads;
        this.sizeOfEnricherThreads = sizeOfEnricherThreads;
        this.batchSize = sizeOfBatch;
        this.dataDir = dataDir;
        this.policeForceService = new PoliceForceService();
        this.filenameQueue = new ConcurrentLinkedQueue<String>(detectFiles());
        this.incomingQueue = new ArrayBlockingQueue<List<RoadAccident>>(sizeOfIncomingQueue);
        this.daytimeOutgoingQueue = new ArrayBlockingQueue<List<RoadAccident>>(sizeOfOutgoingQueue);
        this.nighttimeOutgoingQueue = new ArrayBlockingQueue<List<RoadAccident>>(sizeOfOutgoingQueue);
        this.executorService = Executors.newFixedThreadPool(sizeOfThreadPool);

    }

    public void startProcess() throws Exception{
        startWriters();
        startEnrichers();
        startReaders();
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }

    private Collection<String> detectFiles(){
        String[] files = new File(this.dataDir).list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.matches("DfTRoadSafety_Accidents_[0-9]{4}\\.csv");
            }
        });
        return Arrays.asList(files);
    }

    private void startWriters() throws IOException {
        executorService.execute(new AccidentBatchWriter(daytimeOutgoingQueue, dataDir+"/DaytimeAccidents.csv"));
        executorService.execute(new AccidentBatchWriter(nighttimeOutgoingQueue, dataDir+"/NighttimeAccidents.csv"));
    }

    private void startEnrichers(){
        for(int i=0;i<sizeOfEnricherThreads;i++){
            executorService.execute(new AccidentBatchEnricher(policeForceService, incomingQueue, daytimeOutgoingQueue, nighttimeOutgoingQueue));
        }
    }

    private void startReaders() {
        try {
            while (!filenameQueue.isEmpty()) {
                String filename = filenameQueue.poll();
                System.out.println("Start reading " + filename);
                Future<Integer> readerTask = executorService.submit(new AccidentBatchLoader(batchSize, incomingQueue, dataDir + "/" + filename));
                Integer counter = readerTask.get();
                System.out.println("Done of reading " + filename + " counter is " + counter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{
        String dataDir = "src/main/resources";
        int sizeOfIncomingQueue = 2;
        int sizeOfOutgoingQueue = 2;
        int sizeOfThreadPool = 20;
        int sizeOfReaderThreads = 3;
        int sizeOfEnricherThreads = 5;
        int sizeOfBatch = 40000;
        Session3Processor processor = new Session3Processor(
                sizeOfIncomingQueue,
                sizeOfOutgoingQueue,
                sizeOfThreadPool,
                sizeOfReaderThreads,
                sizeOfEnricherThreads,
                sizeOfBatch,
                dataDir);
        processor.startProcess();
    }
}
