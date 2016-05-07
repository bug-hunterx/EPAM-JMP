package com.epam.processor;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

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
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * This is to be completed by mentees
 */
public class DataProcessor {

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
		RoadAccident roadAccident = null;
		if (roadAccidentList != null && roadAccidentList.size() > 0) {
			for (RoadAccident accident : roadAccidentList) {
				if (accident.getAccidentId() != null
						&& accident.getAccidentId().equals(index)) {
					roadAccident = accident;
					break;
				}
			}
		}
		return roadAccident;
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
		Collection<RoadAccident> colls = null;
		if (roadAccidentList != null && roadAccidentList.size() > 0) {
			colls = new ArrayList<RoadAccident>();
			for (RoadAccident accident : roadAccidentList) {
				if (isItudeInscpe(accident.getLongitude(),minLongitude,maxLongitude) 
						&& isItudeInscpe(accident.getLatitude(),minLatitude,maxLatitude)) {
					colls.add(accident);
				}
			}
		}
		return colls;
	}
	
	public boolean isItudeInscpe(float itude,float minBound,float maxBound){
			boolean result = false;
			if(itude<=maxBound && itude>=minBound){
				result= true;
			}
			return result;
	}

	/**
	 * count incidents by road surface conditions ex: wet -> 2 dry -> 5
	 * 
	 * @return
	 */
	public Map<String, Long> getCountByRoadSurfaceCondition7() {
		//roadSurfaceConditions
		Map<String, Long> resultMap = null;
		
		if (roadAccidentList != null && roadAccidentList.size() > 0) {
			resultMap = new HashMap<String, Long>(); 
			Multimap<String, RoadAccident> multiMap = ArrayListMultimap.create();
			for (RoadAccident accident : roadAccidentList) {
				multiMap.put(accident.getRoadSurfaceConditions(),
						accident);				
			}			
			for(String key:multiMap.keySet()){
				resultMap.put(key, new Long(multiMap.get(key).size()));
			}
		}
		
		return resultMap;
		
		
		
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
		List<String> result = null;
		if (roadAccidentList != null && roadAccidentList.size() > 0) {
			result = new ArrayList<String>();
			Map<String, Long> counterMap = new HashMap<String, Long>();			//
			Multimap<String, RoadAccident> multiMap = ArrayListMultimap.create();
			for (RoadAccident accident : roadAccidentList) {
				multiMap.put(accident.getWeatherConditions(),
						accident);				
			}
			
			for(String key:multiMap.keySet()){
				counterMap.put(key, new Long(multiMap.get(key).size()));
			}
			
			List<Map.Entry<String, Long>> entryList = new ArrayList<Map.Entry<String, Long>>();
			entryList.addAll(counterMap.entrySet());	
			
			Collections.sort(entryList,new Comparator<Map.Entry<String,Long>>(){
				@Override
				public int compare(Entry<String, Long> o1,
						Entry<String, Long> o2) {						
					return o2.getValue().compareTo(o1.getValue());
					//return o1.getValue().compareTo(o2.getValue());
				}				
			});
			
			
			List<Entry<String, Long>> targetEntryList = null;
			if(entryList.size()>=3){
				targetEntryList = entryList.subList(0, 3);
			}else{
				targetEntryList = entryList.subList(0, entryList.size());
			}
			for(Entry<String, Long> entry:targetEntryList){
				result.add(entry.getKey());
			 }
			
//			int length = entryList.size();
//					
//			result.add(entryList.get(length-1).getKey());
//			
//			if(entryList.size()>=3){				
//				result.add(entryList.get(length-2).getKey());
//				result.add(entryList.get(length-3).getKey());
//			}else if(entryList.size()>=2){				
//				result.add(entryList.get(length-2).getKey());
//			}
			
		}
		return result;
	}

	/**
	 * return a multimap where key is a district authority and values are
	 * accident ids ex: authority1 -> id1, id2, id3 authority2 -> id4, id5
	 * 
	 * @return
	 */
	public Multimap<String, String> getAccidentIdsGroupedByAuthority7() {
		Multimap<String, String> multiMap = null;
		if (roadAccidentList != null && roadAccidentList.size() > 0) {
			//multiMap = ArrayListMultimap.create(); // ArrayListMultimap allows
													// duplication key,while
													// HashMultimap doesn't
													// allow
			multiMap = HashMultimap.create();
			for (RoadAccident accident : roadAccidentList) {
				multiMap.put(accident.getDistrictAuthority(),
						accident.getAccidentId());				
			}
		}
		return multiMap;
	}

	// Now let's do same tasks but now with streaming api

	public RoadAccident getAccidentByIndex(String index) {		
		return roadAccidentList.stream()
				.filter(accident -> accident.getAccidentId().equals(index))
				.findFirst().orElse(null);

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
		return roadAccidentList.stream().filter(accident-> accident.getLongitude() <= maxLongitude
						&& accident.getLongitude() >= minLongitude
						&& accident.getLatitude() <= maxLatitude
						&& accident.getLatitude() >= minLatitude).collect(toList());		
	}

	/**
	 * find the weather conditions which caused max number of incidents
	 * 
	 * @return
	 */
	public List<String> getTopThreeWeatherCondition() {		
		Map<String, Long> weatherCountMap = roadAccidentList.stream().collect(groupingBy(RoadAccident::getWeatherConditions,counting()));		
		return weatherCountMap.entrySet().stream().sorted((o1,o2)->o2.getValue().compareTo(o1.getValue())).map(entry->entry.getKey()).limit(3).collect(Collectors.toList());
	}

	/**
	 * count incidents by road surface conditions
	 * 
	 * @return
	 */
	public Map<String, Long> getCountByRoadSurfaceCondition() {		
		return roadAccidentList.stream().collect(groupingBy(RoadAccident::getRoadSurfaceConditions,counting()));
	
	}

	/**
	 * To match streaming operations result, return type is a java collection
	 * instead of multimap
	 * 
	 * @return
	 */
	public Map<String, List<String>> getAccidentIdsGroupedByAuthority() {		
		return roadAccidentList.stream().collect(groupingBy(RoadAccident::getDistrictAuthority,mapping(RoadAccident::getAccidentId, toList())));
	}

}
