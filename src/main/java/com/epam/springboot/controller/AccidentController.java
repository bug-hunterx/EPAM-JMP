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
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AccidentController.class);
    @Autowired
    private AccidentRepository repository;

    @RequestMapping(method= RequestMethod.GET, value= "/accidents", headers="Accept=application/json")
    public @ResponseBody
    List<Accidents> accidents() {
        return repository.findAll();
    }

    @RequestMapping(method= RequestMethod.GET, value= "/accidents/{id}", headers="Accept=application/json")
    public @ResponseBody
    Accidents accidents(@PathVariable String id){
        Accidents accidents= repository.findOne(id);
        return accidents;
    }

    @RequestMapping(method= RequestMethod.POST, value= "/accidents", headers="Accept=application/json")
    public @ResponseBody String save(@RequestBody Accidents accidents){
        logger.info("creating Accidents: "+ accidents.toString());
        //validate(s, false);
        repository.save(accidents);
        return accidents.getId();
    }

    @RequestMapping(method= RequestMethod.PUT, value= "/accidents/{id}", headers="Accept=application/json")
    public @ResponseBody void update(@RequestBody Accidents accidents, @PathVariable String id){
        logger.info("Update Accidents with ID: " + id);
        if(!accidents.getId().equals(id))
            logger.error("Expect " + id + " , but get " + accidents.getId());
//            throw new BadRequestError("id is not match");
        repository.save(accidents);
    }

    @RequestMapping(method= RequestMethod.DELETE, value= "/accidents/{id}", headers="Accept=application/json")
    public @ResponseBody void delete(@PathVariable String id){
        logger.info("Delete Accidents with ID=" + id);
        repository.delete(id);
    }
}
