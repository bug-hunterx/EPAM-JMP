package com.epam;

import com.epam.dbservice.AccidentDBServiceImpl;
import com.epam.entities.RoadAccident;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Created by Tkachi on 2016/4/3.
 */
public class Main {
    public static void main(String[] args) {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("/spring/spring-config.xml");

        AccidentDBServiceImpl accidentService = (AccidentDBServiceImpl) context.getBean("accidentService");

        /*RoadAccident accidentById = accidentService.findOne("200901BS70001");
        System.out.println(accidentById.toString());

        List<RoadAccident> accidentsByYear = accidentService.getAllAccidentsByWeatherConditionAndYear("Fine no high winds", 2009);
        System.out.println(accidentsByYear.get(0).toString());

        List<RoadAccident> accidentsByRoadConditions = accidentService.getAllAccidentsByRoadCondition("Dry");
        System.out.println(accidentsByRoadConditions.get(0).toString());

        List<RoadAccident> accidentsByDate = accidentService.getAllAccidentsByDate(
                Date.from(LocalDate.of(2009, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant())
        );
        System.out.println(accidentsByDate.get(0).toString());*/

        RoadAccident accidentForUpdate = accidentService.findOne("200901BS70002");
        System.out.println("Found entity for update: " + accidentForUpdate.toString());
        accidentForUpdate.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        try {
            accidentService.update(accidentForUpdate);
            System.out.println("Successfully updated entity " + accidentForUpdate.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        context.close();
    }
}
