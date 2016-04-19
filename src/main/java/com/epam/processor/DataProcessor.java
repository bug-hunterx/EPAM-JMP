package com.epam.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.epam.data.RoadAccident;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * This is to be completed by sam pang
 */
public class DataProcessor {

    private final List<RoadAccident> roadAccidentList;

    public DataProcessor(List<RoadAccident> roadAccidentList){
        this.roadAccidentList = roadAccidentList;
    }

    public static void main(String[] args) {
    	
    	Multimap<String, String> multiMap = HashMultimap.create();
    	
    	multiMap.put("1","1");
    	multiMap.put("1","2");
    	multiMap.put("1","3");
    	multiMap.put("1","4");
    	multiMap.put("1","5");
    	multiMap.put("2","1");
    	multiMap.put("2","2");
    	multiMap.put("2","3");
    	multiMap.put("2","4");
    	multiMap.put("2","5");
    	  
    	System.out.println( multiMap.keySet().size());
    	System.out.println( multiMap.get("1").size());
//    	System.out.println(multiMap);
    	
//		class A{
//			private String name;
//			public A(String name){
//				this.name = name;
//			}
//			public String getName() {
//				return name;
//			}
//
//			public void setName(String name) {
//				this.name = name;
//			}
//		}
//    	
//    	List<A> list = new ArrayList<>();
//    	list.add(new A("Sam"));
//    	list.add(new A("Sam"));
//    	list.add(new A("Sam"));
//    	list.add(new A("Sam"));
//    	list.add(new A("Sam"));
//    	list.add(new A("Tom"));
//    	list.add(new A("Tom"));
//    	list.add(new A("Tom"));
//    	list.add(new A("Jim"));
//    	list.add(new A("Jim"));
//    	list.add(new A("Jimy"));
//    	list.add(new A("Jimy"));
//    	list.add(new A("Jimy"));
//    	list.add(new A("Jimy"));
//    	
//    	Map<String, Long> map = list.stream()
//				.map(A::getName)
//				.collect(Collectors.groupingBy(
//						item -> item,
//						Collectors.counting()
//				));
//
//    	List<String> list2 = map.entrySet().stream()
//    							.sorted((val1,val2) -> val2.getValue().compareTo(val1.getValue()))
//    							.map(Map.Entry::getKey)
//    							.limit(3)
//    							.collect(Collectors.toList());
//    	
//    	list2.forEach(e -> System.out.println(e));
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
        long dry = 0, wet = 0;
        
        for(RoadAccident ra : roadAccidentList){
        	if(ra.getRoadSurfaceConditions().equals("2") || ra.getRoadSurfaceConditions().equalsIgnoreCase("wet")){
        		wet++;
        	}else if(ra.getRoadSurfaceConditions().equals("5") || ra.getRoadSurfaceConditions().equalsIgnoreCase("dry")){
        		dry++;
        	}
        }
        
        map.put("Dry", dry);
        map.put("Wet", wet);
        
        return map;
    }

    /**
     * find the weather conditions which caused the top 3 number of incidents
     * as example if there were 10 accidence in rain, 5 in snow, 6 in sunny and 1 in foggy, then your result list should contain {rain, sunny, snow} - top three in decreasing order
     * @return
     */
    public List<String> getTopThreeWeatherCondition7(){  	
    	List<String> list = new LinkedList<>();
    	List<Long> countList = new LinkedList<>();
    	Multimap<String, Long> multiMap = HashMultimap.create(); 	    	
    	Long count = 0l;
    	
    	for(RoadAccident ra : roadAccidentList){
    		multiMap.put(ra.getWeatherConditions(), count++);
    	}
    	
    	for(int i = 0;i < 2;i++){
    		countList.add(0l);
    	}
    	    	
    	for(String key : multiMap.keySet()){
    		long size = multiMap.get(key).size();
    		if(size > countList.get(0)){
    			list.add(0, key);
    			countList.add(0, size);
    		}else if(size > countList.get(1)){
    			list.add(1, key);
    			countList.add(1, size);
    		}else if(size > countList.get(2)){
    			list.add(2, key);
    			countList.add(2, size);
    		} 		
    		if(list.size() == 4){
    			list.remove(3);
    		}
    	}
    	    	
    	return list;
    }

    /**
     * return a multimap where key is a district authority and values are accident ids
     * ex:
     * authority1 -> id1, id2, id3
     * authority2 -> id4, id5
     * @return
     */
    public Multimap<String, String> getAccidentIdsGroupedByAuthority7(){
    	Multimap<String, String> multiMap = HashMultimap.create();
    	long count = 0l;
    	for(RoadAccident ra : roadAccidentList){
    		multiMap.put(ra.getDistrictAuthority(), ra.getAccidentId() + count++);
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
    	List<String> list = new ArrayList<>();
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
    	Map<String, Long> map = new HashMap<>();
    	
    	long dry = roadAccidentList.stream().filter(ra -> ra.getRoadSurfaceConditions().equals("5") || ra.getRoadSurfaceConditions().equalsIgnoreCase("dry")).count();
    	long wet = roadAccidentList.stream().filter(ra -> ra.getRoadSurfaceConditions().equals("2") || ra.getRoadSurfaceConditions().equalsIgnoreCase("wet")).count();
    	
    	map.put("Dry", dry);
        map.put("Wet", wet);
    	return map;  	
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
