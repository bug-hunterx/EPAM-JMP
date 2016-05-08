package com.epam.processor;

import com.epam.data.RoadAccident;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-config.xml")
public class AccidentsControllerIntegrationTest {

    @Autowired
    public  AccidentsController accidentsController;

    @Test
    public void testFindOne() throws Exception {
        RoadAccident roadAccident = accidentsController.findOne("200901BS70001");
        assertThat(roadAccident.getLongitude(), is(-0.201349f));

    }

    @Test
    public void testSave() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }

    @Test
    public void testGetAllMorningAccidents() throws Exception {

    }

    public void setAccidentsController(AccidentsController accidentsController) {
        this.accidentsController = accidentsController;
    }
}