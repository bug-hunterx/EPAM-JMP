package com.epam.processor;

import com.epam.data.RoadAccident;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * This is to be completed by mentees
 */
public class DataProcessor {

    private final List<RoadAccident> roadAccidentList;

    /**
     * feel free to add more pre-processing in the constructor if you feel like it
     * @param roadAccidentList
     */
    public DataProcessor(List<RoadAccident> roadAccidentList){
        this.roadAccidentList = roadAccidentList;
    }

    public RoadAccident getAccidentByIndex(String index){
        return roadAccidentList.stream().filter(roadAccident -> roadAccident.getAccidentId().equals(index)).findFirst().orElse(null);
    }

    /**
     * count incidents by road surface conditions
     * @return
     */
    public Map<String, Long> getCountByRoadSurfaceCondition(){
        Map<String, Long> countedBySurfaceConditions = roadAccidentList.stream()
                .map(roadAccidentList -> roadAccidentList.getRoadSurfaceConditions())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return countedBySurfaceConditions;
    }


    /**
     * find the weather conditions which caused max number of incidents
     * @return
     */
    public List<String> getTopThreeWeatherCondition(){
        Map<String, Long> countedByWeather = roadAccidentList.stream()
                .map(roadAccidentList -> roadAccidentList.getWeatherConditions())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        List<String> topWeather = countedByWeather
                .entrySet()
                .stream()
                .sorted((o1, o2) -> (int) (o2.getValue() - o1.getValue()))
                .limit(3)
                .map(weatherCountEntry -> weatherCountEntry.getKey())
                .collect(Collectors.toList());

        return ImmutableList.copyOf(topWeather);
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

    public Multimap<String, String> getAccidentsIdsGroupedByAuthority(){
        return null;
    }

}
