package com.epam.processor;

import com.epam.data.TimeOfDay;
import com.epam.data.RoadAccident;
import com.epam.service.MockPoliceForceService;
import com.epam.service.PoliceForceService;
import com.google.common.base.Function;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.io.IOException;
import java.time.LocalTime;
import java.util.*;

import static java.util.stream.Collectors.*;

/**
 * This is to be completed by mentees
 */
public class DataProcessor {
    private PoliceForceService policeService;

    public DataProcessor() throws IOException {
        Properties props = new Properties();
        props.load(getClass().getClassLoader().getResourceAsStream("config.properties"));

        this.policeService = new MockPoliceForceService(Integer.valueOf(props.getProperty("max-service-response-time")));
    }


//    First try to solve task using java 7 style for processing collections

    /**
     * Return road accident with matching index
     * @param index
     * @return
     */
    public RoadAccident getAccidentByIndex7(List<RoadAccident> roadAccidentList, String index){
        for(RoadAccident accident : roadAccidentList) {
            if(accident.getAccidentId().equals(index)) {
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
    public Collection<RoadAccident> getAccidentsByLocation7(List<RoadAccident> roadAccidentList, float minLongitude, float maxLongitude, float minLatitude, float maxLatitude){
        List<RoadAccident> filteredAccidents = new ArrayList<>();

        for(RoadAccident accident : roadAccidentList) {
            if(isGeoPositionInsideBoundaries(
                    accident.getLatitude(),
                    accident.getLongitude(),
                    minLongitude, maxLongitude, minLatitude, maxLatitude)) {
                filteredAccidents.add(accident);
            }
        }

        return filteredAccidents;
    }

    /**
     * count incidents by road surface conditions
     * ex:
     * wet -> 2
     * dry -> 5
     * @return
     */
    public Map<String, Long> getCountByRoadSurfaceCondition7(List<RoadAccident> roadAccidentList){
        return getCountBy(roadAccidentList, RoadAccident::getRoadSurfaceConditions);
    }

    private Map<String, Long> getCountBy(List<RoadAccident> roadAccidentList, Function<RoadAccident, String> classificationFunction) {
        Map<String, Long> stats = new HashMap<>();

        for(RoadAccident accident : roadAccidentList) {
            String key = classificationFunction.apply(accident);
            Long counter = stats.containsKey(key) ? stats.get(key)+1 : 1L;

            stats.put(key, counter);
        }

        return stats;
    }

    /**
     * find the weather conditions which caused the top 3 number of incidents
     * as example if there were 10 accidence in rain, 5 in snow, 6 in sunny and 1 in foggy, then your result list should contain {rain, sunny, snow} - top three in decreasing order
     * @return
     */
    public List<String> getTopThreeWeatherCondition7(List<RoadAccident> roadAccidentList){
        // Get stats
        List<Map.Entry<String, Long>> weatherStats = new ArrayList<>(getCountBy(roadAccidentList, RoadAccident::getWeatherConditions).entrySet());

        //Sort and choose top 3
        weatherStats.sort(new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        weatherStats = weatherStats.subList(0, 3);

        // Map stats to conditions list
        List<String> topWeatherConditions = new ArrayList<>();
        for(Map.Entry<String, Long> entry : weatherStats) {
            topWeatherConditions.add(entry.getKey());
        }

        return topWeatherConditions;
    }

    /**
     * return a multimap where key is a district authority and values are accident ids
     * ex:
     * authority1 -> id1, id2, id3
     * authority2 -> id4, id5
     * @return
     */
    public Multimap<String, String> getAccidentIdsGroupedByAuthority7(List<RoadAccident> roadAccidentList){
        Multimap<String, String> idsByAuthority = ArrayListMultimap.create();

        for(RoadAccident accident : roadAccidentList) {
            idsByAuthority.put(accident.getDistrictAuthority(), accident.getAccidentId());
        }

        return idsByAuthority;
    }


    // Now let's do same tasks but now with streaming api



    public RoadAccident getAccidentByIndex(List<RoadAccident> roadAccidentList, String index){
        return roadAccidentList.stream()
                .filter(roadAccident -> roadAccident.getAccidentId().equals(index))
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
    public Collection<RoadAccident> getAccidentsByLocation(List<RoadAccident> roadAccidentList, float minLongitude, float maxLongitude, float minLatitude, float maxLatitude){
        return roadAccidentList.stream()
                .filter(roadAccident -> isGeoPositionInsideBoundaries(
                        roadAccident.getLatitude(),
                        roadAccident.getLongitude(),
                        minLongitude, maxLongitude, minLatitude, maxLatitude))
                .collect(toList());
    }

    private boolean isGeoPositionInsideBoundaries(float lat, float lon, float minLongitude, float maxLongitude, float minLatitude, float maxLatitude) {
        return lat > minLatitude && lat < maxLatitude && lon > minLongitude && lon < maxLongitude;
    }

    /**
     * find the weather conditions which caused max number of incidents
     * @return
     */
    public List<String> getTopThreeWeatherCondition(List<RoadAccident> roadAccidentList){
        Map<String, Long> weatherStatistics = roadAccidentList.stream()
                .collect(groupingBy(RoadAccident::getWeatherConditions, counting()));

        return weatherStatistics.entrySet().stream()
                .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                .map(stringLongEntry -> stringLongEntry.getKey())
                .limit(3)
                .collect(toList());
    }

    /**
     * count incidents by road surface conditions
     * @return
     */
    public Map<String, Long> getCountByRoadSurfaceCondition(List<RoadAccident> roadAccidentList){
        return roadAccidentList.stream()
                .collect(groupingBy(RoadAccident::getRoadSurfaceConditions, counting()));
    }

    /**
     * To match streaming operations result, return type is a java collection instead of multimap
     * @return
     */
    public Map<String, List<String>> getAccidentIdsGroupedByAuthority(List<RoadAccident> roadAccidentList){
        return roadAccidentList.stream()
                .collect(groupingBy(
                        RoadAccident::getDistrictAuthority,
                        HashMap::new,
                        mapping(RoadAccident::getAccidentId, toList())
                ));
    }

    public static void enrichWithTimeOfDay(List<RoadAccident> accidents) {
        accidents.stream()
                .forEach(roadAccident -> roadAccident.setTimeOfDay(timeOfDayFromTime(roadAccident.getTime())));
    }

    private static TimeOfDay timeOfDayFromTime(LocalTime time) {
        for (TimeOfDay part : TimeOfDay.values()) {
            if (!time.isAfter(part.getEnd()) && !time.isBefore(part.getStart())) {
                return part;
            }
        }

        throw new RuntimeException("Could not define day part for time provided: " + time.toString());
    }

    public void enrichWithForceContact(List<RoadAccident> accidents) {
        accidents.stream()
                .forEach(roadAccident -> {
                    try {
                        roadAccident.setForceContact(policeService.getContactNo(roadAccident.getPoliceForce()));
                    } catch (Exception e) {
                        System.out.println("Could not retrieve contact information" + e.toString());
                    }
                });
    }

}
