package com.epam.processor;

import com.epam.data.RoadAccident;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
        Optional<RoadAccident> foundRoadAccident = roadAccidentList.stream()
                .filter(accident -> accident.getAccidentId().equals(index))
                .findFirst();
        return foundRoadAccident.isPresent()?foundRoadAccident.get():null;
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
        return roadAccidentList.stream()
                .filter(accident -> isInScope(accident.getLongitude(), minLongitude, maxLongitude)
                        && isInScope(accident.getLatitude(), minLatitude, maxLatitude))
                .collect(Collectors.toList());
    }

    /**
     * count incidents by road surface conditions
     * ex:
     * wet -> 2
     * dry -> 5
     * @return
     */
    public Map<String, Long> getCountByRoadSurfaceCondition7(){
        return roadAccidentList.stream()
                .collect(Collectors.groupingBy(RoadAccident::getRoadSurfaceConditions, Collectors.counting()));
    }

    /**
     * find the weather conditions which caused the top 3 number of incidents
     * as example if there were 10 accidence in rain, 5 in snow, 6 in sunny and 1 in foggy, then your result list should contain {rain, sunny, snow} - top three in decreasing order
     * @return
     */
    public List<String> getTopThreeWeatherCondition7(){
        Map<String, Long> groupbyWeather = roadAccidentList.stream()
                .collect(Collectors.groupingBy(RoadAccident::getWeatherConditions, Collectors.counting()));
        return groupbyWeather.keySet().stream()
                .sorted((w1,w2) -> groupbyWeather.get(w2).compareTo(groupbyWeather.get(w1)))
                .limit(3)
                .collect(Collectors.toList());
    }

    /**
     * return a multimap where key is a district authority and values are accident ids
     * ex:
     * authority1 -> id1, id2, id3
     * authority2 -> id4, id5
     * @return
     */
    public Multimap<String, String> getAccidentIdsGroupedByAuthority7(){
        Multimap<String, String> resultMap = ArrayListMultimap.create();
        roadAccidentList.stream()
                .forEach(accident -> resultMap.put(accident.getDistrictAuthority(), accident.getAccidentId()));
        return resultMap;
    }


    // Now let's do same tasks but now with streaming api



    public RoadAccident getAccidentByIndex(String index){
        Optional<RoadAccident> foundRoadAccident = roadAccidentList.stream()
                .filter(accident -> accident.getAccidentId().equals(index))
                .findFirst();
        return foundRoadAccident.isPresent()?foundRoadAccident.get():null;
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
        return roadAccidentList.stream()
                .filter(accident -> isInScope(accident.getLongitude(), minLongitude, maxLongitude)
                        && isInScope(accident.getLatitude(), minLatitude, maxLatitude))
                .collect(Collectors.toList());
    }

    private boolean isInScope(float actualValue, float minValue, float maxValue){
        return minValue <= actualValue && actualValue <= maxValue;
    }

    /**
     * find the weather conditions which caused max number of incidents
     * @return
     */
    public List<String> getTopThreeWeatherCondition(){
        Map<String, Long> groupbyWeather = roadAccidentList.stream()
                .collect(Collectors.groupingBy(RoadAccident::getWeatherConditions, Collectors.counting()));
        return groupbyWeather.keySet().stream()
                .sorted((w1,w2) -> groupbyWeather.get(w2).compareTo(groupbyWeather.get(w1)))
                .limit(3)
                .collect(Collectors.toList());
    }

    /**
     * count incidents by road surface conditions
     * @return
     */
    public Map<String, Long> getCountByRoadSurfaceCondition(){
        return roadAccidentList.stream()
                .collect(Collectors.groupingBy(RoadAccident::getRoadSurfaceConditions, Collectors.counting()));
    }

    /**
     * To match streaming operations result, return type is a java collection instead of multimap
     * @return
     */
    public Map<String, List<String>> getAccidentIdsGroupedByAuthority(){
        return roadAccidentList.stream()
                .map(roadAccident -> roadAccident.getDistrictAuthority())
                .collect(Collectors.groupingBy(String::toString, Collectors.toList()));
    }

}
