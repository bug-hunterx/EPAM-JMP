package com.epam.dataservice;

import com.epam.data.RoadAccident;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * Created by rahul.mujnani on 5/3/2016.
 */
public class AccidentConcurrentProcessor {
    private static int processorQueueSize;
    private static int fixedThreadPoolSize;
    private static int processorThreadSize;
    private static int batchRunSize;
    private static int defaultThreadSize;
    private static int readerThreadSize;
    private static String dayTimeWriterCSV;
    private static String nightTimeWriterCSV;
    private static BlockingQueue<CopyOnWriteArrayList<RoadAccident>> dataQueue;
    private static BlockingQueue<CopyOnWriteArrayList<RoadAccident>> dayTimeAccidentsQueue ;
    private static BlockingQueue<CopyOnWriteArrayList<RoadAccident>> nightTimeAccidentQueue;
    private String fileDirectory = "src/main/resources/";
    ExecutorService executorService = Executors.newFixedThreadPool(fixedThreadPoolSize);
    private static Properties property = new Properties();

    public static void main(String[] args) throws Exception {
        property.load(AccidentConcurrentProcessor.class.getClassLoader().getResourceAsStream("env.properties"));
        processorQueueSize = Integer.valueOf(getConfiguration("processorQueueSize"));
        fixedThreadPoolSize = Integer.valueOf(getConfiguration("fixedThreadPoolSize"));
        processorThreadSize = Integer.valueOf(getConfiguration("processorThreadSize"));
        batchRunSize = Integer.valueOf(getConfiguration("batchRunSize"));
        readerThreadSize = Integer.valueOf(getConfiguration("readerThreadSize"));
        defaultThreadSize = Integer.valueOf(getConfiguration("defaultThreadSize"));
        processorQueueSize = Integer.valueOf(getConfiguration("processorQueueSize"));
        dayTimeWriterCSV = getConfiguration("dayTimeWriterCSV");
        nightTimeWriterCSV = getConfiguration("nightTimeWriterCSV");
        dataQueue = new ArrayBlockingQueue<CopyOnWriteArrayList<RoadAccident>>(processorQueueSize);
        dayTimeAccidentsQueue = new ArrayBlockingQueue<CopyOnWriteArrayList<RoadAccident>>(processorQueueSize);
        nightTimeAccidentQueue = new ArrayBlockingQueue<CopyOnWriteArrayList<RoadAccident>>(processorQueueSize);
        AccidentConcurrentProcessor accidentConcurrentProcessor = new AccidentConcurrentProcessor();
        accidentConcurrentProcessor.startProcessingEngine();
    }

    private static String getConfiguration(String configName) {
               return property.getProperty(configName);
           }

    /**
     * Starting Processing Engine for Read,process and Write operations
     */
    public void startProcessingEngine() throws Exception {
        accidentDataEnricher();
        accidentDataReader();
        accidentDataWriter();
    }

    /**
     * Read Csv filenames from directory
     *
     * @return ConcurrentLinkedQueue with file names
     */
    public ConcurrentLinkedQueue<String> findAccidentDataFileNames() {
        String[] inputDataFiles = new File(fileDirectory).list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.matches("DfTRoadSafety.*\\.csv");
            }
        });
        return new ConcurrentLinkedQueue<String>(Arrays.asList(inputDataFiles));
    }

    /***
     * Read RoadAccident Data records for past  years
     */
    private void accidentDataReader() {
        try {
            while (!findAccidentDataFileNames().isEmpty()
                    && readerThreadSize > defaultThreadSize) {
                String accidentDataFilename = findAccidentDataFileNames().poll();
                Future<Integer> accidentDataReader = (Future<Integer>) executorService
                        .submit((new AccidentBatchLoader(batchRunSize, dataQueue, fileDirectory + accidentDataFilename)));
                System.out.println("Thread End" + Thread.currentThread().getName() + "  Batch Size = ");
                if (accidentDataReader.get() == null) System.out.println("Reader Task terminated successfully");

                readerThreadSize--;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * Enrich RoadAccident Data records returned from Reader
     */
    private void accidentDataEnricher() {
        executorService.submit((new AccidentEnricher(dataQueue, dayTimeAccidentsQueue, nightTimeAccidentQueue)));
    }

    /***
     * Write RoadAccident Data records returned from Enricher
     */
    private void accidentDataWriter() throws IOException {
        executorService.execute(new AccidentWriter(dayTimeAccidentsQueue, dayTimeWriterCSV));
        executorService.execute(new AccidentWriter(nightTimeAccidentQueue, nightTimeWriterCSV));
        //executorService.shutdown();
    }

}
