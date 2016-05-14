package com.epam.processor;

import com.epam.Main;
import com.epam.data.AccidentsDataLoader;
import com.epam.data.AccidentsDataWriter;
import com.epam.entities.RoadAccident;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.epam.data.TimeOfDay.*;

/**
 * Created by Alexey on 12.05.2016.
 */
public class MultithreadedProcessor {
    private static Properties props = new Properties();
    private static final String ACCIDENTS_CSV_DIR = "src/main/resources/input";

    private static AtomicInteger filesRead = new AtomicInteger(0);
    private static AtomicInteger batchesRead = new AtomicInteger(0);
    private static AtomicInteger batchesProcessed = new AtomicInteger(0);
    private static AtomicInteger batchesWritten = new AtomicInteger(0);

    public static void process() throws IOException, InterruptedException {
        // Load properties
        props.load(Main.class.getClassLoader().getResourceAsStream("config.properties"));
        int readingQueueDepth = parseIntProp("reading-queue-depth");
        int readingThreadsNum = parseIntProp("reading-thread-num");
        int batchSize = parseIntProp("batch-size");

        int processingQueueDepth = parseIntProp("processing-queue-depth");
        int processingThreadsNum = parseIntProp("processing-thread-num");

        int writingQueueDepth = parseIntProp("writing-queue-depth");
        int writingThreadsNum = parseIntProp("writing-thread-num");

        DataProcessor processor = new DataProcessor();
        List<File> files = Arrays.asList(new File(ACCIDENTS_CSV_DIR).listFiles());

        AccidentsDataWriter dayWriter = new AccidentsDataWriter("day.csv");
        AccidentsDataWriter nightWriter = new AccidentsDataWriter("night.csv");

        // Init thread pools
        ExecutorService readingExecutor = createExecutor("Reading", readingThreadsNum, readingQueueDepth);
        ExecutorService processingExecutor = createExecutor("Processing", processingThreadsNum, processingQueueDepth);
        ExecutorService outputExecutor = createExecutor("Writing", writingThreadsNum, writingQueueDepth);

        // Start reading thread(s)
        long start = System.currentTimeMillis();
        files.stream()
                .forEach(file -> readingExecutor.execute(() -> {
                    AccidentsDataLoader accidentsDataLoader = null;
                    try {
                        accidentsDataLoader = new AccidentsDataLoader(file.getAbsolutePath());

                        while(accidentsDataLoader.hasMore()) { // Not sure if hasMore() is thread-safe
                            List<RoadAccident> batch = accidentsDataLoader.loadRoadAccidents(batchSize);
                            batchesRead.getAndIncrement();

                            processingExecutor.execute(
                                    new MultithreadedProcessor.ProcessingTask(processor, outputExecutor, batch, dayWriter, nightWriter)
                            );
                        }
                    } catch (IOException e) {
                        System.out.println("Could not open file: " + e.toString());
                    } finally {
                        filesRead.getAndIncrement();
                        synchronized (filesRead) {filesRead.notify();}
                    }
                }));

        synchronized (filesRead) {
            while(filesRead.intValue() < files.size()) {
                filesRead.wait();
            }
        }
        readingExecutor.shutdown();

        synchronized (batchesProcessed) {
            while(batchesProcessed.intValue() < batchesRead.intValue()) {
                batchesProcessed.wait();
            }
        }
        processingExecutor.shutdown();

        synchronized (batchesWritten) {
            while(batchesWritten.intValue() < batchesRead.intValue()) {
                batchesWritten.wait();
            }
        }
        outputExecutor.shutdown();

        System.out.println("Finished in " + (System.currentTimeMillis()-start));
    }

    private static int parseIntProp(String propName) {
        return Integer.valueOf(props.getProperty(propName));
    }

    private static ExecutorService createExecutor(String name, int maxPoolSize, int workQueueDepth) {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat(name)
                .build();
        return new ThreadPoolExecutor(
                0, maxPoolSize, 1, TimeUnit.MINUTES,
                new ArrayBlockingQueue<Runnable>(workQueueDepth),
                threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
    }

    private static class ProcessingTask implements Runnable {
        private DataProcessor processor;
        private ExecutorService outputExecutor;
        private List<RoadAccident> accidentsBatch;
        private AccidentsDataWriter dayWriter;
        private AccidentsDataWriter nightWriter;

        public ProcessingTask(DataProcessor processor, ExecutorService outputExecutor, List<RoadAccident> accidentsBatch,
                              AccidentsDataWriter dayWriter, AccidentsDataWriter nightWriter) {
            this.processor = processor;
            this.outputExecutor = outputExecutor;
            this.accidentsBatch = accidentsBatch;
            this.dayWriter = dayWriter;
            this.nightWriter = nightWriter;
        }

        @Override
        public void run() {
            processor.enrichWithTimeOfDay(accidentsBatch);
            processor.enrichWithForceContact(accidentsBatch);

            batchesProcessed.getAndIncrement();
            synchronized (batchesProcessed) {batchesProcessed.notify();}

            List<RoadAccident> dayAccidents = accidentsBatch.stream()
                    .filter(roadAccident -> roadAccident.getTimeOfDay() == MORNING || roadAccident.getTimeOfDay() == AFTERNOON)
                    .collect(Collectors.toList());

            List<RoadAccident> nightAccidents = accidentsBatch.stream()
                    .filter(roadAccident -> roadAccident.getTimeOfDay() == EVENING || roadAccident.getTimeOfDay() == NIGHT)
                    .collect(Collectors.toList());

            if(!dayAccidents.isEmpty()) {
                outputExecutor.execute(new MultithreadedProcessor.WritingTask(dayWriter, dayAccidents));
            }

            if(!nightAccidents.isEmpty()) {
                outputExecutor.execute(new MultithreadedProcessor.WritingTask(nightWriter, nightAccidents));
            }
        }
    }

    private static class WritingTask implements Runnable {
        private AccidentsDataWriter writer;
        private List<RoadAccident> accidentsBatch;

        public WritingTask(AccidentsDataWriter printer, List<RoadAccident> accidentsBatch) {
            this.writer = printer;
            this.accidentsBatch = accidentsBatch;
        }

        @Override
        public void run() {
            try {
                writer.print(accidentsBatch);
                batchesWritten.getAndIncrement();
                synchronized (batchesWritten) {batchesWritten.notify();}
            } catch (IOException e) {
                System.out.println("Couldn't write a batch to file: " + e.toString());
            }
        }
    }

}
