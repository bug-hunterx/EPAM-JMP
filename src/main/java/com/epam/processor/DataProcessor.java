package com.epam.processor;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.epam.data.RoadAccident;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

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
		Collection<RoadAccident> filteredAccidents = Collections2.filter(roadAccidentList,
				new Predicate<RoadAccident>() {

					@Override
					public boolean apply(RoadAccident accident) {
						if (accident.getAccidentId().equals(index)) {
							return true;
						} else {
							return false;
						}
					}

				});

		return (filteredAccidents.size() > 0 ? filteredAccidents.iterator().next() : null);
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
	public Collection<RoadAccident> getAccidentsByLocation7(float minLongitude, float maxLongitude, float minLatitude,
			float maxLatitude) {
		Collection<RoadAccident> filteredAccidents = Collections2.filter(roadAccidentList,
				Predicates.and(new Predicate<RoadAccident>() {

					@Override
					public boolean apply(RoadAccident input) {
						// Longtitude
						if ((input.getLongitude() >= minLongitude) && (input.getLongitude() <= maxLongitude)) {
							return true;
						} else {
							return false;
						}
					}

				}, new Predicate<RoadAccident>() {

					@Override
					public boolean apply(RoadAccident input) {
						if ((input.getLatitude() >= minLatitude) && (input.getLatitude() <= maxLatitude)) {
							return true;
						} else {
							return false;
						}
					}

				}));

		Builder<RoadAccident> builder = ImmutableList.builder();
		builder.addAll(filteredAccidents);
		return builder.build();
	}

	/**
	 * count incidents by road surface conditions ex: wet -> 2 dry -> 5
	 * 
	 * @return
	 */
	public Map<String, Long> getCountByRoadSurfaceCondition7() {
		final Map<String, Long> result = Maps.newHashMap();
		List<String> transformedList = Lists.transform(this.roadAccidentList, new Function<RoadAccident, String>() {

			@Override
			public String apply(RoadAccident input) {
				return input.getRoadSurfaceConditions();
			}

		});

		for (String s : transformedList) {
			if (result.get(s) == null) {
				result.put(s, Long.valueOf(1L));
			} else {
				result.put(s, (result.get(s) + 1L));
			}
		}

		return result;
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
		Multimap<String, RoadAccident> weatherConditionToRa = Multimaps.index(roadAccidentList,
				new Function<RoadAccident, String>() {

					@Override
					public String apply(RoadAccident input) {
						return input.getWeatherConditions();
					}

				});

		Map<String, Integer> weatherConditionToCount = Maps.newHashMap();
		for (String key : weatherConditionToRa.keys()) {
			if (weatherConditionToCount.get(key) == null) {
				weatherConditionToCount.put(key, 1);
			} else {
				weatherConditionToCount.put(key, (weatherConditionToCount.get(key) + 1));
			}
		}

		TreeSet<Map.Entry<String, Integer>> treeSet = new TreeSet<Map.Entry<String, Integer>>(
				new Comparator<Map.Entry<String, Integer>>() {

					@Override
					public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
						
						return (o2.getValue() - o1.getValue());
					}
					
				});
		
		treeSet.addAll(weatherConditionToCount.entrySet());
		
		List<String> weatherConditions = Lists.newArrayList();
		
		int count = 0;
		Iterator<Map.Entry<String, Integer>> iter = treeSet.iterator();
		
		while(iter.hasNext() && (count < 3)){
			weatherConditions.add(iter.next().getKey());
			count++;
		}
		return weatherConditions;
	}

	/**
	 * return a multimap where key is a district authority and values are
	 * accident ids ex: authority1 -> id1, id2, id3 authority2 -> id4, id5
	 * 
	 * @return
	 */
	public Multimap<String, String> getAccidentIdsGroupedByAuthority7() {
		ImmutableListMultimap<String,RoadAccident> authorityToRa = Multimaps.index(roadAccidentList, new Function<RoadAccident,String>(){

			@Override
			public String apply(RoadAccident input) {
				return input.getDistrictAuthority();
			}
			
		});
		
		ListMultimap<String, String> authorityToId = Multimaps.transformValues(authorityToRa, new Function<RoadAccident,String>(){

			@Override
			public String apply(RoadAccident input) {
				return input.getAccidentId();
			}
			
		});
		return ImmutableListMultimap.copyOf(authorityToId);
	}

	// Now let's do same tasks but now with streaming api

	public RoadAccident getAccidentByIndex(String index) {
		Stream<RoadAccident> ret = roadAccidentList.stream()
				.filter((RoadAccident ra) -> ra.getAccidentId().equals(index));

		Optional<RoadAccident> opt = ret.findFirst();
		return opt.isPresent() ? opt.get() : null;
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
	public Collection<RoadAccident> getAccidentsByLocation(float minLongitude, float maxLongitude, float minLatitude,
			float maxLatitude) {
		Stream<RoadAccident> ret = roadAccidentList.stream().filter(ra -> ra.getLongitude() >= minLongitude)
				.filter(ra -> ra.getLongitude() <= maxLongitude).filter(ra -> ra.getLatitude() >= minLatitude)
				.filter(ra -> ra.getLatitude() <= maxLatitude);

		return ret.collect(Collectors.toList());
	}

	/**
	 * find the weather conditions which caused max number of incidents
	 * 
	 * @return
	 */
	public List<String> getTopThreeWeatherCondition() {
		Map<String, Long> groups = roadAccidentList.stream()
				.collect(Collectors.groupingBy(RoadAccident::getWeatherConditions, Collectors.counting()));
		return groups.entrySet().stream().sorted((o1, o2) -> (int) (o2.getValue() - o1.getValue())).limit(3)
				.map(e -> e.getKey()).collect(Collectors.toList());
	}

	/**
	 * count incidents by road surface conditions
	 * 
	 * @return
	 */
	public Map<String, Long> getCountByRoadSurfaceCondition() {
		return roadAccidentList.stream()
				.collect(Collectors.groupingBy(RoadAccident::getRoadSurfaceConditions, Collectors.counting()));
	}

	/**
	 * To match streaming operations result, return type is a java collection
	 * instead of multimap
	 * 
	 * @return
	 */
	public Map<String, List<String>> getAccidentIdsGroupedByAuthority() {
		return roadAccidentList.stream().collect(Collectors.groupingBy(RoadAccident::getDistrictAuthority,
				Collectors.mapping(RoadAccident::getAccidentId, Collectors.toList())));
	}

}
