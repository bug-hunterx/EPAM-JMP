package com.epam.dataservice;

import com.epam.data.AccidentsDataLoader;
import com.epam.data.RoadAccident;
import com.epam.processor.DataProcessor;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
        homeWork3.buildTask();
        homeWork3.run();
    }

}
