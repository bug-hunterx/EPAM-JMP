package com.epam;

import com.epam.dal.JpaAccidentDao;
import com.epam.entities.Accident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by Tkachi on 2016/4/3.
 */
public class Main {
    private static JpaAccidentDao accidentDao;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring/spring-config.xml");

        accidentDao = (JpaAccidentDao) context.getBean("accidentDao");
//        List<Accident> accidents = accidentDao.findAll();
        Accident accident = accidentDao.findOne("200901BS70001");
        System.out.println(accident.getId());
    }
}
