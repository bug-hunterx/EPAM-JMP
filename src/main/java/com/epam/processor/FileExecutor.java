package com.epam.processor;


import com.epam.data.RoadAccident;
import com.epam.dataservice.*;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class FileExecutor {

    private PoliceForceService policeForceService;
    private ExecutorService executorService;
    private int readerThreadCapcity;
    private int enricherThreadCapcity;
    private int batchCapcitye;
    private String dataDir;
    private ConcurrentLinkedQueue<String> filenameQueue;
    private BlockingQueue<List<RoadAccident>> incomingQueue;
    private BlockingQueue<List<RoadAccident>> daytimeOutgoingQueue;
    private BlockingQueue<List<RoadAccident>> nighttimeOutgoingQueue;

    public FileExecutor(int sizeOfIncomingQueue,
                        int sizeOfOutgoingQueue,
                        int sizeOfThreadPool,
                        int sizeOfReaderThreads,
                        int sizeOfEnricherThreads,
                        int sizeOfBatch,
                        String dataDir) {
        this.dataDir = dataDir;
        this.executorService = Executors.newFixedThreadPool(sizeOfThreadPool);
        this.daytimeOutgoingQueue = new ArrayBlockingQueue<List<RoadAccident>>(sizeOfOutgoingQueue);
        this.nighttimeOutgoingQueue = new ArrayBlockingQueue<List<RoadAccident>>(sizeOfOutgoingQueue);
        this.filenameQueue = new ConcurrentLinkedQueue<String>(findAccidentFiles());
        this.incomingQueue = new ArrayBlockingQueue<List<RoadAccident>>(sizeOfIncomingQueue);
        this.readerThreadCapcity = sizeOfReaderThreads;
        this.enricherThreadCapcity = sizeOfEnricherThreads;
        this.batchCapcitye = sizeOfBatch;
        this.policeForceService = new PoliceForceService();
    }

    public void lanuchThread() throws Exception {
        startWriteFilesForDaytimeAccident();
        startWriteFilesForNighttimeAccident();
        startProcess();
        startReadFiles();
    }

    private List<String> findAccidentFiles(){
        File files = new File(dataDir);
        String[] filesNanme = files.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.matches("DfTRoadSafety_Accidents_[0-9]{4}\\.csv");
            }
        });
        return Arrays.asList(filesNanme);
    }

    private void startProcess() {
        for(int i=0;i<enricherThreadCapcity;i++){
            executorService.execute(new AccidentBatchEnricher(policeForceService, incomingQueue, daytimeOutgoingQueue, nighttimeOutgoingQueue));
        }
    }

    private void startReadFiles() {
        for(int i=0;i<readerThreadCapcity;i++){
            new Thread(new RoadAccidentFileReader(filenameQueue, executorService, incomingQueue, batchCapcitye, dataDir)).start();
        }
    }

    private void startWriteFilesForDaytimeAccident() throws IOException {
        executorService.execute(new RoadAccidentFileWriter(daytimeOutgoingQueue, dataDir+"/DaytimeAccidents.csv"));
    }

    private void startWriteFilesForNighttimeAccident() throws IOException {
        executorService.execute(new RoadAccidentFileWriter(nighttimeOutgoingQueue, dataDir+"/NighttimeAccidents.csv"));
    }

    public static void main(String args[]) throws Exception {
        String dataDir = "src/main/resources";
        int sizeOfIncomingQueue = 2;
        int sizeOfOutgoingQueue = 2;
        int sizeOfThreadPool = 20;
        int sizeOfReaderThreads = 3;
        int sizeOfEnricherThreads = 5;
        int sizeOfBatch = 40000;
        FileExecutor processor = new FileExecutor(
                sizeOfIncomingQueue,
                sizeOfOutgoingQueue,
                sizeOfThreadPool,
                sizeOfReaderThreads,
                sizeOfEnricherThreads,
                sizeOfBatch,
                dataDir);
        processor.lanuchThread();
    }
}
