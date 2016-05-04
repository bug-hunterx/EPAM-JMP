package com.epam.dataservice;

import com.epam.data.AccidentsDataLoader;
import com.epam.data.RoadAccident;
import com.epam.data.RoadAccidentBuilder;
import com.epam.data.TimeOfDay;
import com.epam.processor.DataProcessor;
import org.hamcrest.core.AnyOf;
import org.hamcrest.core.StringStartsWith;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

import static org.hamcrest.core.Is.is;
import org.hamcrest.core.IsInstanceOf;
import org.hamcrest.core.IsNull;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.*;
import static org.junit.Assert.*;


/**
 * Created by bill on 16-5-1.
 */
public class HomeWork3Test {
    public static LinkedList<String> inputFiles = new LinkedList<>();
    private static HomeWork3 homeWork3;

    @BeforeClass
    public static void loadData(){
        for (int i = 2009; i <= 2012 ; i++) {
            inputFiles.add("src/main/resources/DfTRoadSafety_Accidents_" + Integer.toString(i) +".csv");
        }
        homeWork3 = new HomeWork3(inputFiles);
    }

    private RoadAccident createRoadAccident(String id, String time) {
        RoadAccident roadAccident;
        if (id == null) {
            roadAccident = new RoadAccidentBuilder(null).build();
        } else {
            roadAccident = new RoadAccidentBuilder(id)
                    .withTime(LocalTime.parse(time, DateTimeFormatter.ISO_LOCAL_TIME))
                    .build();
        }
        return roadAccident;
    }

    @Test
    public void testAccidentBatchProcessor() throws Exception {
        String[] times = {"00:00:00", "06:00:00", "17:59:59", "23:59:59"};
        BlockingQueue<List<RoadAccident>> readQueue = new ArrayBlockingQueue<List<RoadAccident>>(2);
        List<BlockingQueue<RoadAccident>> writeQueues = new ArrayList<>(2);
        for (int i = 0; i < 2; i++) {
            writeQueues.add( new ArrayBlockingQueue<RoadAccident>(2) );
        }
        ExecutorService procExecutor = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 2; i++) {
            procExecutor.submit(new AccidentBatchProcessor(readQueue, writeQueues));
        }
        for (String time : times ) {
            List<RoadAccident> roadAccidentList = new ArrayList<RoadAccident>();
            roadAccidentList.add(createRoadAccident(time, time));
            readQueue.put(roadAccidentList);
        }
//        TimeUnit.SECONDS.sleep(1);

        RoadAccident roadAccident;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                BlockingQueue<RoadAccident> writeQueue = writeQueues.get(j);
                System.out.println("Q" + j + " Size=" + writeQueue.size());
                roadAccident = writeQueue.take();
                System.out.println(roadAccident.toCSV());
                assertThat(roadAccident, instanceOf(RoadAccident.class));
/*
                if (j==0) {
                    assertThat(roadAccident.getTimeOfDay(), isIn(Arrays.asList(TimeOfDay.EVENING, TimeOfDay.NIGHT));
                } else {
                    assertThat(roadAccident.getTimeOfDay(), isIn(Arrays.asList(TimeOfDay.MORNING, TimeOfDay.AFTERNOON));
                }
*/
                assertThat(roadAccident.getTimeOfDay().getCategory(), equalTo(j));
                assertThat(roadAccident.getForceContact(), StringStartsWith.startsWith("13163862"));
            }
//            TimeUnit.SECONDS.sleep(1);
        }
        for (int i = 0; i < 2; i++) {
            assertThat(writeQueues.get(i).isEmpty(), equalTo(true));
        }
        assertThat(readQueue.isEmpty(), equalTo(true));
        procExecutor.shutdown();
    }

    @Test
    public void testMain() {
        homeWork3.run();
    }

    @Test
    public void testEnum() {
        TimeOfDay timeOfDay;
        timeOfDay = TimeOfDay.getTimeOfDay(LocalTime.parse("09:56:00", DateTimeFormatter.ISO_LOCAL_TIME));
        assertThat(timeOfDay, equalTo(TimeOfDay.MORNING));
        assertThat(timeOfDay.getCategory(), equalTo(1));
        timeOfDay = TimeOfDay.getTimeOfDay(LocalTime.parse("03:56:00", DateTimeFormatter.ISO_LOCAL_TIME));
        assertThat(timeOfDay, equalTo(TimeOfDay.NIGHT));
        assertThat(timeOfDay.getCategory(), equalTo(0));
    }

    @Test
    public void testPoliceForceService() {
        PoliceForceService policeForceService = new PoliceForceService();
        assertThat(policeForceService.getContactNo("Norfolk"), equalTo("1316386236"));
        assertThat(policeForceService.getContactNo("London"), equalTo("13163862"));
        assertThat(policeForceService.getContactNo(""), equalTo("13163862"));
        assertThat(policeForceService.getContactNo(null), equalTo("13163862"));
//        assertThat(policeForceService.getContactNo("London"), IsNull.nullValue());
    }

}
/*
    Read accident files for different years and then we need to populate following 2 fields for each record.
        1. ForceContact -- Use PoliceForceService.getContactNo(String forceName) to get contact no.
        Assume you dont have control on this method and this method can be time consuming or may even block randomly.
        Assume this can be a I/O service for example some web service

        2. TimeosDay -- Add 1 field DayTime which can have 4 different values as below based on accident time.
        MORNING - 6 am to 12 pm
        AFTERNOON - 12 pm to 6 pm
        EVENING - 6 pm to 12 am
        NIGHT - 12 am to 6 am

        And then we need to generate 2 files
        1. File which contains all day time accdent data for the years (MORNING, AFTERNOON)  -- DaytimeAccidents.csv
        2. File which contains all night time accdent data for the years (EVENING, NIGHT)    -- NighttimeAccidents.csv
*/
