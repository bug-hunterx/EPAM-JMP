package com.epam.processor;

import com.epam.data.RoadAccident;
import com.epam.dbservice.AccidentService;
import com.epam.entities.Accident;
import org.hamcrest.core.IsNull;
import org.hsqldb.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-config.xml")
public class AccidentServiceIntegrationTest {

    private static Server hsqlServer;
    @Autowired
    public AccidentService accidentService;

    @BeforeClass
    public static void starDatabase(){
        System.setProperty("textdb.allow_full_path", "true");
        hsqlServer = new Server();
        hsqlServer.setLogWriter(null);
        hsqlServer.setSilent(true);
        hsqlServer.setDatabaseName(0, "jmp");
        hsqlServer.setPort(12345);
        hsqlServer.setDatabasePath(0, "file:jmpdb");

        hsqlServer.start();
    }

    @AfterClass
    public static void shutdownDatabase(){
        hsqlServer.stop();
        hsqlServer.shutdown();
    }

    @Test
    public void testFindOne() throws Exception {
        Accident accident = accidentService.findOne("200901BS70001");
        assertThat(accident, IsNull.nullValue());
    }



}