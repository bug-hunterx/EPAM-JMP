package com.epam.processor;


import com.epam.data.RoadAccident;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.*;

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
	public RoadAccident getAccidentByIndex7(String index) {

		for (RoadAccident accident : roadAccidentList) {
			if ((accident.getAccidentId()).equals(index)) {

				return accident;
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
	public Collection<RoadAccident> getAccidentsByLocation7(float minLongitude,
			float maxLongitude, float minLatitude, float maxLatitude) {

		List<RoadAccident> accidentByLocation =  new ArrayList<RoadAccident>();
		for(RoadAccident accident : roadAccidentList) {

			if (accident.getLongitude() > minLongitude
					&& accident.getLongitude() < maxLongitude
					&& accident.getLatitude() > minLatitude
					&& accident.getLatitude() < maxLatitude) {
				accidentByLocation.add(accident);

			}
		}

		return accidentByLocation;
	}

    /**
     * count incidents by road surface conditions
     * ex:
     * wet -> 2
     * dry -> 5
     * @return
     */
    public Map<String, Long> getCountByRoadSurfaceCondition7(){
        
    	Map<String, Long>  result = new HashMap<String, Long>();
    	Multimap<String, RoadAccident> intermediateMap = (Multimap) ArrayListMultimap.create();
     	for(RoadAccident accident : roadAccidentList) {
     		
     		intermediateMap.put(accident.getRoadSurfaceConditions(), accident);
     	}
     	for(String key :intermediateMap.keySet() ) {
     		
     		result.put(key, new Long(intermediateMap.get(key).size()));
     	}
     	return result;
    }

    /**
     * find the weather conditions which caused the top 3 number of incidents
     * as example if there were 10 accidence in rain, 5 in snow, 6 in sunny and 1 in foggy, then your result list should contain {rain, sunny, snow} - top three in decreasing order
     * @return
     */
    public List<String> getTopThreeWeatherCondition7(){
        return null;
    }

    /**
     * return a multimap where key is a district authority and values are accident ids
     * ex:
     * authority1 -> id1, id2, id3
     * authority2 -> id4, id5
     * @return
     */
    public Multimap<String, String> getAccidentIdsGroupedByAuthority7(){

    	Multimap<String, String> accidentIdGrpByAuthority = (Multimap) ArrayListMultimap.create();
    	for(RoadAccident accident : roadAccidentList) {
    		
    		accidentIdGrpByAuthority.put(accident.getDistrictAuthority(), accident.getAccidentId());
    		
    	}
    return accidentIdGrpByAuthority;
    }


    // Now let's do same tasks but now with streaming api


	public RoadAccident getAccidentByIndex(String index) {
		return roadAccidentList.stream()
				.filter(accident -> accident.getAccidentId().equals(index))
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
	public Collection<RoadAccident> getAccidentsByLocation(float minLongitude,
			float maxLongitude, float minLatitude, float maxLatitude) {
		
		return roadAccidentList
				.stream()
				.filter(roadAccident -> filterLocationbyCordinates(
						roadAccident.getLongitude(),
						roadAccident.getLatitude(), minLongitude, maxLongitude,
						minLatitude, maxLatitude)).collect(Collectors.toList());

	}

    private boolean filterLocationbyCordinates(float longitude, float latitude,
			float minLongitude, float maxLongitude, float minLatitude,
			float maxLatitude) {

    	return  (longitude > minLongitude && longitude < maxLongitude 
    		 &&	latitude > minLatitude && latitude < maxLatitude);
	}


	/**
     * find the weather conditions which caused max number of incidents
     * @return
     */
	public List<String> getTopThreeWeatherCondition() {
		Map<String, Long> noOfIncidents = roadAccidentList.stream().collect(
				groupingBy(RoadAccident::getWeatherConditions, counting()));

		List<String> accidentListByWeatherCond = noOfIncidents
				.entrySet()
				.stream()
				.sorted(Comparator.comparing(Map.Entry::getValue,
						Comparator.reverseOrder())).limit(3)
				.map(Map.Entry::getKey).collect(Collectors.toList());

		return accidentListByWeatherCond;

	}

    /**
     * count incidents by road surface conditions
     * @return
     */
	public Map<String, Long> getCountByRoadSurfaceCondition() {
		return roadAccidentList.stream().collect(
				groupingBy(RoadAccident::getRoadSurfaceConditions, counting()));
	}

    /**
     * To match streaming operations result, return type is a java collection instead of multimap
     * @return
     */
	public Map<String, List<String>> getAccidentIdsGroupedByAuthority() {

		return roadAccidentList.stream().collect(
				groupingBy(RoadAccident::getDistrictAuthority,
						mapping(RoadAccident::getAccidentId, toList())));
	}

}
