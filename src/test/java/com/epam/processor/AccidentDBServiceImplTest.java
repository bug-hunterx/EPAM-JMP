package com.epam.processor;

import com.epam.data.RoadAccident;
import com.epam.data.TimeOfDay;
import com.epam.db.HsqlInit;
import com.epam.entities.Accident;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by rahul.mujnani on 5/9/2016.
 */

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-context.xml")*/
public class AccidentDBServiceImplTest {

    HsqlInit hsqlInit;
    Connection connection;
    public AccidentDBServiceImpl accidentDBServiceImpl = null;

    @Before
    public void init() {
        hsqlInit = new HsqlInit();
        connection = hsqlInit.initDatabase();

        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:/spring-config.xml");
        accidentDBServiceImpl = (AccidentDBServiceImpl) context.getBean("accidentDBServiceImpl");
       // hsqlInit = (HsqlInit) context.getBean("hsqldb.connection");
    }


    @Test
    public void testFindOne() throws Exception {
        Accident roadAccident = accidentDBServiceImpl.findOne("200901BS70001");
        assertThat(roadAccident.getLongitude(), is(-0.201349f));
        assertThat(roadAccident.getLocalAuthorityDistrict(), is(12));
    }

    @Test
    public void test_find_roadAccidents() throws Exception {
        List<RoadAccident> list = new ArrayList<RoadAccident>();
        Iterable<RoadAccident> accidentByRoadCondition = accidentDBServiceImpl.getAllAccidentsByRoadCondition(1);
        Iterator iteratorRoadSurefaceAccidents = accidentByRoadCondition.iterator();
        while (iteratorRoadSurefaceAccidents.hasNext()) {
            RoadAccident roadAccidentdRoadSurface = (RoadAccident)iteratorRoadSurefaceAccidents.next();
            assertEquals(roadAccidentdRoadSurface.getRoadSurfaceConditions(), "Dry");
            break;
        }
        assertNotNull(accidentByRoadCondition);
    }

    @Test
    public void test_find_WeatherCondition() throws Exception {
        Iterable<RoadAccident> accidentByWeatherCondition = accidentDBServiceImpl.getAllAccidentsByWeatherConditionAndYear(1, "2009");
        Iterator iteratorWeatherCondition = accidentByWeatherCondition.iterator();
        while (iteratorWeatherCondition.hasNext()) {
            RoadAccident roadAccidentdWeather = (RoadAccident)iteratorWeatherCondition.next();
            assertEquals(roadAccidentdWeather.getWeatherConditions(), "Fine no high winds");
            break;
        }
        assertNotNull(iteratorWeatherCondition);
    }

    @Test
    public void test_accidentsByDate_Update() throws Exception {
        Boolean updateFlag = false;
        Iterable<RoadAccident> accidentByDate = accidentDBServiceImpl.getAllAccidentsByDate(new Date(2015,05,15));
        Iterator iteratorUpdateAccidents = accidentByDate.iterator();
        while (iteratorUpdateAccidents.hasNext()) {
            RoadAccident roadAccidentdByDate = (RoadAccident)iteratorUpdateAccidents.next();
            assertEquals(roadAccidentdByDate.getAccidentId(), "200901BS70001");
            updateFlag=accidentDBServiceImpl.update(roadAccidentdByDate);
        }
        assertThat(updateFlag, is(true));
    }
}
