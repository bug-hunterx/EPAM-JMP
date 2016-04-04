package com.epam.processor;

import com.epam.data.AccidentsDataLoader;
import com.epam.data.RoadAccident;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.*;

/**
 * Created by Tkachi on 2016/4/4.
 */
public class DataProcessorTest {

    private static final String ACCIDENTS_CSV = "src/main/resources/DfTRoadSafety_Accidents_2009.csv";

    private static DataProcessor dataProcessor;

    @BeforeClass
    public static void loadData(){
        AccidentsDataLoader accidentsDataLoader = new AccidentsDataLoader();

        List<RoadAccident> accidents = accidentsDataLoader.loadRoadAccidents(ACCIDENTS_CSV);
        dataProcessor = new DataProcessor(accidents);
    }

    @Test
    public void should_find_by_index(){
        RoadAccident accident = dataProcessor.getAccidentByIndex("200901BS70021");
        assertNotNull(accident);
    }

    @Test
    public void should_return_null_if_no_such_index(){
        RoadAccident accident = dataProcessor.getAccidentByIndex("some-made-up-index");
        assertNull(accident);
    }

    @Test
    public void should_return_three_most_often_weathers(){
        List<String> threeTopWeathers = dataProcessor.getTopThreeWeatherCondition();
        assertThat(threeTopWeathers.size(), is(3));
        assertThat(threeTopWeathers, hasItems("Fine no high winds", "Raining no high winds", "Other"));
    }

}