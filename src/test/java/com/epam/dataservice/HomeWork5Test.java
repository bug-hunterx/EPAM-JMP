package com.epam.dataservice;

import com.epam.springboot.AccidentsRestApplication;
import com.epam.springboot.modal.Accidents;
import com.epam.springboot.repository.AccidentRepository;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    static Logger log = Logger.getLogger(HomeWork5Test.class.getName());
//    private static ApplicationContext context;
    @Before
    public void init ()  throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
        template = new TestRestTemplate();

        repository.deleteAll();
        repository.save(new Accidents("200901BS70001",-0.201349,51.512273,1,2,2,1,"01/01/2009",5,"15:11",12,1,1,1));
        repository.save(new Accidents("200901BS70002",-0.199248,51.514399,2,2,2,11,"05/01/2009",2,"10:59",12,1,1,2));
        repository.save(new Accidents("200901BS70003",-0.179599,51.486668,3,3,2,1,"04/01/2009",1,"14:19",12,1,1,2));
        repository.save(new Accidents("200901BS70004",-0.20311,51.507804,4,2,2,1,"05/25/2016",3,"8:10",12,1,8,4));
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
        List<Accidents> accidentsList = repository.findAll();
        log.info(accidentsList);
        assertThat(accidentsList.size(), equalTo(4));
    }

    @Test
    public void getAllAccidentsByRoadConditionTest() {
        Integer roadCondition = 2;
        List<Accidents> accidentsList = repository.findByRoadSurfaceConditions(roadCondition);
        log.info(accidentsList);
//        assertThat(accidentsList.size(), equalTo(2));
        assertThat(accidentsList.get(0).getRoadSurfaceConditions(), equalTo(roadCondition));
    }

    @Test
    public void getAllAccidentsByDateTest() {
        LocalDate date = LocalDate.parse("05/01/2009", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        List<Accidents> accidentsList = repository.findByDate(date);
        log.info(accidentsList);
        assertThat(accidentsList.size(), equalTo(2));
        assertThat(accidentsList.get(0).getDate(), equalTo(date));
    }

    @Test
    public void getAllAccidentsByWeatherConditionAndYearTest() {
        LocalDate date = LocalDate.parse("05/01/2009", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        List<Accidents> accidentsList = repository.findByDate(date);
        log.info(accidentsList);
        assertThat(accidentsList.size(), equalTo(2));
        assertThat(accidentsList.get(0).getDate(), equalTo(date));
    }
}
/*

Scenarios to be implemented for Homework:


        1. Find all the accidents by ID(Note: We can use findOne method which will accept the Accident ID as PK).

        2. Find all the accidents count groupby all roadsurface conditions .

        3. Find all the accidents count groupby accident year and weather condition .( For eg: in year 2009 we need to know the number of accidents based on each weather condition).

        4. On a given date,  fetch all the accidents and update the Time based on the below rules

        Time Logic:
        MORNING - 6 am to 12 pm
        AFTERNOON - 12 pm to 6 pm
        EVENING - 6 pm to 12 am
        NIGHT - 12 am to 6 am
 */