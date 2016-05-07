package com.epam.processor;

import com.epam.data.AccidentsDataLoader;
import com.epam.data.RoadAccident;
import com.epam.test.matchers.EndNumberMatcher;
import com.epam.test.matchers.IndexMatcher;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * Created by Tkachi on 2016/4/4.
 */
public class DataProcessorTestShort {

    private static final String ACCIDENTS_CSV = "src/main/resources/DfTRoadSafety_Accidents_2009.csv";

    private static DataProcessor dataProcessor;

    @BeforeClass
    public static void loadData(){
        AccidentsDataLoader accidentsDataLoader = new AccidentsDataLoader();

        List<RoadAccident> accidents = accidentsDataLoader.loadRoadAccidents(ACCIDENTS_CSV);
        dataProcessor = new DataProcessor(accidents);
    }

    
    //JUnit test format
    @Test
    public void should_group_by_authority_junit(){
    	Map<String, List<String>> groupedByAuthority = dataProcessor.getAccidentIdsGroupedByAuthority();
    	
    	Assert.assertEquals(groupedByAuthority.get("North Warwickshire").size(), 274);
    	Assert.assertEquals(274, groupedByAuthority.get("North Warwickshire").size());
    	Assert.assertTrue(groupedByAuthority.get("North Warwickshire").size() == 274);
    	Assert.assertEquals("Incorrect amount of data for 'North Warwickshire'",
    			groupedByAuthority.get("North Warwickshire").size(), 274);
    }

    @Test
    public void should_group_by_authority(){
        Map<String, List<String>> groupedByAuthority = dataProcessor.getAccidentIdsGroupedByAuthority();
        assertThat(groupedByAuthority.get("North Warwickshire").size(), is(274));
    }

    //CustomMatcher
    @Test
    public void should_find_by_index(){
        String index = "200901BS70021";
        RoadAccident accident = dataProcessor.getAccidentByIndex(index);
        assertNotNull(accident);
        assertThat("123ertrtt", EndNumberMatcher.isIndexEndWIthNumber());
        assertThat(accident.getAccidentId(), IndexMatcher.isIndexValid());
        assertThat(accident.getAccidentId(), equalTo(index));
    }

    @Test
    public void should_return_three_most_often_weathers(){
        List<String> threeTopWeathers = dataProcessor.getTopThreeWeatherCondition();
        assertThat(threeTopWeathers.size(), is(3));
        assertThat(threeTopWeathers, hasItems("Fine no high winds", "Raining no high winds", "Other"));
    }

    @Test
    public void should_filter_by_location(){
        Collection<RoadAccident> filteredBylocation = dataProcessor.getAccidentsByLocation(-0.2f,-0.1f,51f,52f);
        assertThat(filteredBylocation.size(), is(8199));
    }


}