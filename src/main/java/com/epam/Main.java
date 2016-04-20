package com.epam;

import com.epam.data.AccidentsDataLoader;
import com.epam.data.RoadAccident;

import java.io.IOException;
import java.util.List;


/**
 * Created by Tkachi on 2016/4/3.
 */
public class Main {

    private static final String ACCIDENTS_CSV = "src/main/resources/DfTRoadSafety_Accidents_2010.csv";


    public static void main(String[] args) throws IOException {
        AccidentsDataLoader accidentsDataLoader = new AccidentsDataLoader();
        List<RoadAccident> accidents = accidentsDataLoader.loadRoadAccidents(ACCIDENTS_CSV);

    }

}
