package com.epam.processor;

import com.epam.data.RoadAccident;
import com.google.common.base.*;
import com.google.common.collect.*;

import java.util.*;
import java.util.Optional;
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
        Iterator<RoadAccident> iter = roadAccidentList.iterator();
        while(iter.hasNext()) {
            RoadAccident rec = iter.next();
            if (rec.getAccidentId().equals(index))
                return rec;
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
        List<RoadAccident> result = new ArrayList<>();
        for (RoadAccident rec : roadAccidentList ) {
            float longitude = rec.getLongitude();
            float latitude = rec.getLatitude();
            if (longitude>=minLongitude && longitude<=maxLongitude && latitude>=minLatitude && latitude<=maxLatitude )
                result.add(rec);
        }
//        System.out.println("Total records: " + result.size());
        return result;
    }

    /**
     * count incidents by road surface conditions
     * ex:
     * wet -> 2
     * dry -> 5
     * @return
     */
    public Map<String, Long> getCountByRoadSurfaceCondition7(){
        Map<String, Long> result = new HashMap<>();
        Multiset<String> roadSurfaceCondition = HashMultiset.create();
        for (RoadAccident rec : roadAccidentList ) {
            roadSurfaceCondition.add(rec.getRoadSurfaceConditions());
        }
        for (String key : roadSurfaceCondition.elementSet() ) {
//            System.out.println(key+" count："+roadSurfaceCondition.count(key));
            result.put(key, Long.valueOf(roadSurfaceCondition.count(key)));
        }
        return result;
    }

    /**
     * find the weather conditions which caused the top 3 number of incidents
     * as example if there were 10 accidence in rain, 5 in snow, 6 in sunny and 1 in foggy, then your result list should contain {rain, sunny, snow} - top three in decreasing order
     * @return
     */
    public List<String> getTopThreeWeatherCondition6(){

        Multiset<String> weatherConditions = HashMultiset.create();
        for (RoadAccident rec : roadAccidentList ) {
            weatherConditions.add(rec.getWeatherConditions());
        }
        ImmutableSortedMap.Builder<String, String> builder = new ImmutableSortedMap
                .Builder<String, String>(Ordering.natural().reverse());
        for (String key : weatherConditions.elementSet() ) {
            String countStr = String.format("%08d %s", weatherConditions.count(key), key);
            System.out.println(countStr);
            builder.put(countStr, key);
        }
        ImmutableSortedMap<String, String> sortedConditions = builder.build();
        List<String> result = sortedConditions.values().asList();
//        System.out.println(result);
        return result.subList(0,3);
    }

    public List<String> getTopThreeWeatherCondition7(){
        Multiset<String> weatherConditions = HashMultiset.create();
        for (RoadAccident rec : roadAccidentList ) {
            weatherConditions.add(rec.getWeatherConditions());
        }
        // If no duplicate count value for WeatherCondition, we can use BiMap
        Multimap<Integer, String> sortedConditions = TreeMultimap.create(Ordering.natural().reverse(), Ordering.natural());
        for (String key : weatherConditions.elementSet() ) {
            sortedConditions.put(weatherConditions.count(key), key);
        }
//        System.out.println(sortedConditions);
        List<String> result = ImmutableList.copyOf(sortedConditions.values());
        System.out.println(result);
        return result.subList(0,3);
    }

    /**
     * return a multimap where key is a district authority and values are accident ids
     * ex:
     * authority1 -> id1, id2, id3
     * authority2 -> id4, id5
     * @return
     */
    public Multimap<String, String> getAccidentIdsGroupedByAuthority7(){
        ImmutableMultimap.Builder<String, String> builder = new ImmutableMultimap.Builder<String, String>();
        for (RoadAccident rec : roadAccidentList ) {
            builder.put(rec.getDistrictAuthority(), rec.getAccidentId());
        }
        ImmutableMultimap<String, String> result = builder.build();
/*
        for (String key : result.keySet()) {
            System.out.println(key + " : " + result.get(key).size());
        }
*/
        return result;
    }


    // Now let's do same tasks but now with streaming api



    public RoadAccident getAccidentByIndex(String index){
        Optional<RoadAccident> result = roadAccidentList.stream()
                .filter(r -> r.getAccidentId().equals(index))
                .findFirst();

        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
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
                .filter(r -> r.getLongitude()>=minLongitude && r.getLongitude()<=maxLongitude
                        && r.getLatitude()>=minLatitude && r.getLatitude()<=maxLatitude )
                .collect(Collectors.toList());
    }

    /**
     * find the weather conditions which caused max number of incidents
     * @return
     */
    public List<String> getTopThreeWeatherCondition(){
        Map<String, Long> weatherCondition = roadAccidentList.stream()
                .collect(Collectors.groupingBy(RoadAccident::getWeatherConditions,
                        Collectors.counting()));
//        System.out.println(weatherCondition);

        // Assume no duplicate value
        TreeMap<Long, String> sCounter = new TreeMap<>(Ordering.natural().reverse());
        sCounter.putAll(HashBiMap.create(weatherCondition).inverse());
//        System.out.println(sCounter);
        return  sCounter.values().stream().limit(3).collect(Collectors.toList());
    }

    /**
     * count incidents by road surface conditions
     * @return
     */
    public Map<String, Long> getCountByRoadSurfaceCondition(){
        Map<String, Long> roadSurfaceCondition = roadAccidentList.stream()
                .collect(Collectors.groupingBy(RoadAccident::getRoadSurfaceConditions,
                        Collectors.counting()));

        return roadSurfaceCondition;
    }

    /**
     * To match streaming operations result, return type is a java collection instead of multimap
     * @return
     */
    public Map<String, List<String>> getAccidentIdsGroupedByAuthority(){
        Map<String, List<String>> authority = roadAccidentList.stream()
                .collect(Collectors.groupingBy(RoadAccident::getDistrictAuthority,
                        Collectors.mapping(RoadAccident::getAccidentId, Collectors.toList())));

        return authority;
    }

}
