package com.epam.processor;

import com.epam.data.RoadAccident;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * This is to be completed by mentees
 */
public class DataProcessor {

    private final List<RoadAccident> roadAccidentList;

    public DataProcessor(List<RoadAccident> roadAccidentList){
        this.roadAccidentList = roadAccidentList;
    }


//    First try to solve task using java 7 style for processing collections

    /**
     * Return road accident with matching index
     * @param index
     * @return
     */
    public RoadAccident getAccidentByIndex7(String index){
        return null;
    }


    /**
     * filter list by longtitude and latitude values, including boundaries
     * @param minLongitude
     * @param maxLongitude
     * @param minLatitude
     * @param maxLatitude
     * @return
     */
    public Collection<RoadAccident> getAccidentsByLocation7(float minLongitude, float maxLongitude, float minLatitude, float maxLatitude){
        return null;
    }

    /**
     * count incidents by road surface conditions
     * ex:
     * wet -> 2
     * dry -> 5
     * @return
     */
    public Map<String, Long> getCountByRoadSurfaceCondition7(){
        return null;
    }

    /**
     * find the weather conditions which caused the top 3 number of incidents
     * as example if there were 10 accidence in rain, 5 in snow, 6 in sunny and 1 in foggy, then your result list should contain {rain, sunny, snow} - top three in decreasing order
     * @return
     */
    public List<String> getTopThreeWeatherCondition7(){
        return null;
    }

    /**
     * return a multimap where key is a district authority and values are accident ids
     * ex:
     * authority1 -> id1, id2, id3
     * authority2 -> id4, id5
     * @return
     */
    public Multimap<String, String> getAccidentIdsGroupedByAuthority7(){
        return null;
    }


    // Now let's do same tasks but now with streaming api



    public RoadAccident getAccidentByIndex(String index){
        return null;
    }


    /**
     * filter list by longtitude and latitude fields
     * @param minLongitude
     * @param maxLongitude
     * @param minLatitude
     * @param maxLatitude
     * @return
     */
    public Collection<RoadAccident> getAccidentsByLocation(float minLongitude, float maxLongitude, float minLatitude, float maxLatitude){
        return null;
    }

    /**
     * find the weather conditions which caused max number of incidents
     * @return
     */
    public List<String> getTopThreeWeatherCondition(){
        return null;
    }

    /**
     * count incidents by road surface conditions
     * @return
     */
    public Map<String, Long> getCountByRoadSurfaceCondition(){
        return null;
    }

    /**
     * To match streaming operations result, return type is a java collection instead of multimap
     * @return
     */
    public Map<String, List<String>> getAccidentIdsGroupedByAuthority(){
        return null;
    }

}
