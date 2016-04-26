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

    private static final String ACCIDENTS_CSV = "src/main/resources/DfTRoadSafety_Accidents_2009.csv";
    private final List<RoadAccident> accidents;
    private final DataProcessor dataProcessor;

    public AccidentsController(){

        AccidentsDataLoader accidentsDataLoader = new AccidentsDataLoader();
        accidents = accidentsDataLoader.loadRoadAccidents(ACCIDENTS_CSV);
        dataProcessor = new DataProcessor(accidents);
    }

    @RequestMapping("")
     public List<RoadAccident> getAllAccidents(){
        return accidents;
    }

    @RequestMapping("/{id}")
    public RoadAccident getAllAccidents(@PathVariable String id){
        return dataProcessor.getAccidentByIndex(id);
    }
}
