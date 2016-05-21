package com.epam.dbservice;

import com.epam.entities.RoadAccident;
import com.epam.it.IntegrationTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Alexey on 21.05.2016.
 */
@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-config.xml")
public class AccidentDBServiceImplIT {

    @Autowired
    AccidentDBServiceImpl accidentService;

    @Test
    public void testFindOne() {
        String id = "200901BS70001";
        RoadAccident accident = accidentService.findOne(id);

        assertThat(accident.getAccidentId(), is(equalTo(id)));
    }

    @Test
    public void testFindByWeatherConditionsAndYear() {
        List<RoadAccident> accidents = accidentService.getAllAccidentsByWeatherConditionAndYear("Fine no high winds", 2009);

        assertThat(accidents.size(), is(equalTo(81168))); // Checked with Excel first (really:))
    }

    @Test
    public void testFindByRoadConditions() {
        List<RoadAccident> accidents = accidentService.getAllAccidentsByRoadCondition("Dry");

        assertThat(accidents.size(), is(equalTo(70315)));
    }

    @Test
    public void testFindByDate() {
        List<RoadAccident> accidents = accidentService.getAllAccidentsByDate(
                Date.from(LocalDate.of(2009, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant())
        );

        assertThat(accidents.size(), is(equalTo(155)));
    }

    @Test
    public void testUpdate() throws Exception {
        String id = "200901BS70002";
        Date now = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

        RoadAccident accidentForUpdate = accidentService.findOne(id);
        accidentForUpdate.setDate(now);

        accidentService.update(accidentForUpdate);

        RoadAccident updatedAccident = accidentService.findOne(id);

        assertThat(updatedAccident.getDate(), is(equalTo(now)));
    }
}
