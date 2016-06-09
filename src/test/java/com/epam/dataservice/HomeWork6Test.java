package com.epam.dataservice;

import com.epam.springboot.AccidentsRestApplication;
import com.epam.springboot.WeatherUnitTest;
import com.epam.springboot.controller.AccidentController;
import com.epam.springboot.modal.Accidents;
import com.epam.springboot.modal.RoadConditions;
import com.epam.springboot.modal.WeatherConditions;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isEmptyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by bill on 16-5-22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AccidentsRestApplication.class)   //MockServletContext
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
@WebAppConfiguration
@DatabaseSetup("/sampleData.xml")
public class HomeWork6Test {

    @Autowired
    AccidentController accidentController;

    static Logger log = Logger.getLogger(HomeWork6Test.class.getName());
    private final static String REST_URL = "/accidents";
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(accidentController).build();
    }

    @Test
    public void contextLoads() {
    }

/*
    @Test
    public void RoadConditionRepositoryTest() {
        assertThat(roadConditionRepository.count(), equalTo(8L));
        List<RoadConditions> roadConditionsList = roadConditionRepository.findAll();
        log.info(roadConditionsList);
        assertThat(roadConditionsList.size(), equalTo(8));
    }
*/

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
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("2009-01")));
    }

    @Test
    public void accidentOneTest() throws Exception {
        String test_id = "200901BS70016";
        mvc.perform(MockMvcRequestBuilders.get(REST_URL + "/" + test_id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(test_id)));
    }

    @Test
    public void createAccidentTest() throws Exception {
        //"{"id":"200901BS70001","weatherConditions":{"code":6,"label":"Snowing + high winds"},"roadSurfaceConditions":{"code":3,"label":"Snow"},"date":"2009-01-01","time":"15:11","longitude":-0.201349,"latitude":51.512273,"policeForce":1,"severity":2,"numberOfVehicles":2,"numberOfCasualties":1,"dayOfWeek":5,"districtAuthority":12,"lightConditions":1}"
        String test_id = "201606BS70008";
        Accidents accidents = new Accidents(test_id, new WeatherConditions(6), new RoadConditions(3), new java.util.Date());
        mvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(WeatherUnitTest.toJsonBytes(accidents))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(accidents.getId())));
        mvc.perform(MockMvcRequestBuilders.get(REST_URL + "/" + test_id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Snow")));
    }

    @Test
    public void updateAccidentTest() throws Exception {
        String test_id = "200901BS70004";
        String url = REST_URL + "/" + test_id;
        String json = "{\"id\":\"" + test_id + "\",\"dayOfWeek\":5}";
        Accidents accidents = new Accidents(test_id);
        mvc.perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(accidents.getId())));
        mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"dayOfWeek\":5")));
    }

    @Test
    public void deleteAccidentTest() throws Exception {
        String test_id = "200901BS70004";
        String url = REST_URL + "/" + test_id;
        mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(test_id)));
        mvc.perform(MockMvcRequestBuilders.delete(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(isEmptyString()));
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