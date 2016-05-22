package com.epam.springboot;

import com.epam.springboot.modal.Accidents;
import com.epam.springboot.repository.AccidentRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Created by bill on 16-5-22.
 */

@Configuration
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class AccidentsRestApplication {
    public static ApplicationContext getContext() {
        return context;
    }

    private static ApplicationContext context;
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication();
        context = SpringApplication.run(AccidentsRestApplication.class, args);
//        initDB(context);
    }

    private static void initDB(ApplicationContext ctx){
        AccidentRepository accidentRepository= ctx.getBean(AccidentRepository.class);
        accidentRepository.save(new Accidents("200901BS70001",1,2));
        accidentRepository.save(new Accidents("200901BS70002",2,3));
        List<Accidents> accidentsList = accidentRepository.findAll();
        System.out.println(accidentsList);
    }
}
