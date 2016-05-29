package com.epam.springboot;

import com.epam.springboot.controller.WeatherController;
import com.epam.springboot.modal.WeatherConditions;
import com.epam.springboot.repository.WeatherConditionRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
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
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

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
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
@WebAppConfiguration
//@WebIntegrationTest
//@IntegrationTest("server.port:8090")
@DatabaseSetup("/restSampleData.xml")
//@WebMvcTest(WeatherController.class)
public class WeatherUnitTest {
//    @Autowired
//    WeatherConditionRepository weatherConditionRepository;
    @Autowired
    WeatherController weatherController;

    static Logger log = Logger.getLogger(WeatherUnitTest.class.getName());
    private MockMvc mvc;
    private WeatherConditions sampleWatherConditions = new WeatherConditions(20,"Test20 Sample");

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(weatherController).build();
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
        mvc.perform(MockMvcRequestBuilders.get("/weather/3").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Snowing")));
    }

    @Test
    // curl -X PATCH -H "Content-Type:application/json" -d '{ "code": "20", "label":"Test20" }' http://localhost:8080/weather
    public void weatherCreateTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/weather")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonBytes(sampleWatherConditions))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(sampleWatherConditions.getLabel())));
    }

    public static byte[] toJsonBytes(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return mapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
