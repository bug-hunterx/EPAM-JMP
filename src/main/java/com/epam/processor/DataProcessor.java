package com.epam.processor;

import com.epam.data.RoadAccident;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This is to be completed by mentees
 */
public class DataProcessor {

    private final List<RoadAccident> roadAccidentList;
    
    private final static int NUMBER_OF_CONDITION = 3;

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
    	//the roadAccidentList[i] can't be resolved
//    	RoadAccident accident = roadAccidentList[0];
    	for(RoadAccident roadAccident : roadAccidentList) {
    		if(index.equals(roadAccident.getAccidentId())) {
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
        List<RoadAccident> accidentsByLocation = new ArrayList<RoadAccident>();
        for(RoadAccident roadAccident : roadAccidentList) {
        	boolean locationInTargetLongitude = isInTargetLongitudeOrLatitue(roadAccident.getLongitude(), minLongitude, maxLongitude);
        	boolean locationInTargetLatitude = isInTargetLongitudeOrLatitue(roadAccident.getLatitude(), minLatitude, maxLatitude);
        	if(locationInTargetLongitude && locationInTargetLatitude) {
        		accidentsByLocation.add(roadAccident);
        	}
        }
    	return accidentsByLocation;
    }

	private boolean isInTargetLongitudeOrLatitue(float location, float minValue, float maxValue) {
		return location >= minValue && location <= maxValue;
	}

    /**
     * count incidents by road surface conditions
     * ex:
     * wet -> 2
     * dry -> 5
     * @return
     */
    public Map<String, Long> getCountByRoadSurfaceCondition7(){
    	Map<String, Long> countGroupByRoadSurfaceCondition = new HashMap<String, Long>();
		for(RoadAccident roadAccident : roadAccidentList) {
			getCountGroupByCondition(countGroupByRoadSurfaceCondition, roadAccident.getRoadSurfaceConditions());
		}
		return countGroupByRoadSurfaceCondition;
    }


	private void getCountGroupByCondition(Map<String, Long> countGroupByCondition, String condition) {
		Long countByCondition = countGroupByCondition.get(condition);
		if(countByCondition == null) {
			countGroupByCondition.put(condition, 1L);
		} else {
			countGroupByCondition.put(condition, ++countByCondition);
		}
	}
    
    /**
     * find the weather conditions which caused the top 3 number of incidents
     * as example if there were 10 accidence in rain, 5 in snow, 6 in sunny and 1 in foggy, then your result list should contain {rain, sunny, snow} - top three in decreasing order
     * @return
     */
    public List<String> getTopThreeWeatherCondition7(){
    	Map<String, Long> countGroupByWeatherCondition = getCountGroupByWeatherCondition();
    	
    	List<Entry<String, Long>> weatherConditionsByCountInDescOrder = getWeatherConditionsByCountInDescOrder(countGroupByWeatherCondition);
    	
    	return getTop_N_WeatherCondition(weatherConditionsByCountInDescOrder, NUMBER_OF_CONDITION);
    }

	private Map<String, Long> getCountGroupByWeatherCondition() {
		Map<String, Long> countGroupByWeatherCondition = new HashMap<String, Long>();
		for(RoadAccident roadAccident : roadAccidentList) {
			getCountGroupByCondition(countGroupByWeatherCondition, roadAccident.getWeatherConditions());
		}
		return countGroupByWeatherCondition;
	}

	private List<Entry<String, Long>> getWeatherConditionsByCountInDescOrder(Map<String, Long> countGroupByWeatherCondition) {
		List<Entry<String, Long>> countByWeatherCondition = new ArrayList<Entry<String, Long>>(countGroupByWeatherCondition.entrySet());
    	Collections.sort(countByWeatherCondition, new Comparator<Map.Entry<String, Long>>() {

			@Override
			public int compare(Entry<String, Long> weather1, Entry<String, Long> weather2) {
				return (int) (weather2.getValue() - weather1.getValue());
			}
    		
    	});
		return countByWeatherCondition;
	}

	private List<String> getTop_N_WeatherCondition(List<Entry<String, Long>> weatherConditionsByCountInDescOrder, int nubmerOfCondition) {
		List<String> top_N_Weathers = new ArrayList<String>();
		Iterator<Entry<String, Long>> iter = weatherConditionsByCountInDescOrder.subList(0, nubmerOfCondition).iterator();
    	while(iter.hasNext()) {
    		top_N_Weathers.add(iter.next().getKey());
    	}
        return top_N_Weathers;
	}

    /**
     * return a multimap where key is a district authority and values are accident ids
     * ex:
     * authority1 -> id1, id2, id3
     * authority2 -> id4, id5
     * @return
     */
    public Multimap<String, String> getAccidentIdsGroupedByAuthority7(){
    	Multimap<String, String> accidentsGroupByAuthority = ArrayListMultimap.create();
    	for(RoadAccident roadAccident : roadAccidentList) {
    		accidentsGroupByAuthority.put(roadAccident.getDistrictAuthority(), roadAccident.getAccidentId());
    	}
        return accidentsGroupByAuthority;
    }


    // Now let's do same tasks but now with streaming api



    public RoadAccident getAccidentByIndex(String index){
        return roadAccidentList.stream()
    			.filter(aRoadAccident -> aRoadAccident.getAccidentId().equals(index))
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
        return roadAccidentList.stream().filter(accident -> accident.getLongitude() >= minLongitude
        		&& accident.getLongitude() <= maxLongitude
        		&& accident.getLatitude() >= minLatitude
        		&& accident.getLatitude() <= maxLatitude).collect(Collectors.toList());
    }

    /**
     * find the weather conditions which caused max number of incidents
     * @return
     */
    public List<String> getTopThreeWeatherCondition(){
         Map<String, Long> weatherConditionCountMap = roadAccidentList.stream()
        		.collect(Collectors.groupingBy(RoadAccident::getWeatherConditions, Collectors.counting()));
         
         return weatherConditionCountMap.entrySet().stream().sorted(new Comparator<Map.Entry<String, Long>>(){

        	 @Override
			public int compare(Entry<String, Long> weatherCount1, Entry<String, Long> weatherCount2) {
				return (int) (weatherCount2.getValue() - weatherCount1.getValue());
			}
        	 
         }).map(Entry::getKey).collect(Collectors.toList()).subList(0, 3);
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
    			.collect(Collectors.groupingBy(RoadAccident::getDistrictAuthority, Collectors.mapping(RoadAccident::getAccidentId, Collectors.toList())));
    }

}
