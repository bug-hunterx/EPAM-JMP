package com.epam.processor;

import com.epam.data.AccidentsDataLoader;
import com.epam.data.RoadAccident;
import com.epam.data.RoadAccidentBuilder;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.Collection;
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
		List<RoadAccident> list = new ArrayList<>();
		for (RoadAccident roadAccident : roadAccidentList) {
			if (roadAccident.getLongitude() >= minLongitude
					&& roadAccident.getLongitude() <= maxLongitude
					&& roadAccident.getLatitude() >= minLatitude
					&& roadAccident.getLatitude() <= maxLatitude) {
				list.add(roadAccident);
			}
		}
		return list;
	}

	/**
	 * count incidents by road surface conditions ex: wet -> 2 dry -> 5
	 * 
	 * @return
	 */
	public Map<String, Long> getCountByRoadSurfaceCondition7() {
		Map<String, Long> map = new HashMap<>();
		long dry = 0, wet = 0;

		for (RoadAccident roadAccident : roadAccidentList) {
			if (roadAccident.getRoadSurfaceConditions().equals("2")
					|| roadAccident.getRoadSurfaceConditions()
							.equalsIgnoreCase("wet")) {
				wet++;
			} else if (roadAccident.getRoadSurfaceConditions().equals("5")
					|| roadAccident.getRoadSurfaceConditions()
							.equalsIgnoreCase("dry")) {
				dry++;
			}
		}
		map.put("Dry", dry);
		map.put("Wet", wet);
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
		List<String> list = new LinkedList<>();
		List<Long> countList = new ArrayList<>();
		Multimap<String, Long> multiMap = HashMultimap.create();
		Long count = 0L;

		for (RoadAccident roadAccident : roadAccidentList) {
			multiMap.put(roadAccident.getWeatherConditions(), count++);
		}

		for (int i = 0; i < 2; i++) {
			countList.add(0L);
		}

		for (String key : multiMap.keySet()) {
			long size = multiMap.get(key).size();
			if (size > countList.get(0)) {
				list.add(0, key);
				countList.add(0, size);
			} else if (size > countList.get(1)) {
				list.add(1, key);
				countList.add(1, size);
			} else if (size > countList.get(2)) {
				list.add(2, key);
				countList.add(2, size);
			}
			if (list.size() == 4) {
				list.remove(3);
			}
		}
		return list;
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
		Map<String, Long> map = new HashMap<>();

		long dry = roadAccidentList
				.stream()
				.filter(ra -> ra.getRoadSurfaceConditions().equals("5")
						|| ra.getRoadSurfaceConditions()
								.equalsIgnoreCase("dry")).count();
		long wet = roadAccidentList
				.stream()
				.filter(ra -> ra.getRoadSurfaceConditions().equals("2")
						|| ra.getRoadSurfaceConditions()
								.equalsIgnoreCase("wet")).count();

		map.put("Dry", dry);
		map.put("Wet", wet);
		return map;
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
