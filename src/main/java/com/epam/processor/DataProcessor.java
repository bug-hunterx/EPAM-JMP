package com.epam.processor;

import com.epam.data.AccidentsDataLoader;
import com.epam.data.RoadAccident;
import com.google.common.collect.Multimap;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.summingLong;

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
    public RoadAccident getAccidentByIndex7(String index) {

        Iterator<RoadAccident> iterator = roadAccidentList.iterator();
        RoadAccident roadAccidentRecord = null;

        while (iterator.hasNext()) {
            roadAccidentRecord = iterator.next();
            if (roadAccidentRecord.getAccidentId().equals(index)) break;
        }
        return roadAccidentRecord;
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
        Iterator<RoadAccident> iterator = roadAccidentList.iterator();
        RoadAccident roadAccidentRecord = null;
        List<RoadAccident> roadAccidentCollection = new ArrayList<RoadAccident>();

        while (iterator.hasNext()) {
            roadAccidentRecord = iterator.next();
            if ((roadAccidentRecord.getLongitude() > minLongitude && roadAccidentRecord.getLongitude()<=maxLongitude )&&
                    (roadAccidentRecord.getLatitude() > minLatitude && roadAccidentRecord.getLatitude()<=maxLatitude))
                roadAccidentCollection.add(roadAccidentRecord);
        }
        return roadAccidentCollection;
    }

    /**
     * count incidents by road surface conditions
     * ex:
     * wet -> 2
     * dry -> 5
     * @return
     */
    public Map<String, Long> getCountByRoadSurfaceCondition7() {

        Iterator<RoadAccident> iterator = roadAccidentList.iterator();
        RoadAccident roadAccidentRecord = null;
        Map<String, Long> countByRoadSurfecCond = new HashMap<String, Long>();

        while (iterator.hasNext()) {
            roadAccidentRecord = iterator.next();
            Long value = countByRoadSurfecCond.get(roadAccidentRecord.getRoadSurfaceConditions());
            if (value == null)
                value = 0L;
            value++;
            countByRoadSurfecCond.put(roadAccidentRecord.getRoadSurfaceConditions(), value);
        }

        return countByRoadSurfecCond;
    }

    /**
     * find the weather conditions which caused the top 3 number of incidents
     * as example if there were 10 accidence in rain, 5 in snow, 6 in sunny and 1 in foggy, then your result list should contain {rain, sunny, snow} - top three in decreasing order
     * @return
     */
    public List<String> getTopThreeWeatherCondition7(){
        /*Iterator<RoadAccident> iterator = roadAccidentList.iterator();
        RoadAccident roadAccidentRecord = null;
        List<String> weatherList = new ArrayList<String>();
        Map<String, Long> weatherMap = new TreeMap<String, Long>();
        int count=0;
        while (iterator.hasNext()) {
            roadAccidentRecord = iterator.next();
            Long value = weatherMap.get(roadAccidentRecord.getWeatherConditions());
            if (value == null)
                value = 0L;
            value++;
            weatherMap.put(roadAccidentRecord.getRoadSurfaceConditions(), value);
        }

        for (Map.Entry<String,Long> entry:weatherMap.entrySet()) {
            if (count > 3) break;
            weatherList.add(entry.getKey());
            count++;
        }*/

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

/*        Iterator<RoadAccident> iterator = roadAccidentList.iterator();
        RoadAccident roadAccidentRecord = null;
        Map<String, Long> countByRoadSurfecCond = new HashMap<String, Long>();

        while (iterator.hasNext()) {
            roadAccidentRecord = iterator.next();
            Long value = countByRoadSurfecCond.get(roadAccidentRecord.getRoadSurfaceConditions());
            if (value == null)
                value = 0L;
            value++;
            countByRoadSurfecCond.put(roadAccidentRecord.getRoadSurfaceConditions(), value);
        }*/

        return null;

    }


    // Now let's do same tasks but now with streaming api



    public RoadAccident getAccidentByIndex(String index){
        List<RoadAccident> accidentByIndexList = roadAccidentList.stream()
                .filter(a -> a.getAccidentId().equals(index))
                .collect(Collectors.toList());

        //Optional.ofNullable(accidentByIndexList).isPresent();
        if(accidentByIndexList.size()<=0) return null;
        return accidentByIndexList.get(0);
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

        Predicate<RoadAccident> locationByLongitude = s -> s.getLongitude()> minLongitude && s.getLongitude()<= maxLongitude;
        Predicate<RoadAccident> locationByLatitude  = s -> s.getLatitude()> minLatitude && s.getLatitude()<= maxLatitude;

        List<RoadAccident> accidentListByLocation = roadAccidentList.stream()
                .filter(locationByLongitude.and(locationByLatitude))
                .collect(Collectors.toList());

        return accidentListByLocation;
    }

    /**
     * find the weather conditions which caused max number of incidents
     * @return
     */
    public List<String> getTopThreeWeatherCondition(){
        Map<String ,Long>  incidentCount=roadAccidentList.stream()
                .collect( Collectors.groupingBy( RoadAccident::getWeatherConditions,
                        counting()));

        List<String> accidentListByWeatherCond=incidentCount.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return accidentListByWeatherCond;
    }

    /**
     * count incidents by road surface conditions1
     * @return
     */
    public Map<String, Long> getCountByRoadSurfaceCondition(){
        Map<String ,Long>  countByRoadSurfaceCond=roadAccidentList.stream()
                .collect(Collectors.groupingBy(RoadAccident::getRoadSurfaceConditions,counting()));
        return countByRoadSurfaceCond ;
    }

    /**
     * To match streaming operations result, return type is a java collection instead of multimap
     * @return
     */
    public Map<String, List<String>> getAccidentIdsGroupedByAuthority(){
        Map< String , List<String>>  accidentIdsByAuthority=roadAccidentList.stream()
                .collect(Collectors.groupingBy(RoadAccident::getDistrictAuthority,
                        Collectors.mapping(RoadAccident::getAccidentId,
                                Collectors.toList())));
        return accidentIdsByAuthority;
    }

}
