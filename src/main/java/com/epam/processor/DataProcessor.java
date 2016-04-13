package com.epam.processor;

import com.epam.data.RoadAccident;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    	for(RoadAccident roadAccident : roadAccidentList) {
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
        Collection<RoadAccident> roadAccidents = new ArrayList<>();
        for(RoadAccident roadAccident : roadAccidentList) {
        	if (roadAccident.getLongitude() >= minLongitude && roadAccident.getLongitude() <= maxLongitude &&
        			roadAccident.getLatitude() >= minLatitude && roadAccident.getLatitude() <= maxLatitude) {
        		roadAccidents.add(roadAccident);
        	}
        }
    	return roadAccidents;
    }

    /**
     * count incidents by road surface conditions
     * ex:
     * wet -> 2
     * dry -> 5
     * @return
     */
    public Map<String, Long> getCountByRoadSurfaceCondition7(){
    	Map<String, Long> roadAccidentMaps = new HashMap<String, Long>();
    	
    	for(RoadAccident roadAccident : roadAccidentList) {
    		if (!roadAccidentMaps.containsKey(roadAccident.getRoadSurfaceConditions())) {
    			roadAccidentMaps.put(roadAccident.getRoadSurfaceConditions(), 1L);
    		}
    		else {
    			roadAccidentMaps.put(roadAccident.getRoadSurfaceConditions(), roadAccidentMaps.get(roadAccident.getRoadSurfaceConditions()) + 1);
    		}
    	}
    	return roadAccidentMaps;
    }

    /**
     * find the weather conditions which caused the top 3 number of incidents
     * as example if there were 10 accidence in rain, 5 in snow, 6 in sunny and 1 in foggy, then your result list should contain {rain, sunny, snow} - top three in decreasing order
     * @return
     */
    public List<String> getTopThreeWeatherCondition7(){
    	List<String> top3WeatherCondition = new ArrayList<>();
    	for(RoadAccident roadAccident : roadAccidentList) {
    		if (!top3WeatherCondition.contains(roadAccident.getWeatherConditions())) {
    			top3WeatherCondition.add(roadAccident.getWeatherConditions());
    			if (top3WeatherCondition.size() == 3 ) {
    				break;
    			}
    		}
    	}
    	
    	return top3WeatherCondition;
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
        
        for(RoadAccident roadAccident : roadAccidentList) {
        	multimap.put(roadAccident.getDistrictAuthority(), roadAccident.getAccidentId());
        }
    	return multimap;
    }


    // Now let's do same tasks but now with streaming api



    public RoadAccident getAccidentByIndex(String index){
    	List<RoadAccident> roadAccidents = roadAccidentList.stream().filter( roadAccident -> roadAccident.getAccidentId().equals(index)).collect(Collectors.toList());
    	return roadAccidents.isEmpty() ? null : roadAccidents.get(0); 
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
    	Collection<RoadAccident> roadAccidents = roadAccidentList.stream().filter(x -> x.getLongitude() >= minLongitude && x.getLongitude() <= maxLongitude && 
    			x.getLatitude() >= minLatitude && x.getLatitude() <= maxLatitude).collect(Collectors.toList());
    	
    	return roadAccidents;
    }

    /**
     * find the weather conditions which caused max number of incidents
     * @return
     */
    public List<String> getTopThreeWeatherCondition(){
    	List<String> top3WeatherCondition = roadAccidentList.stream().map(x -> x.getWeatherConditions()).distinct().limit(3).collect(Collectors.toList());
    	return top3WeatherCondition;
    }

    /**
     * count incidents by road surface conditions
     * @return
     */
    public Map<String, Long> getCountByRoadSurfaceCondition(){
    	Map<String, Long> roadAccidents = roadAccidentList.stream().collect(Collectors.groupingBy(RoadAccident::getRoadSurfaceConditions, Collectors.counting()));

        return roadAccidents;
    }

    /**
     * To match streaming operations result, return type is a java collection instead of multimap
     * @return
     */
    public Map<String, List<String>> getAccidentIdsGroupedByAuthority(){
    	Map<String, List<String>> roadAccidentMaps = roadAccidentList.stream().collect(Collectors.groupingBy(RoadAccident::getDistrictAuthority, Collectors.mapping(RoadAccident::getAccidentId, Collectors.toList())));
        return roadAccidentMaps;
    }

}
