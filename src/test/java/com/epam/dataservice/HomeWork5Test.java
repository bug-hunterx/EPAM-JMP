package com.epam.dataservice;

import com.epam.springboot.AccidentsRestApplication;
import com.epam.springboot.modal.Accidents;
import com.epam.springboot.modal.RoadConditions;
import com.epam.springboot.repository.AccidentRepository;
import com.epam.springboot.repository.RoadConditionRepository;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
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
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
//        DirtiesContextTestExecutionListener.class,
//        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@WebAppConfiguration   // 3
@IntegrationTest("server.port:0")   // 4
@DatabaseSetup("/sampleData.xml")
public class HomeWork5Test {
    @Autowired
    RoadConditionRepository roadConditionRepository;
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

//        RestAssured.port = port;
    }

    @Test
    public void AcciedentsTest () {
        Accidents acciedent = new Accidents("200901BS70001");
        log.info(acciedent);

    }

    @Test
    public void AccidentRepositoryTest() {
        String testId = "200901BS70002";
        assertThat(repository.count(), equalTo(4L));
        Accidents accidents = repository.findOne(testId);
        log.info(accidents);
        assertThat(accidents.getId(), equalTo(testId));
    }

    /*
        @Test
        public void getHello() throws Exception {
            ResponseEntity<String> response = template.getForEntity(base.toString() + "hello/Bill", String.class);
            assertThat(response.getBody(), equalTo("hello, Bill"));
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
            List<Accidents> accidentsList = repository.findAll(); //findByDate(date);
            log.info(accidentsList);
            assertThat(accidentsList.size(), equalTo(2));
            assertThat(accidentsList.get(0).getDate(), equalTo(date));
        }

        @Test
        public void getAllAccidentsByWeatherConditionAndYearTest() {
            LocalDate date = LocalDate.parse("05/01/2009", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            List<Accidents> accidentsList = repository.findAll(); //findByDate(date);
            log.info(accidentsList);
            assertThat(accidentsList.size(), equalTo(2));
            assertThat(accidentsList.get(0).getDate(), equalTo(date));
        }
    */
    @Test
    public void RoadConditionRepositoryTest() {
        assertThat(roadConditionRepository.count(), equalTo(8L));
        List<RoadConditions> roadConditionsList = roadConditionRepository.findAll();
        log.info(roadConditionsList);
        assertThat(roadConditionsList.size(), equalTo(8));
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