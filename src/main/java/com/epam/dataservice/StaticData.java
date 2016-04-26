package com.epam.dataservice;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by Tanmoy on 4/25/2016.
 */
public class StaticData {

    private static final String DISTRICT_AUTHORITY_CSV = "src/main/resources/disrict_authority.csv";
    private static final String ACCIDENT_SEVERITY_CSV = "src/main/resources/accident_severity.csv";
    private static final String LIGHT_CONDITIONS_CSV = "src/main/resources/light_conditions.csv";
    private static final String POLICE_FORCE_CSV = "src/main/resources/police_force.csv";
    private static final String ROAD_SURFACE_CSV = "src/main/resources/road_surface.csv";
    private static final String WEATHER_CONDITIONS_CSV = "src/main/resources/weather_conditions.csv";
    private static Map<Integer, String> districtAuthorities;
    private static Map<Integer, String> accidentSeverity;
    private static Map<Integer, String> lightConditions;
    private static Map<Integer, String> policeForce;
    private static Map<Integer, String> roadSurface;
    private static Map<Integer, String> weatherConditions;


    static{
        loadData();
    }

    private static void loadData() {
        districtAuthorities = loadIntToStrignMap(DISTRICT_AUTHORITY_CSV);
        accidentSeverity = loadIntToStrignMap(ACCIDENT_SEVERITY_CSV);
        lightConditions = loadIntToStrignMap(LIGHT_CONDITIONS_CSV);
        policeForce = loadIntToStrignMap(POLICE_FORCE_CSV);
        roadSurface = loadIntToStrignMap(ROAD_SURFACE_CSV);
        weatherConditions = loadIntToStrignMap(WEATHER_CONDITIONS_CSV);
    }

    private static Map<Integer, String> loadIntToStrignMap(String filepath) {
        Map<Integer, String> resultMap = new HashMap<>();

        try {
            Reader reader = new FileReader(filepath);
            Iterable<CSVRecord> records = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
            for (CSVRecord record : records) {
                Integer key = Integer.valueOf(record.get(0));
                String value = record.get(1);
                resultMap.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    public static String getDistrictAuthority(String id){
        return districtAuthorities.get(Integer.parseInt(id));
    }

    public static String getAccidentSeverity(String id){
        return accidentSeverity.get(Integer.parseInt(id));
    }
    public static String getLightConditions(String id){
        return lightConditions.get(Integer.parseInt(id));
    }
    public static String getPoliceForce(String id){
        return policeForce.get(Integer.parseInt(id));
    }
    public static String getRoadSurface(String id){
        return roadSurface.get(Integer.parseInt(id));
    }

    public static String getWeatherConditions(String id){
        return weatherConditions.get(Integer.parseInt(id));
    }
}
