package com.epam.dataservice;

import com.epam.springboot.AccidentsRestApplication;
import com.epam.springboot.controller.HelloController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by bill on 16-5-22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AccidentsRestApplication.class)   //MockServletContext
@WebAppConfiguration
public class HomeWork6Test {

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
    }

    @Test
    public void contextLoads() {
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


}
