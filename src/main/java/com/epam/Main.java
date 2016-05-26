package com.epam;

import com.epam.data.AccidentsDataLoader;
import com.epam.data.RoadAccident;
import com.epam.data.RoadAccidentBuilder;
import com.epam.dataservice.AccidentBatchLoader;
import com.epam.dbservice.AccidentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.List;


/**
 * Created by Tkachi on 2016/4/3.
 */
@SpringBootApplication
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

//    @Bean
    public CommandLineRunner demo(AccidentService repository) {
//        AccidentsDataLoader loader = new AccidentsDataLoader();
//        List<RoadAccident> accidents = loader.loadRoadAccidents("src/main/resources/DfTRoadSafety_Accidents_2009.csv");
//        return (args) -> {
//            accidents.forEach(accident -> repository.save(accident));
//            System.out.println(repository.findAll().iterator().next());
//            System.out.println(repository.getAllAccidentsByRoadSurfaceCondition("Dry").iterator().next());
//            System.out.println(repository.getAllAccidentsByWeatherConditionAndYear("Fine no high winds", 2009).iterator().next());
//        };
    	return null;
    }


}
