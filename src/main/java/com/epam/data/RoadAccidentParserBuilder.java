package com.epam.data;

import java.util.Map;

/**
 * Created by Tkachi on 2016/3/31.
 */
public class RoadAccidentParserBuilder {
    Map<Integer, String> policeForceMap;
    Map<Integer, String> accidentSeverinityMap;
    Map<Integer, String> districtAuthorityMap;
    Map<Integer, String> highwayAuthorityMap;
    Map<Integer, String> weatherConditionsMap;
    Map<Integer, String> roadSurfaceConditionsMap;
    Map<Integer, String> lightConditionsMap;


    public RoadAccidentParserBuilder withPoliceForceMap(Map<Integer, String> policeForceMap) {
        this.policeForceMap = policeForceMap;
        return this;
    }

    public RoadAccidentParserBuilder withAccidentSeverinityMap(Map<Integer, String> accidentSeverinityMap) {
        this.accidentSeverinityMap = accidentSeverinityMap;
        return this;
    }

    public RoadAccidentParserBuilder withDistrictAuthorityMap(Map<Integer, String> districtAuthorityMap) {
        this.districtAuthorityMap = districtAuthorityMap;
        return this;
    }

    public RoadAccidentParserBuilder withHighwayAuthorityMap(Map<Integer, String> highwayAuthorityMap) {
        this.highwayAuthorityMap = highwayAuthorityMap;
        return this;
    }

    public RoadAccidentParserBuilder withWeatherConditionsMap(Map<Integer, String> weatherConditionsMap) {
        this.weatherConditionsMap = weatherConditionsMap;
        return this;
    }

    public RoadAccidentParserBuilder withRoadSurfaceConditionsMap(Map<Integer, String> roadSurfaceConditionsMap) {
        this.roadSurfaceConditionsMap = roadSurfaceConditionsMap;
        return this;
    }

    public RoadAccidentParser build(){
        return new RoadAccidentParser(this);
    }

}
