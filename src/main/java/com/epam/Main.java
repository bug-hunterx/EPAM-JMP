package com.epam;

import com.epam.dbservice.AccidentDBServiceImpl;
import com.epam.entities.RoadAccident;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Tkachi on 2016/4/3.
 */
public class Main {
    public static void main(String[] args) {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("/spring/spring-config.xml");

        AccidentDBServiceImpl accidentService = (AccidentDBServiceImpl) context.getBean("accidentService");
        RoadAccident accident = accidentService.findOne("200901BS70001");
        System.out.println(accident.toString());

        context.close();
    }
}
