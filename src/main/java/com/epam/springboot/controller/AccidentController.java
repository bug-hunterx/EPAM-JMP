package com.epam.springboot.controller;

import com.epam.springboot.modal.Accidents;
import com.epam.springboot.repository.AccidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by bill on 16-5-22.
 */
@RestController
//@RequestMapping(value = "/api/")
public class AccidentController {
    @Autowired
    private AccidentRepository repository;

    @RequestMapping(method= RequestMethod.GET, value= "/accidents", headers="Accept=application/xml")
//    @RequestMapping("/accidents")
    List<Accidents> accidents() {
        return repository.findAll();
    }

    @RequestMapping(method= RequestMethod.GET, value= "/accidents/{id}", headers="Accept=application/json")
    public @ResponseBody
    Accidents accidents(@PathVariable String id){
        Accidents accidents= repository.findOne(id);

        return accidents;
    }

    @RequestMapping(method= RequestMethod.GET, value= "/accidentsByRoadCondition/{id}", headers="Accept=application/json")
    public @ResponseBody
    List<Accidents> getAllAccidentsByRoadCondition(@PathVariable Integer id){
        List<Accidents> accidents= repository.findByRoadSurfaceConditions(id);

        return accidents;
    }

}
