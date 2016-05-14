package com.epam.processor;

import com.epam.data.AccidentsDataLoader;
import com.epam.entities.RoadAccident;
import com.epam.entities.RoadAccidentBuilder;
import com.epam.service.PoliceForceService;
import com.google.common.collect.Multimap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.epam.data.TimeOfDay.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by Tkachi on 2016/4/4.
 */
@RunWith(MockitoJUnitRunner.class)
public class DataProcessorTest {

    private static final String ACCIDENTS_CSV = "src/main/resources/input/DfTRoadSafety_Accidents_2009.csv";

    private static List<RoadAccident> accidents;

    @Mock
    private static PoliceForceService policeForceService;

    @InjectMocks
    private static DataProcessor dataProcessor;

    @BeforeClass
    public static void setUp() throws IOException {
        AccidentsDataLoader accidentsDataLoader = new AccidentsDataLoader(ACCIDENTS_CSV);
        accidents = accidentsDataLoader.loadRoadAccidents();
    }

    @Test
    public void should_find_by_index7(){
        String index = "200901BS70021";
        RoadAccident accident = dataProcessor.getAccidentByIndex7(accidents, index);
        assertNotNull(accident);
        assertThat(accident.getAccidentId(), equalTo(index));
    }

    @Test
    public void should_find_by_index(){
        String index = "200901BS70021";
        RoadAccident accident = dataProcessor.getAccidentByIndex(accidents, index);
        assertNotNull(accident);
        assertThat(accident.getAccidentId(), equalTo(index));
    }

    @Test
    public void should_return_null_if_no_such_index(){
        RoadAccident accident = dataProcessor.getAccidentByIndex(accidents, "some-made-up-index");
        assertNull(accident);
    }

    @Test
    public void should_count_by_road_conditions7(){
        Map<String, Long> countByRoadConditions = dataProcessor.getCountByRoadSurfaceCondition7(accidents);
        assertThat(countByRoadConditions.get("Dry"), is(110277L));
    }

    @Test
    public void should_count_by_road_conditions(){
        Map<String, Long> countByRoadConditions = dataProcessor.getCountByRoadSurfaceCondition(accidents);
        assertThat(countByRoadConditions.get("Dry"), is(110277L));
    }

    @Test
    public void should_return_three_most_often_weathers7(){
        List<String> threeTopWeathers = dataProcessor.getTopThreeWeatherCondition7(accidents);
        assertThat(threeTopWeathers.size(), is(3));
        assertThat(threeTopWeathers, hasItems("Fine no high winds", "Raining no high winds", "Other"));
    }

    @Test
    public void should_return_three_most_often_weathers(){
        List<String> threeTopWeathers = dataProcessor.getTopThreeWeatherCondition(accidents);
        assertThat(threeTopWeathers.size(), is(3));
        assertThat(threeTopWeathers, hasItems("Fine no high winds", "Raining no high winds", "Other"));
    }

    @Test
    public void should_filter_by_location7(){
        Collection<RoadAccident> filteredBylocation = dataProcessor.getAccidentsByLocation7(accidents, -0.2f,-0.1f,51f,52f);
        assertThat(filteredBylocation.size(), is(8199));
    }

    @Test
    public void should_filter_by_location(){
        Collection<RoadAccident> filteredBylocation = dataProcessor.getAccidentsByLocation(accidents, -0.2f,-0.1f,51f,52f);
        assertThat(filteredBylocation.size(), is(8199));
    }

    @Test
    public void should_group_by_authority7(){
        Multimap<String, String> groupedByAuthority = dataProcessor.getAccidentIdsGroupedByAuthority7(accidents);
        assertThat(groupedByAuthority.get("North Warwickshire").size(), is(274));
    }

    @Test
    public void should_group_by_authority(){
        Map<String, List<String>> groupedByAuthority = dataProcessor.getAccidentIdsGroupedByAuthority(accidents);
        assertThat(groupedByAuthority.get("North Warwickshire").size(), is(274));
    }

    @Test
    public void should_define_time_of_day() {
        // given
        List<RoadAccident> accidents = Arrays.asList(new RoadAccident[]{
           new RoadAccidentBuilder("").withTime(LocalTime.parse("09:30")).build(),
           new RoadAccidentBuilder("").withTime(LocalTime.parse("12:00")).build(),
           new RoadAccidentBuilder("").withTime(LocalTime.parse("23:59")).build(),
           new RoadAccidentBuilder("").withTime(LocalTime.parse("00:00")).build()
        });

        // when
        dataProcessor.enrichWithTimeOfDay(accidents);

        // then
        assertThat(accidents.get(0).getTimeOfDay(), equalTo(MORNING));
        assertThat(accidents.get(1).getTimeOfDay(), equalTo(AFTERNOON));
        assertThat(accidents.get(2).getTimeOfDay(), equalTo(EVENING));
        assertThat(accidents.get(3).getTimeOfDay(), equalTo(NIGHT));
    }

    @Test
    public void should_enrich_with_contacts() throws Exception {
        String mockContact = "Mocked phone number";
        // given
        when(policeForceService.getContactNo(any())).thenReturn(mockContact);

        // when
        dataProcessor.enrichWithForceContact(accidents);

        // then
        assertThat(accidents.get(0).getForceContact(), equalTo(mockContact));
    }


}