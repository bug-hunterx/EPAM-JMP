package com.epam.dataservice;

import com.epam.data.AccidentsDataLoader;
import com.epam.data.RoadAccident;
import com.epam.data.TimeOfDay;
import com.epam.processor.DataProcessor;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;
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

    @Test
    public void test1() {
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
    public void test() {

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
