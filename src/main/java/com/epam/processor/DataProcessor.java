package com.epam.processor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.epam.data.RoadAccident;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

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
    		if (index.equals(roadAccident.getAccidentId()))
    			return roadAccident;
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
		List<RoadAccident> list = new ArrayList<RoadAccident>();
		for (RoadAccident roadAccident : roadAccidentList) {
			if (roadAccident.getLongitude() > minLongitude && roadAccident.getLongitude() < maxLongitude
					&& roadAccident.getLatitude() > minLatitude && roadAccident.getLatitude() < maxLatitude)
				list.add(roadAccident);
		}
		return list;
    }

    /**
     * count incidents by road surface conditions
     * ex:
     * wet -> 2
     * dry -> 5
     * @return
     */
    public Map<String, Long> getCountByRoadSurfaceCondition7(){
    	Map<String, Long> map = new HashMap<String, Long>();
    	for (RoadAccident roadAccident : roadAccidentList) {
    		Long count = map.get(roadAccident.getRoadSurfaceConditions());
    		if (count == null)
    			map.put(roadAccident.getRoadSurfaceConditions(), 1L);
    		else
    			map.put(roadAccident.getRoadSurfaceConditions(), count + 1);
		}
        return map;
    }

    /**
     * find the weather conditions which caused the top 3 number of incidents
     * as example if there were 10 accidence in rain, 5 in snow, 6 in sunny and 1 in foggy, then your result list should contain {rain, sunny, snow} - top three in decreasing order
     * @return
     */
    public List<String> getTopThreeWeatherCondition7(){
    	Map<String, Integer> map = new HashMap<String, Integer>();
    	for (RoadAccident roadAccident : roadAccidentList) {
    		Integer count = map.get(roadAccident.getWeatherConditions());
    		if (count == null)
    			map.put(roadAccident.getWeatherConditions(), 1);
    		else
    			map.put(roadAccident.getWeatherConditions(), count + 1);
		}
    	
    	List<Integer> valueList = new ArrayList<Integer>();
    	valueList.addAll(map.values());
    	Collections.sort(valueList, new Comparator<Integer>(){
			@Override
			public int compare(Integer arg0, Integer arg1) {
				return arg1 - arg0;
			}
    	});
    	
    	List<String> keyList = new ArrayList<String>();
    	for (Integer value : valueList) {
    		for (String key : map.keySet()) {
    			if (value.equals(map.get(key)))
    				keyList.add(key);
    			if (keyList.size() == 3)
    				return keyList;
    		}
    	}
    	
        return keyList;
    }

    /**
     * return a multimap where key is a district authority and values are accident ids
     * ex:
     * authority1 -> id1, id2, id3
     * authority2 -> id4, id5
     * @return
     */
    public Multimap<String, String> getAccidentIdsGroupedByAuthority7(){
		Multimap<String, String> map = ArrayListMultimap.create();
		for (RoadAccident roadAccident : roadAccidentList) {
			map.put(roadAccident.getDistrictAuthority(), roadAccident.getAccidentId());
		}
		return map;
    }


    // Now let's do same tasks but now with streaming api



    public RoadAccident getAccidentByIndex(String index){
    	List<RoadAccident> list = roadAccidentList.stream().filter(r -> index.equals(r.getAccidentId())).collect(Collectors.toList());
    	if (list.size() > 0)
    		return list.get(0);
        return null;
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
        		.filter(r -> r.getLongitude() > minLongitude).filter(r -> r.getLongitude() < maxLongitude)
        		.filter(r -> r.getLatitude() > minLatitude).filter(r -> r.getLatitude() < maxLatitude)
        		.collect(Collectors.toList());
    }

    /**
     * find the weather conditions which caused max number of incidents
     * @return
     */
    public List<String> getTopThreeWeatherCondition(){
		Map<String, Integer> map = new HashMap<String, Integer>();
		roadAccidentList.stream().map(RoadAccident::getWeatherConditions).forEach(r -> {
			Integer count = map.get(r);
			if (count == null)
				map.put(r, 1);
			else
				map.put(r, count + 1);
		});

		return map.entrySet().stream().sorted(new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return o2.getValue() - o1.getValue();
			}
		}).map(Entry::getKey).collect(Collectors.toList()).subList(0, 3);
    }

    /**
     * count incidents by road surface conditions
     * @return
     */
    public Map<String, Long> getCountByRoadSurfaceCondition(){
    	Map<String, Long> map = new HashMap<String, Long>();
    	roadAccidentList.stream().map(RoadAccident::getRoadSurfaceConditions).forEach(r -> {
    		Long count = map.get(r);
    		if (count == null)
    			map.put(r, 1L);
    		else
    			map.put(r, count + 1);
    	});
    	return map;
    }

    /**
     * To match streaming operations result, return type is a java collection instead of multimap
     * @return
     */
    public Map<String, List<String>> getAccidentIdsGroupedByAuthority(){
    	Map<String, List<String>> map = new HashMap<String, List<String>>();
    	roadAccidentList.stream().forEach(r -> {
    		List<String> list = map.get(r.getDistrictAuthority());
    		if (list == null)
    			list = new ArrayList<String>();
    		list.add(r.getAccidentId());
    		map.put(r.getDistrictAuthority(), list);
    	});
		return map;
    }

}
