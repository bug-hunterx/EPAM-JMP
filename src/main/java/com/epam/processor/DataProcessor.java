package com.epam.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.epam.data.RoadAccident;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * This is to be completed by sam pang
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
        for(RoadAccident ra : roadAccidentList){
        	if(ra.getAccidentId().equals(index)){
        		return ra;
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
        List<RoadAccident> list = new ArrayList<>();
        for(RoadAccident ra : roadAccidentList){
        	if(ra.getLongitude() >= minLongitude 
        			&& ra.getLongitude() <= maxLongitude
        			&& ra.getLatitude() >= minLatitude
        			&& ra.getLatitude() <= maxLatitude){
        		list.add(ra);
        	}
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
        Map<String, Long> map = new HashMap<>();
        
        for(RoadAccident ra : roadAccidentList){
        	long count = 0l;
        	if(map.get(ra.getRoadSurfaceConditions()) != null){
              	count = map.get(ra.getRoadSurfaceConditions());             	
        	}
   
        	map.put(ra.getRoadSurfaceConditions(),++count);
        }
               
        return map;
    }

    /**
     * find the weather conditions which caused the top 3 number of incidents
     * as example if there were 10 accidence in rain, 5 in snow, 6 in sunny and 1 in foggy, then your result list should contain {rain, sunny, snow} - top three in decreasing order
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<String> getTopThreeWeatherCondition7(){  	
    	String[] weatherCondition = new String[3];
    	Map<String,Long> map = new HashMap<String,Long>(); 
    	
    	for(RoadAccident ra : roadAccidentList){
        	long count = 0l;
        	if(map.get(ra.getWeatherConditions()) != null){
              	count = map.get(ra.getWeatherConditions());             	
        	}
   
        	map.put(ra.getWeatherConditions(),++count);
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
     * return a multimap where key is a district authority and values are accident ids
     * ex:
     * authority1 -> id1, id2, id3
     * authority2 -> id4, id5
     * @return
     */
    public Multimap<String, String> getAccidentIdsGroupedByAuthority7(){
    	Multimap<String, String> multiMap = ArrayListMultimap.create();
    	for(RoadAccident ra : roadAccidentList){
    		multiMap.put(ra.getDistrictAuthority(), ra.getAccidentId());
    	}
    	
        return multiMap;
    }


    // Now let's do same tasks but now with streaming api



    public RoadAccident getAccidentByIndex(String index){  	
        return roadAccidentList.stream()
        			.filter(ra -> ra.getAccidentId().equals(index))
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
        return roadAccidentList.stream()
        		.filter(ra -> ra.getLongitude() >= minLongitude && ra.getLongitude() <= maxLongitude)
        		.filter(ra -> ra.getLatitude() >= minLatitude && ra.getLatitude() <= maxLatitude)
        		.collect(Collectors.toList());
    }

    /**
     * find the weather conditions which caused max number of incidents
     * @return
     */
    public List<String> getTopThreeWeatherCondition(){   	
    	Map<String, Long> map = roadAccidentList.stream()
    								.map(RoadAccident::getWeatherConditions)
    								.collect(Collectors.groupingBy(
    										item -> item,
    										Collectors.counting()
    								));
    	
    			return map.entrySet().stream()
    					.sorted((val1,val2) -> val2.getValue().compareTo(val1.getValue()))
    					.map(Map.Entry::getKey)
    					.limit(3)
    					.collect(Collectors.toList());
    }

    /**
     * count incidents by road surface conditions
     * @return
     */
    public Map<String, Long> getCountByRoadSurfaceCondition(){ 
    	return roadAccidentList.stream()
    			.map(RoadAccident::getRoadSurfaceConditions)
    			.collect(Collectors.groupingBy(
							item -> item,
							Collectors.counting()
    			));	
    }

    /**
     * To match streaming operations result, return type is a java collection instead of multimap
     * @return
     */
    public Map<String, List<String>> getAccidentIdsGroupedByAuthority(){  	
    	return roadAccidentList.stream().collect(
    				Collectors.groupingBy(
    						RoadAccident::getDistrictAuthority,
    						Collectors.mapping(RoadAccident::getAccidentId, Collectors.toList())		
    		   ));
    }

}
