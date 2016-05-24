package com.epam.processor;

import com.epam.data.RoadAccident;
import com.epam.dbrepositories.AccidentRepository;
import com.epam.entities.Accident;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.http.HTTPException;
import java.util.List;
import java.util.Vector;

/**
 * Created by rahul.mujnani on 5/21/2016.
 */
@RestController
@RequestMapping(value = "/api/")
public class RoadAccidentController {
    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(RoadAccidentController.class);
    @Autowired
    private AccidentRepository accidentRepository;


    @RequestMapping(method = RequestMethod.GET, value = "/accidents", headers = "Accept=application/json")
    public
    @ResponseBody
    List<Accident> getAllAccidentRecords() {
        return accidentRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/accidents/{id}", headers = "Accept=application/json")
    public
    @ResponseBody
    Accident getAccidentById(@PathVariable String id) {
        return accidentRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/accidents/{day}", headers = "Accept=application/json")
    public
    @ResponseBody
    Vector getAccidentByDay(@PathVariable String day) {
        return accidentRepository.findAccidentsByDate(day);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/accidents/{accident}", headers = "Accept=application/json")
    public
    @ResponseBody
    Accident createAccidentByPOST(@PathVariable Accident accident) {
        return accidentRepository.save(accident);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/accidents/{accident}", headers = "Accept=application/json")
    public
    @ResponseBody
    Accident createAccidentByPUT(@PathVariable Accident accident) {
        return accidentRepository.save(accident);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/accidents/{accident}", headers = "Accept=application/json")
    public
    @ResponseBody
    Boolean updateAccident(@PathVariable Accident accident  ) {
        //accidentDBServiceImpl.update(accident);?
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/accidents/{accidentId}", headers = "Accept=application/json")
    public
    @ResponseBody
    void deleteAccidentById(@PathVariable String accidentId) {
        accidentRepository.delete(accidentId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/accidents/{id}", headers = "Accept=application/json")
    public
    @ResponseBody
    void invalidID(@PathVariable String accidentId) {
        if (accidentRepository.findOne(accidentId) == null) {
            throw new HTTPException(404);
        }
    }

}
