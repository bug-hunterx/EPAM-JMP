package com.epam.processor;

import com.epam.data.RoadAccident;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;

import java.util.*;
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
        for (RoadAccident roadAccident : roadAccidentList) {
            if (roadAccident.getAccidentId().equals(index)) {
                return roadAccident;
            }
        }
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

        Collection<RoadAccident> roadAccidentCollection = new ArrayList<RoadAccident>();
        for (RoadAccident roadAccident : roadAccidentList) {
            if (roadAccident.getLongitude() >= minLongitude
                    && roadAccident.getLongitude() <= maxLongitude
                    && roadAccident.getLatitude() >= minLatitude
                    && roadAccident.getLatitude() <= maxLatitude) {
                roadAccidentCollection.add(roadAccident);
            }
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
    public Map<String, Long> getCountByRoadSurfaceCondition7(){

        Map<String, Long> counts = new HashMap<String, Long>();
        for (RoadAccident roadAccident : roadAccidentList) {
            String surfaceCondition = roadAccident.getRoadSurfaceConditions();
            Long count = counts.get(surfaceCondition);
            if (count == null) {
                counts.put(surfaceCondition, (long) 1);
            }
            else {
                counts.put(surfaceCondition, count + 1);
            }
        }

        return counts;
    }

    /**
     * find the weather conditions which caused the top 3 number of incidents
     * as example if there were 10 accidence in rain, 5 in snow, 6 in sunny and 1 in foggy, then your result list should contain {rain, sunny, snow} - top three in decreasing order
     * @return
     */
    public List<String> getTopThreeWeatherCondition7(){

        Map<String, Long> map = new HashMap<String, Long>();
        for (RoadAccident roadAccident : roadAccidentList) {
            String weatherCondition = roadAccident.getWeatherConditions();
            Long count = map.get(weatherCondition);
            if (count == null) {
                map.put(weatherCondition, (long) 1);
            }
            else {
                map.put(weatherCondition, count + 1);
            }
        }
/*
        List<String> topThreeList = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            Long value = Collections.max(counts.values());
            for (Map.Entry<String, Long> count : counts.entrySet()) {
                if (count.getValue().equals(value)) {
                    String key = count.getKey();
                    topThreeList.add(key);
                }
            }
            counts.remove(topThreeList.get(i));
        }

        return topThreeList;
*/

        // Convert Map to List
        List<Map.Entry<String, Long>> list =  new LinkedList<Map.Entry<String, Long>>(map.entrySet());

        // Sort List with comparator, to compare the Map values
        Collections.sort(list, new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // Get the top 3 conditions
        List<String> topThreeList = new ArrayList<String>();
        for (Map.Entry<String, Long> entry: list.subList(0, 3)) {
            topThreeList.add(entry.getKey());
        }

        return topThreeList;
    }

    /**
     * return a multimap where key is a district authority and values are accident ids
     * ex:
     * authority1 -> id1, id2, id3
     * authority2 -> id4, id5
     * @return
     */
    public Multimap<String, String> getAccidentIdsGroupedByAuthority7(){
        ListMultimap<String, String> multimap = ArrayListMultimap.create();
        for (RoadAccident roadAccident : roadAccidentList) {
            multimap.put(roadAccident.getDistrictAuthority(), roadAccident.getAccidentId());
        }
        return  multimap;
    }


    // Now let's do same tasks but now with streaming api



    public RoadAccident getAccidentByIndex(String index){
        return roadAccidentList
                .stream()
                .filter(roadAccident -> roadAccident.getAccidentId().equals(index))
                .findAny()
                .orElse(null);
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
        return roadAccidentList
                .stream()
                .filter(roadAccident -> roadAccident.getLongitude() >= minLongitude
                                        && roadAccident.getLongitude() <= maxLongitude
                                        && roadAccident.getLatitude() >= minLatitude
                                        && roadAccident.getLatitude() <= maxLatitude)
                .collect(Collectors.toList());
    }

    /**
     * find the weather conditions which caused max number of incidents
     * @return
     */
    public List<String> getTopThreeWeatherCondition(){
        return roadAccidentList
                .stream()
                .collect(Collectors.groupingBy(RoadAccident::getWeatherConditions, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry<String, Long>::getKey)
                .collect(Collectors.toList());
    }

    /**
     * count incidents by road surface conditions
     * @return
     */
    public Map<String, Long> getCountByRoadSurfaceCondition(){
           return roadAccidentList
                    .stream()
                    .collect(Collectors.groupingBy(RoadAccident::getRoadSurfaceConditions, Collectors.counting()));
    }

    /**
     * To match streaming operations result, return type is a java collection instead of multimap
     * @return
     */
    public Map<String, List<String>> getAccidentIdsGroupedByAuthority(){
        return roadAccidentList
                .stream()
                .collect(Collectors.groupingBy(RoadAccident::getDistrictAuthority, Collectors.mapping(RoadAccident::getAccidentId, Collectors.toList())));
    }
}
