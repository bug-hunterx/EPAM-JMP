package com.epam.processor;

import com.epam.data.RoadAccident;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

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
        if(!roadAccidentList.isEmpty()) {
            for (RoadAccident roadAccident : roadAccidentList) {
                if (roadAccident.getAccidentId().equals(index)) {
                    return roadAccident;
                }
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
        Collection<RoadAccident> filteredBylocation= new ArrayList<RoadAccident>();
        for(RoadAccident roadAccident:roadAccidentList){
            float longitude = roadAccident.getLongitude();
            float latitude = roadAccident.getLatitude();
            if(longitude>=minLongitude && longitude<=maxLongitude && latitude>=minLatitude && latitude<=maxLatitude) {
                filteredBylocation.add(roadAccident);
            }
        }
        //System.out.println("There are "+filteredBylocation.size()+" accidents in [minLongitude, maxLongitude, minLatitude, maxLatitude] :" +"["+ minLongitude+", "+ maxLongitude+", "+ minLatitude+", "+maxLatitude+"]");
        return filteredBylocation;
    }

    /**
     * count incidents by road surface conditions
     * ex:
     * wet -> 2
     * dry -> 5
     * @return
     */
    public Map<String, Long> getCountByRoadSurfaceCondition7(){
        Map<String,Long> countByRoadConditions= Maps.newHashMap();
        for(RoadAccident roadAccident:roadAccidentList){
            String roadSurfaceConditions = roadAccident.getRoadSurfaceConditions();
            if(countByRoadConditions.containsKey(roadSurfaceConditions)) {
                countByRoadConditions.put(roadSurfaceConditions,countByRoadConditions.get(roadSurfaceConditions) + 1L);
            }else{
                countByRoadConditions.put(roadSurfaceConditions,1L);
            }
        }
        return countByRoadConditions;
    }

    /**
     * find the weather conditions which caused the top 3 number of incidents
     * as example if there were 10 accidence in rain, 5 in snow, 6 in sunny and 1 in foggy, then your result list should contain {rain, sunny, snow} - top three in decreasing order
     * @return
     */
    public List<String> getTopThreeWeatherCondition7(){

        Map countByRoadConditions = getCountByRoadSurfaceCondition7();
        List<String> weatherConditions= new ArrayList<String>();
        weatherConditions.addAll(countByRoadConditions.keySet());
        Collections.sort(weatherConditions, new Comparator<String>() {
                public int compare(String w1, String w2) {
                    Long l1 = (Long) countByRoadConditions.get(w1);
                    Long l2 = (Long) countByRoadConditions.get(w2);
                    return (int)(l2 - l1);
                }
        });
        return weatherConditions.subList(0,3);
    }

    /**
     * return a multimap where key is a district authority and values are accident ids
     * ex:
     * authority1 -> id1, id2, id3
     * authority2 -> id4, id5
     * @return
     */
    public Multimap<String, String> getAccidentIdsGroupedByAuthority7(){
        ListMultimap<String,String> groupedByAuthority = ArrayListMultimap.create();
        for(RoadAccident roadAccident:roadAccidentList){
            if(roadAccident.getDistrictAuthority() != null){
                groupedByAuthority.put(roadAccident.getDistrictAuthority(),roadAccident.getAccidentId());
            }
        }
        return groupedByAuthority;
    }


    // Now let's do same tasks but now with streaming api



    public RoadAccident getAccidentByIndex(String index){
        RoadAccident roadAccident = roadAccidentList.stream()
                .filter( rdAcc->{
                    return rdAcc.getAccidentId().equals(index) ;
                }).findFirst().get();
               // .forEach(s ->System.out.println("forEach: " +s.getAccidentId()+s.getAccidentSeverity()));;
        return roadAccident;
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
        Collection<RoadAccident> filteredBylocation = roadAccidentList.stream()
                .filter( rdAcc ->{
                    float longitude = rdAcc.getLongitude();
                    float latitude = rdAcc.getLatitude();
                    return  longitude>=minLongitude && longitude<=maxLongitude && latitude>=minLatitude && latitude<=maxLatitude;
                }).collect(Collectors.toList());
        return filteredBylocation;
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
        Map<String,Long> countByRoadConditions= Maps.newHashMap();
        countByRoadConditions = roadAccidentList.stream()
                .collect(groupingBy(RoadAccident::getRoadSurfaceConditions, Collectors.counting()));
        return countByRoadConditions;
    }

    /**
     * To match streaming operations result, return type is a java collection instead of multimap
     * @return
     */
    public Map<String, List<String>> getAccidentIdsGroupedByAuthority(){
        /*Map<Integer, List<String>> peopleByAge = people
                .collect(groupingBy(p -> p.age, mapping((Person p) -> p.name, toList())));*/
      /*  Map<String, List<String>> accidentIdsGroupedByAuthority = Stream.of(roadAccidentList)
                //.collect(Collectors.groupingBy(Function.identity(), Collectors. ));
                .collect(groupingBy(r -> r.districtAuthority, mapping((RoadAccident r) -> r.accidentId, toList())));*/
        return null;
    }

}
