package com.epam;

import com.epam.data.AccidentsDataLoader;
import com.epam.data.AccidentsDataWriter;
import com.epam.data.RoadAccident;
import com.epam.processor.DataProcessor;

import java.io.IOException;
import java.util.List;
import java.util.Properties;


/**
 * Created by Tkachi on 2016/4/3.
 */
public class Main {

    private static final String ACCIDENTS_CSV = "src/main/resources/input/DfTRoadSafety_Accidents_2010.csv";


    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        props.load(Main.class.getClassLoader().getResourceAsStream("config.properties"));

        AccidentsDataLoader accidentsDataLoader = new AccidentsDataLoader(ACCIDENTS_CSV);

        List<RoadAccident> accidents = accidentsDataLoader.loadRoadAccidents(Integer.valueOf(props.getProperty("batch-size")));

        DataProcessor processor = new DataProcessor();

        processor.enrichWithTimeOfDay(accidents);
        processor.enrichWithForceContact(accidents);

        AccidentsDataWriter dayWriter = new AccidentsDataWriter("day.csv");
        AccidentsDataWriter nightWriter = new AccidentsDataWriter("night.csv");

        dayWriter.print(accidents);

        dayWriter.close();
        nightWriter.close();
    }

}
