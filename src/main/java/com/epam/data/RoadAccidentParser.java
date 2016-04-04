package com.epam.data;

import java.util.Map;

/**
 * Created by Tkachi on 2016/3/31.
 */
public class RoadAccidentParser {

    private final Map<Integer, String> policeForceMap;
    private final Map<Integer, String> accidentSeverinityMap;
    private final Map<Integer, String> districtAuthorityMap;
    private final Map<Integer, String> highwayAuthorityMap;
    private final Map<Integer, String> weatherConditionsMap;
    private final Map<Integer, String> roadSurfaceConditionsMap;
    private final Map<Integer, String> lightConditionsMap;

    RoadAccidentParser(RoadAccidentParserBuilder builder){
        this.policeForceMap = builder.policeForceMap;
        this.accidentSeverinityMap = builder.accidentSeverinityMap;
        this.districtAuthorityMap = builder.districtAuthorityMap;
        this.highwayAuthorityMap = builder.highwayAuthorityMap;
        this.weatherConditionsMap = builder.weatherConditionsMap;
        this.roadSurfaceConditionsMap = builder.roadSurfaceConditionsMap;
        this.lightConditionsMap = builder.lightConditionsMap;
    }
}
