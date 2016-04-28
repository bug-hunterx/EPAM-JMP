package com.epam.processor;

import com.epam.data.RoadAccident;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This is to be completed by mentees
 */
public class DataProcessor {

    private final List<RoadAccident> roadAccidentList;

    public DataProcessor(List<RoadAccident> roadAccidentList) {
        this.roadAccidentList = roadAccidentList;
    }


//    First try to solve task using java 7 style for processing collections

    /**
     * Return road accident with matching index
     *
     * @param index
     * @return
     */
    public RoadAccident getAccidentByIndex7(String index) {
        Iterator<RoadAccident> roadAccidentIterator = roadAccidentList.iterator();
        while (roadAccidentIterator.hasNext()) {
            RoadAccident current = roadAccidentIterator.next();
            if (current.getAccidentId().equals(index)) {
                return current;
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
    public Collection<RoadAccident> getAccidentsByLocation7(float minLongitude, float maxLongitude, float minLatitude, float maxLatitude) {
        ArrayList<RoadAccident> result = new ArrayList<>();

        Iterator<RoadAccident> roadAccidentIterator = roadAccidentList.iterator();

        while (roadAccidentIterator.hasNext()) {
            RoadAccident current = roadAccidentIterator.next();
            if (minLongitude <= current.getLongitude() && current.getLongitude() <= maxLongitude &&
                    minLatitude <= current.getLatitude() && current.getLatitude() <= maxLatitude) {
                result.add(current);
            }
        }

        return result;
    }

    /**
     * count incidents by road surface conditions
     * ex:
     * wet -> 2
     * dry -> 5
     *
     * @return
     */
    public Map<String, Long> getCountByRoadSurfaceCondition7() {
        HashMap<String, Long> result = new HashMap<>();
        Iterator<RoadAccident> roadAccidentIterator = roadAccidentList.iterator();

        while (roadAccidentIterator.hasNext()) {
            RoadAccident current = roadAccidentIterator.next();
            String roadSurfaceConditions = current.getRoadSurfaceConditions();

            Long newValue = result.get(roadSurfaceConditions) == null ? 1 : Long.valueOf(result.get(roadSurfaceConditions) + 1);
            result.put(roadSurfaceConditions, newValue);
        }

        return result;
    }

    /**
     * find the weather conditions which caused the top 3 number of incidents
     * as example if there were 10 accidence in rain, 5 in snow, 6 in sunny and 1 in foggy, then your result list should contain {rain, sunny, snow} - top three in decreasing order
     *
     * @return
     */
    public List<String> getTopThreeWeatherCondition7() {
        List<String> result = new ArrayList<>();

        Map<String, Long> accidentsByWeatherConditions = new HashMap<>();

        Iterator<RoadAccident> roadAccidentIterator = roadAccidentList.iterator();

        while (roadAccidentIterator.hasNext()) {
            RoadAccident current = roadAccidentIterator.next();
            String weatherConditions = current.getWeatherConditions();

            Long newValue = accidentsByWeatherConditions.get(weatherConditions) == null ? 1 : Long.valueOf(accidentsByWeatherConditions.get(weatherConditions) + 1);
            accidentsByWeatherConditions.put(weatherConditions, newValue);
        }

        List<Map.Entry<String, Long>> listAccidentsByConditions = new LinkedList<Map.Entry<String, Long>>(accidentsByWeatherConditions.entrySet());

        Collections.sort(listAccidentsByConditions, new Comparator<Map.Entry<String, Long>>(){

            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                // Decreasing order
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        List<Map.Entry<String, Long>> topAccidents = listAccidentsByConditions.subList(0, 3);

        for (Map.Entry<String, Long>  accident: topAccidents) {
            result.add(accident.getKey());
        }

        return result;
    }

    /**
     * return a multimap where key is a district authority and values are accident ids
     * ex:
     * authority1 -> id1, id2, id3
     * authority2 -> id4, id5
     *
     * @return
     */
    public Multimap<String, String> getAccidentIdsGroupedByAuthority7() {
        Multimap<String, String> result = ArrayListMultimap.create();
        Iterator<RoadAccident> roadAccidentIterator = roadAccidentList.iterator();

        while (roadAccidentIterator.hasNext()) {
            RoadAccident current = roadAccidentIterator.next();
            result.put(current.getDistrictAuthority(), current.getAccidentId());
        }

        return result;
    }


    // Now let's do same tasks but now with streaming api


    public RoadAccident getAccidentByIndex(String index) {
        Optional<RoadAccident> result = roadAccidentList.stream()
                .filter(acc -> acc.getAccidentId().equals(index))
                .findFirst();

        return result.isPresent() ? result.get() : null;
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
    public Collection<RoadAccident> getAccidentsByLocation(float minLongitude, float maxLongitude, float minLatitude, float maxLatitude) {
        return roadAccidentList.stream()
                .filter(acc -> minLongitude <= acc.getLongitude() && acc.getLongitude() <= maxLongitude &&
                        minLatitude <= acc.getLatitude() && acc.getLatitude() <= maxLatitude)
                .collect(Collectors.toList());
    }

    /**
     * find the weather conditions which caused max number of incidents
     *
     * @return
     */
    public List<String> getTopThreeWeatherCondition() {

        Map<String, Long> mapAccidentsByWeatherCondition = roadAccidentList.stream()
                .collect(Collectors.groupingBy(RoadAccident::getWeatherConditions, Collectors.counting()));

        return mapAccidentsByWeatherCondition.entrySet().stream()
                .sorted((s1, s2) -> s2.getValue().compareTo(s1.getValue()))
                .map(Map.Entry::getKey)
                .limit(3)
                .collect(Collectors.toList());
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
     * To match streaming operations result, return type is a java collection instead of multimap
     *
     * @return
     */
    public Map<String, List<String>> getAccidentIdsGroupedByAuthority() {

        return roadAccidentList.stream()
                .collect(Collectors.groupingBy(RoadAccident::getDistrictAuthority, HashMap::new, Collectors.mapping(RoadAccident::getAccidentId, Collectors.toList())));
    }

}
