package com.epam.springboot.controller;

import com.epam.springboot.modal.WeatherConditions;
import com.epam.springboot.repository.WeatherConditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by bill on 16-5-29.
 */
@RestController
@RequestMapping(value = "/weather")
public class WeatherController {
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(WeatherController.class);
    @Autowired
    private WeatherConditionRepository weatherConditionRepository;

    @ExceptionHandler
    void handleIllegalArgumentException(IllegalArgumentException e,
                                        HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

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

    @RequestMapping(method= RequestMethod.GET, headers="Accept=application/json")
    public @ResponseBody
    List<WeatherConditions> getWeatherConditions() {
        List<WeatherConditions> result = weatherConditionRepository.findAll();
        logger.info(result);
        return result;
    }

    @RequestMapping(method= RequestMethod.GET, value= "/{id}", headers="Accept=application/json")
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

    @RequestMapping(method= RequestMethod.POST, headers="Accept=application/json")
    public @ResponseBody
    WeatherConditions create (@RequestBody WeatherConditions weatherConditions) {
        logger.info("Create " + weatherConditions);
        return weatherConditionRepository.save(weatherConditions);
    }

    @RequestMapping(method= RequestMethod.PUT, value= "/{id}", headers="Accept=application/json")
    public @ResponseBody
    WeatherConditions put (@PathVariable Integer id, @RequestBody WeatherConditions weatherConditions) {
        WeatherConditions target = weatherConditionRepository.findOne(id);
        if ((target != null) && (weatherConditions.getCode().equals(target.getCode()))) {
            logger.info("Put: Update " + target + " With " + weatherConditions);
            target.setLabel(weatherConditions.getLabel());
            return weatherConditionRepository.save(target);
        } else {
            logger.error("Try to update id=" + id + " With " + weatherConditions );
            throw new IllegalArgumentException();
//            return null;
        }
    }

    @RequestMapping(method= RequestMethod.DELETE, value= "/{id}", headers="Accept=application/json")
    public @ResponseBody
    WeatherConditions delete (@PathVariable Integer id) {
        WeatherConditions weatherConditions = weatherConditionRepository.findOne(id);
        weatherConditionRepository.delete(id);
        logger.info("Delete " + weatherConditions);
        return weatherConditions;
    }

}
