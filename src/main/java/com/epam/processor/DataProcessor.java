package com.epam.processor;

import com.epam.data.AccidentsDataLoader;
import com.epam.data.RoadAccident;
import com.epam.data.RoadAccidentBuilder;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This is to be completed by mentees
 */
public class DataProcessor {
	private static final String ACCIDENTS_CSV = "src/main/resources/DfTRoadSafety_Accidents_2009.csv";

	private final List<RoadAccident> roadAccidentList;

	public DataProcessor(List<RoadAccident> roadAccidentList) {
		this.roadAccidentList = roadAccidentList;
	}

	// First try to solve task using java 7 style for processing collections

	/**
	 * Return road accident with matching index
	 * 
	 * @param index
	 * @return
	 */
	public RoadAccident getAccidentByIndex7(String index) {
		for (RoadAccident roadAccident : roadAccidentList) {
			if (roadAccident.getAccidentId().equals(index)) {
				return roadAccident;
			}
		}
		return null;
	}

	/**
	 * filter list by longtitude and latitude values, including boundaries
	 * 
	 * @param minLongitude
	 * @param maxLongitude
	 * @param minLatitude
	 * @param maxLatitude
	 * @return
	 */
	public Collection<RoadAccident> getAccidentsByLocation7(float minLongitude,
			float maxLongitude, float minLatitude, float maxLatitude) {
		List<RoadAccident> accidentList = new ArrayList<>();
		for (RoadAccident roadAccident : roadAccidentList) {
			if (roadAccident.getLongitude() >= minLongitude
					&& roadAccident.getLongitude() <= maxLongitude
					&& roadAccident.getLatitude() >= minLatitude
					&& roadAccident.getLatitude() <= maxLatitude) {
				accidentList.add(roadAccident);
			}
		}
		return accidentList;
	}

	/**
	 * count incidents by road surface conditions ex: wet -> 2 dry -> 5
	 * 
	 * @return
	 */
	public Map<String, Long> getCountByRoadSurfaceCondition7() {
		Map<String, Long> map = new HashMap<>();
        
        for(RoadAccident roadAccident : roadAccidentList){
        	long count = 0l;
        	if(map.get(roadAccident.getRoadSurfaceConditions()) != null){
              	count = map.get(roadAccident.getRoadSurfaceConditions());             	
        	}
   
        	map.put(roadAccident.getRoadSurfaceConditions(),++count);
        }
               
        return map;
	}

	/**
	 * find the weather conditions which caused the top 3 number of incidents as
	 * example if there were 10 accidence in rain, 5 in snow, 6 in sunny and 1
	 * in foggy, then your result list should contain {rain, sunny, snow} - top
	 * three in decreasing order
	 * 
	 * @return
	 */
	public List<String> getTopThreeWeatherCondition7() {
		String[] weatherCondition = new String[3];
    	Map<String,Long> map = new HashMap<String,Long>(); 
    	
    	for(RoadAccident roadAccident : roadAccidentList){
        	long count = 0l;
        	if(map.get(roadAccident.getWeatherConditions()) != null){
              	count = map.get(roadAccident.getWeatherConditions());             	
        	}
   
        	map.put(roadAccident.getWeatherConditions(),++count);
    	}
   	   	
		List list = new ArrayList(map.entrySet());  
        Collections.sort(list, new Comparator(){  
            public int compare(Object o1, Object o2) {  
                Map.Entry obj1 = (Map.Entry) o1;  
                Map.Entry obj2 = (Map.Entry) o2;  
                return ((Long) obj2.getValue()).compareTo((Long)obj1.getValue());  
                }  
        }); 
    	
        for(int i = 0; i < 3;i++){
        	Map.Entry entry = (Map.Entry)list.get(i);
        	weatherCondition[i] = (String)entry.getKey();
        }
        
    	return  Arrays.asList(weatherCondition);
	}

	/**
	 * return a multimap where key is a district authority and values are
	 * accident ids ex: authority1 -> id1, id2, id3 authority2 -> id4, id5
	 * 
	 * @return
	 */
	public Multimap<String, String> getAccidentIdsGroupedByAuthority7() {
		Multimap<String, String> multiMap = HashMultimap.create();
		long count = 0L;
		for (RoadAccident roadAccident : roadAccidentList) {
			multiMap.put(roadAccident.getDistrictAuthority(),
					roadAccident.getAccidentId() + count++);
		}
		return multiMap;
	}

	// Now let's do same tasks but now with streaming api

	public RoadAccident getAccidentByIndex(String index) {
		RoadAccident roadAccident = roadAccidentList.stream()
				.filter(ra -> ra.getAccidentId().equals(index)).findFirst()
				.orElse(null);
		return roadAccident;
	}

	/**
	 * filter list by longtitude and latitude fields
	 * 
	 * @param minLongitude
	 * @param maxLongitude
	 * @param minLatitude
	 * @param maxLatitude
	 * @return
	 */
	public Collection<RoadAccident> getAccidentsByLocation(float minLongitude,
			float maxLongitude, float minLatitude, float maxLatitude) {
		return roadAccidentList
				.stream()
				.filter(roadAccident -> roadAccident.getLongitude() >= minLongitude
						&& roadAccident.getLongitude() <= maxLongitude)
				.filter(roadAccident -> roadAccident.getLatitude() >= minLatitude
						&& roadAccident.getLatitude() <= maxLatitude)
				.collect(Collectors.toList());
	}

	/**
	 * find the weather conditions which caused max number of incidents
	 * 
	 * @return
	 */
	public List<String> getTopThreeWeatherCondition() {
		List<String> list = new ArrayList<>();
		Map<String, Long> map = roadAccidentList
				.stream()
				.map(RoadAccident::getWeatherConditions)
				.collect(
						Collectors.groupingBy(item -> item,
								Collectors.counting()));

		return map
				.entrySet()
				.stream()
				.sorted((val1, val2) -> val2.getValue().compareTo(
						val1.getValue())).map(Map.Entry::getKey).limit(3)
				.collect(Collectors.toList());
	}

	/**
	 * count incidents by road surface conditions
	 * 
	 * @return
	 */
	public Map<String, Long> getCountByRoadSurfaceCondition() {
		return roadAccidentList.stream()
    			.map(RoadAccident::getRoadSurfaceConditions)
    			.collect(Collectors.groupingBy(
							item -> item,
							Collectors.counting()
    			));	
	}

	/**
	 * To match streaming operations result, return type is a java collection
	 * instead of multimap
	 * 
	 * @return
	 */
	public Map<String, List<String>> getAccidentIdsGroupedByAuthority() {
		return roadAccidentList.stream().collect(
				Collectors.groupingBy(RoadAccident::getDistrictAuthority,
						Collectors.mapping(RoadAccident::getAccidentId,
								Collectors.toList())));
	}

}
