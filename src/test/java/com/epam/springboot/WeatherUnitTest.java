package com.epam.springboot;

import com.epam.springboot.controller.WeatherController;
import com.epam.springboot.modal.WeatherConditions;
import com.epam.springboot.repository.WeatherConditionRepository;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by bill on 16-5-29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AccidentsRestApplication.class)   //MockServletContext
//@ContextConfiguration(classes = MockServletContext.class)
//@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
@WebAppConfiguration
//@WebIntegrationTest
//@IntegrationTest("server.port:8090")
//@DatabaseSetup("/restSampleData.xml")
//@WebMvcTest(WeatherController.class)
public class WeatherUnitTest {
    @Autowired
    WeatherConditionRepository weatherConditionRepository;
    @Autowired
    WeatherController weatherController;

    static Logger log = Logger.getLogger(WeatherUnitTest.class.getName());
    private MockMvc mvc;
    private WeatherConditions sampleWatherConditions = new WeatherConditions(20,"Test20 Sample");

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(weatherController).build();
        mvc.perform(MockMvcRequestBuilders.get("/init").accept(MediaType.APPLICATION_JSON));
//        weatherConditionRepository.saveAndFlush(new WeatherConditions(1,"First"));
//        weatherConditionRepository.saveAndFlush(sampleWatherConditions);
//        log.info(weatherConditionRepository.count());
//        log.info(weatherConditionRepository.findAll());
    }

    @Test
    public void simpleWeatherTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/test").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test20")));
    }

    @Test
    public void weatherAllTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/weather").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void weatherOneTest() throws Exception {
//        assertThat(weatherConditionRepository.count(), equalTo(2L));
//        List<RoadConditions> roadConditionsList = roadConditionRepository.findAll();
        mvc.perform(MockMvcRequestBuilders.get("/weather/3").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
