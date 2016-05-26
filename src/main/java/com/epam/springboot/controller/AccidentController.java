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
    private static org.apache.log4j.Logger LOG= org.apache.log4j.Logger.getLogger(AccidentController.class);
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

/*
    @RequestMapping(method= RequestMethod.GET, value= "/accidentsByRoadCondition/{id}", headers="Accept=application/json")
    public @ResponseBody
    List<Accidents> getAllAccidentsByRoadCondition(@PathVariable Integer id){
        List<Accidents> accidents= repository.findByRoadSurfaceConditions(id);

        return accidents;
    }
*/

    @RequestMapping(method= RequestMethod.POST, value= "/accidents", headers="Accept=application/json")
    public @ResponseBody String save(@RequestBody Accidents accidents){
        LOG.info("creating Accidents: "+ accidents.toString());
        //validate(s, false);
        repository.save(accidents);
        return accidents.getId();
    }

    @RequestMapping(method= RequestMethod.PUT, value= "/accidents/{id}", headers="Accept=application/json")
    public @ResponseBody void update(@RequestBody Accidents accidents, @PathVariable String id){
        LOG.info("Update Accidents with ID: " + id);
        if(accidents.getId()!= id)
            LOG.error("Expect " + id + " , but get " + accidents.getId());
//            throw new BadRequestError("id is not match");
        repository.save(accidents);
    }

    @RequestMapping(method= RequestMethod.DELETE, value= "/accidents/{id}", headers="Accept=application/json")
    public @ResponseBody void delete(@PathVariable String id){
        LOG.info("Delete Accidents with ID=" + id);
        repository.delete(id);
    }
}
