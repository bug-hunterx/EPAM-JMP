package com.epam.processor;

import com.epam.AccidentTrackingMainApplication;
import com.epam.db.HsqlInit;
import com.epam.entities.Accident;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.sql.Connection;
//import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by rahul.mujnani on 5/21/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = AccidentTrackingMainApplication.class)
@WebAppConfiguration
@ContextConfiguration("classpath*:/spring-config.xml")
public class AccidentTrackingMainApplicationTest {

    private MockMvc mvc;
    HsqlInit hsqlInit=new HsqlInit();
    Connection connection=hsqlInit.initDatabase();
    public AccidentDBServiceImpl accidentDBServiceImpl = null;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new RoadAccidentController()).build();
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:/spring-config.xml");
        accidentDBServiceImpl = (AccidentDBServiceImpl) context.getBean("accidentDBServiceImpl");
    }

    /*Restful service to get all accidents*/
    @Test
    public void find_allAccident_test() throws Exception {
        mvc.perform(get("/accidents/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
                //.andExpect(jsonPath("$", hasSize(102900)));
    }

    /**Restful services to get accident by Id**/
    @Test
    public void find_AccidentById_test() throws Exception {
        String id = "200901BS70001";
        mvc.perform(get("/accidents/" + id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.accidentId").value(id));
    }

    /**Restful services to get accident by day**/
    @Test
    public void find_AccidentByDay_test() throws Exception {
        mvc.perform(get("/accidents/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
               // .andExpect(jsonPath("$", hasSize(18980)));
    }

    /**Restful services to create new accident with POST-in Progress **/
    @Test
    public void should_create_accidentRecord_Post() throws Exception {
       /* String accident="{\n" +
                "  \"accidentId\": \""+78787+"\",\n"+"}";

        mvc.perform(post("/accidents/"+accident).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));*/
    }

    /**Restful services to create new accident with PUT*/
    @Test
    public void should_create_accidentRecord_Put() throws Exception {
        /*//String accident="{\n" +
                               \"accidentId\": \""+78787+"\",\n"+"}";

        mvc.perform(put("/accidents/"+acident).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));*/
    }

    /*Restful services to update existed accident*/
    @Test
    public void should_update_accidentRecord() throws Exception {
        String oldAccidentRecord="200901BS70002";
        Accident accidentToUpdate=accidentDBServiceImpl.findOne(oldAccidentRecord);
        accidentToUpdate.setAccidentSeverity(3);
        accidentToUpdate.setDayOfWeek(1);
        mvc.perform(put("/accidents/"+accidentToUpdate).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        mvc.perform(get("/accidents/"+oldAccidentRecord).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accidentSeverity").value(3))
                .andExpect(jsonPath("$.accidentId").value(accidentToUpdate.getAccidentIndex()));
    }

    /*Restful services to delete accident*/
    @Test
    public void should_delete_accidentRecord() throws Exception {
        String NO_DATA="";
        String oldAccidentRecord="200901BS70001";
        Accident accidentToDelete=accidentDBServiceImpl.findOne(oldAccidentRecord);
        if(!accidentToDelete.equals(NO_DATA)) {
            mvc.perform(delete("/accidents/" + oldAccidentRecord).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            mvc.perform(get("/accidents/"+oldAccidentRecord).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
    }

    /*Response 404(Not Found) when id is not exist​*/
    @Test
    public void should_throwException() throws Exception {
        mvc.perform(get("/accidents/XXXX").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
