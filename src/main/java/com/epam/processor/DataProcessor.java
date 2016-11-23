package com.epam.processor;

import com.epam.data.RoadAccident;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.Predicate;
import static java.util.stream.Collectors.*;

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
            if ((roadAccidentRecord.getLongitude() >= minLongitude
                    && roadAccidentRecord.getLongitude() <= maxLongitude)
                    && roadAccidentRecord.getLatitude() >= minLatitude
                    && roadAccidentRecord.getLatitude() <= maxLatitude)
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

        Map<String, Long>  roadSurfaceConditionList = new HashMap<String, Long>();
        Multimap<String, RoadAccident> countByRoadSurfecCond = (Multimap) ArrayListMultimap.create();

        for(RoadAccident roadAccident : roadAccidentList) {
            countByRoadSurfecCond.put(roadAccident.getRoadSurfaceConditions(), roadAccident);
        }
        for(String key :countByRoadSurfecCond.keySet() ) {

            roadSurfaceConditionList.put(key, new Long(countByRoadSurfecCond.get(key).size()));
        }
        return roadSurfaceConditionList;
    }

    /**
     * find the weather conditions which caused the top 3 number of incidents
     * as example if there were 10 accidence in rain, 5 in snow, 6 in sunny and 1 in foggy, then your result list should contain {rain, sunny, snow} - top three in decreasing order
     * @return
     */
    public List<String> getTopThreeWeatherCondition7(){
        Multimap<String,String> accidentIdsGrpByWeatherCond = ArrayListMultimap.create();
        List<String> weatherConditionList = new ArrayList<String>();

        for (RoadAccident roadAccident : roadAccidentList) {
            accidentIdsGrpByWeatherCond.put(roadAccident.getWeatherConditions(), roadAccident.getAccidentId());
        }

        List<Map.Entry<String, Collection<String>>> entries =
                new ArrayList<Map.Entry<String, Collection<String>>>(accidentIdsGrpByWeatherCond.asMap().entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Collection<String>>>() {
            @Override
            public int compare(Map.Entry<String, Collection<String>> e1, Map.Entry<String, Collection<String>> e2) {
                return Integer.valueOf(accidentIdsGrpByWeatherCond.get(e2.getKey()).size()).compareTo
                        (accidentIdsGrpByWeatherCond.get(e1.getKey()).size());
            }
        });
        for (Map.Entry<String, Collection<String>> e : entries) {
            weatherConditionList.add(e.getKey());
        }
        return weatherConditionList.subList(0, 3);
    }

    /**
     * return a multimap where key is a district authority and values are accident ids
     * ex:
     * authority1 -> id1, id2, id3
     * authority2 -> id4, id5
     * @return
     */
    public Multimap<String, String> getAccidentIdsGroupedByAuthority7(){
        Multimap<String,String> accidentIdsGrpByAuthority = ArrayListMultimap.create();
        for(RoadAccident accident : roadAccidentList) {
            accidentIdsGrpByAuthority.put(accident.getDistrictAuthority(),accident.getAccidentId());
        }
        return accidentIdsGrpByAuthority;
    }


    // Now let's do same tasks but now with streaming api



    public RoadAccident getAccidentByIndex(String index){
        return roadAccidentList.stream()
                .filter(a -> a.getAccidentId().equals(index))
                .findFirst().orElse(null);
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

        Predicate<RoadAccident> locationByLongitude = s -> s.getLongitude()> minLongitude
                && s.getLongitude()<= maxLongitude;
        Predicate<RoadAccident> locationByLatitude  = s -> s.getLatitude()> minLatitude
                && s.getLatitude()<= maxLatitude;

        List<RoadAccident> accidentListByLocation = roadAccidentList.stream()
                .filter(locationByLongitude.and(locationByLatitude))
                .collect(toList());

        return accidentListByLocation;
    }

    /**
     * find the weather conditions which caused max number of incidents
     * @return
     */
    public List<String> getTopThreeWeatherCondition(){
        Map<String ,Long>  incidentCount=roadAccidentList.stream()
                .collect( groupingBy( RoadAccident::getWeatherConditions,
                        counting()));

        List<String> accidentListByWeatherCond=incidentCount.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(toList());

        return accidentListByWeatherCond;
    }

    /**
     * count incidents by road surface conditions1
     * @return
     */
    public Map<String, Long> getCountByRoadSurfaceCondition(){
        Map<String ,Long>  countByRoadSurfaceCond=roadAccidentList.stream()
                .collect(groupingBy(RoadAccident::getRoadSurfaceConditions,counting()));
        return countByRoadSurfaceCond ;
    }

    /**
     * To match streaming operations result, return type is a java collection instead of multimap
     * @return
     */
    public Map<String, List<String>> getAccidentIdsGroupedByAuthority(){
        Map< String , List<String>>  accidentIdsByAuthority=roadAccidentList.stream()
                .collect(groupingBy(RoadAccident::getDistrictAuthority,
                        mapping(RoadAccident::getAccidentId,
                                toList())));
        return accidentIdsByAuthority;
    }
}
