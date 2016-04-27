package com.epam;

import com.epam.data.AccidentsDataLoader;
import com.epam.data.RoadAccident;
import com.epam.processor.DataProcessor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accidents")
public class AccidentsController {

    @RequestMapping("")
     public String getAllAccidents(){
        return "Here you will get list of all accidents... When I will implements this api...";
    }

    @RequestMapping("/{id}")
    public String getAllAccidents(@PathVariable String id){
        return "You requested accident with id " + id + " - but this functionality is not implemented yet";
    }
}
