package com.epam.demo.collections;

import com.epam.data.AccidentsDataLoader;
import com.epam.data.RoadAccident;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Olga on 05.04.2016.
 */
public class MoreStreamingDemo {
    private static final String ACCIDENTS_CSV = "src/main/resources/DfTRoadSafety_Accidents_2009.csv";


    public static void main(String[] args) throws IOException {
        AccidentsDataLoader accidentsDataLoader = new AccidentsDataLoader();
        List<RoadAccident> accidents = accidentsDataLoader.loadRoadAccidents(ACCIDENTS_CSV);


        // get all accident with district authority Westminster

        List<RoadAccident> filtered = accidents.stream()
                .filter(roadAccident -> roadAccident.getDistrictAuthority().equals("Westminster"))
                .collect(Collectors.toList());

        List<RoadAccident> accidentsByDistrictAuthority = accidents.stream().filter(roadAccident -> roadAccident.getDistrictAuthority().equals("Westminster")).collect(Collectors.toList());

        // get first accident matching predicate

        RoadAccident firstMatching = accidents.stream()
                .filter(roadAccident -> roadAccident.getDistrictAuthority().equals("Westminster"))
                .findFirst().get();

        RoadAccident roadAccidentLimited = accidents.stream().filter(roadAccident -> roadAccident.getDistrictAuthority().equals("Westminster")).findFirst().get();

        // get all accidents with number of vehicle > 5 and number of casualties < 2



        // find top two light conditions by number of acidents

        Map<String, Long> lightCondNumbers = accidents.stream()
                .map(roadAccident -> roadAccident.getLightConditions())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        lightCondNumbers.entrySet().stream()
                .sorted((o1, o2) -> (int) (o2.getValue() - o1.getValue()))
                .limit(2)
                .forEach(stringLongEntry -> System.out.println(stringLongEntry.getKey()));


        //group by police force. result should be a map <String, List<RoadAccident>>

    }
}
