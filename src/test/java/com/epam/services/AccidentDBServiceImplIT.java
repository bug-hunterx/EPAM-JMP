package com.epam.services;

import com.epam.AccidentsManagementApplication;
import com.epam.entities.RoadAccident;
import com.epam.it.IntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by Alexey on 21.05.2016.
 */
@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AccidentsManagementApplication.class)
@WebAppConfiguration
public class AccidentDBServiceImplIT {

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    AccidentDBServiceImpl accidentService;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testRestFindAll() throws Exception {
        mockMvc.perform(get("/accidents/"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$", hasSize(102900)));
    }

    @Test
    public void testRestGetById() throws Exception {
        String id = "200901BS70001";

        mockMvc.perform(get("/accidents/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accidentId", is(id)));
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
    public void testRestFindByDate() throws Exception {
        mockMvc.perform(get("/accidents/on/2009-01-01"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$", hasSize(155)));
    }

    /*@Test
    public void testRestUpdate() throws Exception {
        String id = "200901BS70002";
        String dateString = "2016-05-25";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getDefault());
        Date date = dateFormat.parse(dateString);

        RoadAccident accidentForUpdate = accidentService.findOne(id);
        accidentForUpdate.setDate(date);

        mockMvc.perform(put("/accidents/" + id));

        mockMvc.perform(get("/accidents/" + id))
                .andExpect(jsonPath("$.date", is(dateString)));
    }*/

    @Test
    public void testRestDelete() throws Exception {
        String dummyRecordId = "dummy";
        String dummyRecord = "{\n" +
                "  \"accidentId\": \""+dummyRecordId+"\",\n" +
                "  \"longitude\": -0.179599,\n" +
                "  \"latitude\": 51.486668,\n" +
                "  \"numberOfVehicles\": 2,\n" +
                "  \"numberOfCasualties\": 1,\n" +
                "  \"date\": 1230998400000,\n" +
                "  \"weatherConditions\": {\n" +
                "    \"code\": 1\n" +
                "  },\n" +
                "  \"roadSurfaceConditions\": {\n" +
                "    \"code\": 1\n" +
                "  }\n" +
                "}";

        mockMvc.perform(post("/accidents/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(dummyRecord));

        mockMvc.perform(delete("/accidents/" + dummyRecordId));

        mockMvc.perform(get("/accidents/" + dummyRecordId))
                .andExpect(content().string(""));
    }

    @Test
    public void testRestUpdateWithWrongIdThrowsError() throws Exception {
        String dummyRecord = "{\n" +
                "  \"accidentId\": \""+1+"\",\n" +
                "}";

        mockMvc.perform(put("/accidents/2")
        .contentType(MediaType.APPLICATION_JSON)
        .content(dummyRecord))
                .andExpect(status().isBadRequest());
    }

}
