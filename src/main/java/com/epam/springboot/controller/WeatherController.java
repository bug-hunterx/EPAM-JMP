package com.epam.springboot.controller;

import com.epam.springboot.modal.WeatherConditions;
import com.epam.springboot.repository.WeatherConditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by bill on 16-5-29.
 */
@RestController
@RequestMapping(value = "/")
public class WeatherController {
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(WeatherController.class);
    @Autowired
    private WeatherConditionRepository weatherConditionRepository;

    @RequestMapping(method= RequestMethod.GET, value= "/init", headers="Accept=application/json")
    public @ResponseBody String init() {
        weatherConditionRepository.saveAndFlush(new WeatherConditions(1,"First"));
        weatherConditionRepository.saveAndFlush(new WeatherConditions(2,"Second"));
        weatherConditionRepository.saveAndFlush(new WeatherConditions(3,"Third"));
        return "Init 2 records";
    }

    @RequestMapping(method= RequestMethod.GET, value= "/test", headers="Accept=application/json")
    public @ResponseBody
    WeatherConditions testWeatherConditions() {
        return new WeatherConditions(20,"Test20");
    }

    @RequestMapping(method= RequestMethod.GET, value= "/weather", headers="Accept=application/json")
    public @ResponseBody
    List<WeatherConditions> getWeatherConditions() {
        List<WeatherConditions> result = weatherConditionRepository.findAll();
        logger.info(result);
        return result;
    }

    @RequestMapping(method= RequestMethod.GET, value= "/weather/{id}", headers="Accept=application/json")
    public @ResponseBody
    WeatherConditions getWeatherCondition(@PathVariable Integer id){
        WeatherConditions weatherConditions = new WeatherConditions(20,"Test20");
        logger.warn("Autowired weatherConditionRepository is: " + weatherConditionRepository);
        if (weatherConditionRepository != null) {
            weatherConditions = weatherConditionRepository.findOne(id);
        }
        logger.info(weatherConditions);
        return weatherConditions;
    }
}
