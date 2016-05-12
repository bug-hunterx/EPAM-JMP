package com.epam.dataservice;

import com.epam.data.RoadAccident;
import com.epam.data.RoadAccidentBuilder;
import com.epam.processor.DataProcessor;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by Nick on 03.05.2016.
 */

public class AccidentBatchProcessorRunnableTest {

    final static int QUEUE_SIZE = 6;

    BlockingQueue<List<RoadAccident>> dataQueue;
    BlockingQueue<List<RoadAccident>> resultQueue1;
    BlockingQueue<List<RoadAccident>> resultQueue2;

    private BlockingQueue<List<RoadAccident>> createData() throws ParseException {
        RoadAccident roadAccident1, roadAccident2, roadAccident3, roadAccident4;

        String dayTimeStr = "08:03:10 AM";
        String nightTimeStr = "08:03:10 PM";
        DateTimeFormatter parseFormat = new DateTimeFormatterBuilder().appendPattern("h:mm:ss a").toFormatter();
        
        LocalTime dayTime = LocalTime.parse(dayTimeStr, parseFormat);
        LocalTime nightTime = LocalTime.parse(nightTimeStr, parseFormat);

        dataQueue = new ArrayBlockingQueue<List<RoadAccident>>(QUEUE_SIZE);
        List<RoadAccident> batch1 = new ArrayList<>();

        roadAccident1 = new RoadAccidentBuilder("11").withTime(nightTime).build();
        roadAccident2 = new RoadAccidentBuilder("12").withTime(dayTime).build();
        roadAccident3 = new RoadAccidentBuilder("13").withTime(nightTime).build();
        roadAccident4 = new RoadAccidentBuilder("14").withTime(dayTime).build();

        batch1.add(roadAccident1);
        batch1.add(roadAccident2);
        batch1.add(roadAccident3);
        batch1.add(roadAccident4);

        List<RoadAccident> batch2 = new ArrayList<>();
        roadAccident1 = new RoadAccidentBuilder("21").withTime(dayTime).build();
        roadAccident2 = new RoadAccidentBuilder("22").withTime(dayTime).build();
        roadAccident3 = new RoadAccidentBuilder("23").withTime(dayTime).build();
        roadAccident4 = new RoadAccidentBuilder("24").withTime(nightTime).build();

        batch2.add(roadAccident1);
        batch2.add(roadAccident2);
        batch2.add(roadAccident3);
        batch2.add(roadAccident4);

        List<RoadAccident> batch3 = new ArrayList<>();
        roadAccident1 = new RoadAccidentBuilder("31").withTime(nightTime).build();
        roadAccident2 = new RoadAccidentBuilder("32").withTime(dayTime).build();
        roadAccident3 = new RoadAccidentBuilder("33").withTime(nightTime).build();
        roadAccident4 = new RoadAccidentBuilder("34").withTime(nightTime).build();

        batch3.add(roadAccident1);
        batch3.add(roadAccident2);
        batch3.add(roadAccident3);
        batch3.add(roadAccident4);

        List<RoadAccident> poisonedBatch = new ArrayList<>();
        poisonedBatch.add(RoadAccident.RA_POISON_PILL);

        dataQueue.add(batch1);
        dataQueue.add(batch2);
        dataQueue.add(poisonedBatch);
        dataQueue.add(batch3);
        dataQueue.add(poisonedBatch);

        return dataQueue;
    }

    @Test
    public void testRun() throws Exception {
        resultQueue1 = new ArrayBlockingQueue<List<RoadAccident>>(QUEUE_SIZE);
        resultQueue2 = new ArrayBlockingQueue<List<RoadAccident>>(QUEUE_SIZE);
        dataQueue = createData();

        CountDownLatch counter = new CountDownLatch(2);

        AccidentBatchProcessorRunnable accidentBatchProcessorRunnable = new AccidentBatchProcessorRunnable(dataQueue, resultQueue1, resultQueue2, counter);

        accidentBatchProcessorRunnable.run();

        assertEquals(resultQueue1.size(), 4);
        assertEquals(resultQueue2.size(), 4);


    }
}