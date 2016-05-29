package com.epam.dataservice;

import com.epam.springboot.AccidentsRestApplication;
import com.epam.springboot.controller.AccidentController;
import com.epam.springboot.controller.HelloController;
import com.epam.springboot.modal.RoadConditions;
import com.epam.springboot.modal.WeatherConditions;
import com.epam.springboot.repository.AccidentRepository;
import com.epam.springboot.repository.AccidentService;
import com.epam.springboot.repository.RoadConditionRepository;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
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
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by bill on 16-5-22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AccidentsRestApplication.class)   //MockServletContext
@ContextConfiguration(classes = MockServletContext.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
@WebAppConfiguration
@IntegrationTest("server.port:8090")
@DatabaseSetup("/sampleData.xml")
public class HomeWork6Test {

    @Autowired
    WeatherConditions weatherConditions;
    @Autowired
    RoadConditionRepository roadConditionRepository;
    @Autowired
    AccidentRepository repository;
    @Autowired
    AccidentService accidentService;
    static Logger log = Logger.getLogger(HomeWork6Test.class.getName());
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
//        mvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
        mvc = MockMvcBuilders.standaloneSetup(new AccidentController()).build();
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void RoadConditionRepositoryTest() {
        assertThat(roadConditionRepository.count(), equalTo(8L));
        List<RoadConditions> roadConditionsList = roadConditionRepository.findAll();
        log.info(roadConditionsList);
        assertThat(roadConditionsList.size(), equalTo(8));
    }

    @Test
    public void helloTest() throws Exception {
        String name = "Bill";
        mvc.perform(MockMvcRequestBuilders.get("/hello/" + name).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("hello, " + name)));
    }

    @Test
    public void accidentAllTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/accidents").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void accidentOneTest() throws Exception {
        assertThat(roadConditionRepository.count(), equalTo(8L));
//        List<RoadConditions> roadConditionsList = roadConditionRepository.findAll();
        mvc.perform(MockMvcRequestBuilders.get("/roads/12").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void createAccidentTest() throws Exception {
        String test_id = "200901BS70008";
        Map<String,String> param = new HashMap<>();
        param.put("Id",test_id);
        param.put("dayOfWeek","5");
        mvc.perform(MockMvcRequestBuilders.post("/accidents",param).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateAccidentTest() throws Exception {
        String test_id = "200901BS70004";
        Map<String,String> param = new HashMap<>();
        param.put("Id",test_id);
        param.put("dayOfWeek","5");
        mvc.perform(MockMvcRequestBuilders.put("/accidents/"+test_id,param).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteAccidentTest() throws Exception {
        String test_id = "200901BS70004";
        mvc.perform(MockMvcRequestBuilders.delete("/accidents/"+test_id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mvc.perform(MockMvcRequestBuilders.get("/accidents/"+test_id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}
/*
//todo add Restful services to get all accidents
//todo add Restful services to get accident by Id
//todo add Restful services to get accident by day
//todo add Restful services to create new accident (with POST and PUT - you do remember difference, right? ;) )
//todo add Restful services to update existed accident
//todo add Restful services to delete accident
//todo for put method response 404(Not Found) when encounter the exception that the id is not exist​

 */