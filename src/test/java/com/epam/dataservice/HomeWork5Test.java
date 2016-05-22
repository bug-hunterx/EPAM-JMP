package com.epam.dataservice;

import com.epam.springboot.AccidentsRestApplication;
import com.epam.springboot.modal.Accidents;
import com.epam.springboot.repository.AccidentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by bill on 16-5-22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AccidentsRestApplication.class)   //MockServletContext
@WebAppConfiguration   // 3
@IntegrationTest("server.port:0")   // 4
public class HomeWork5Test {
    @Autowired
    AccidentRepository repository;

    @Value("${local.server.port}")   // 6
    private int port;

    private URL base;
    private RestTemplate template;

//    private static ApplicationContext context;
    @Before
    public void init ()  throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
        template = new TestRestTemplate();

        repository.deleteAll();
        repository.save(new Accidents("200901BS70001",1,2));
        repository.save(new Accidents("200901BS70002",2,3));
//        RestAssured.port = port;
    }

    @Test
    public void AcciedentsTest () {
        Accidents acciedent = new Accidents("200901BS70001",1,2);
        System.out.println(acciedent);

    }

    @Test
    public void getHello() throws Exception {
        ResponseEntity<String> response = template.getForEntity(base.toString() + "hello/Bill", String.class);
        assertThat(response.getBody(), equalTo("hello, Bill"));
    }

    @Test
    public void AccidentRepositoryTest() {
        repository.save(new Accidents("200901BS70003",2,3));
        List<Accidents> accidentsList = repository.findAll();
        System.out.println(accidentsList);
        assertThat(accidentsList.size(), equalTo(3));
    }
}
