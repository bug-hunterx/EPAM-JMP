package com.epam.data;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccidentsDataLoader {

    private static final String DISTRICT_AUTHORITY_CSV = "src/main/resources/disrict_authority.csv";
    private static final String ACCIDENT_SEVERITY_CSV = "src/main/resources/accident_severity.csv";
    private static final String LIGHT_CONDITIONS_CSV = "src/main/resources/light_conditions.csv";
    private static final String POLICE_FORCE_CSV = "src/main/resources/police_force.csv";
    private static final String ROAD_SURFACE_CSV = "src/main/resources/road_surface.csv";
    private static final String WEATHER_CONDITIONS_CSV = "src/main/resources/weather_conditions.csv";
    private Map<Integer, String> districtAuthorities;
    private Map<Integer, String> accidentSeverity;
    private Map<Integer, String> lightConditions;
    private Map<Integer, String> policeForce;
    private Map<Integer, String> roadSurface;
    private Map<Integer, String> weatherConditions;

    private void loadAdditionalTables() {
        districtAuthorities = loadIntToStrignMap(DISTRICT_AUTHORITY_CSV);
        accidentSeverity = loadIntToStrignMap(ACCIDENT_SEVERITY_CSV);
        lightConditions = loadIntToStrignMap(LIGHT_CONDITIONS_CSV);
        policeForce = loadIntToStrignMap(POLICE_FORCE_CSV);
        roadSurface = loadIntToStrignMap(ROAD_SURFACE_CSV);
        weatherConditions = loadIntToStrignMap(WEATHER_CONDITIONS_CSV);
    }


    private Map<Integer, String> loadIntToStrignMap(String filepath) {
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

    public List<RoadAccident> loadRoadAccidents(String filepath) {
        loadAdditionalTables();

        List<RoadAccident> roadAccidentList = new ArrayList<>();
        try {
            Reader reader = new FileReader(filepath);
            Iterable<CSVRecord> records = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
            for (CSVRecord record : records) {
                RoadAccident roadAccident = parseOneRecord(record);
                if (roadAccident != null) {
                    roadAccidentList.add(roadAccident);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.printf("Loaded %d accidents information \n", roadAccidentList.size());
        return roadAccidentList;
    }

    private RoadAccident parseOneRecord(CSVRecord record) {
        try {
            String accidentId = record.get("Accident_Index");
            RoadAccident roadAccident = new RoadAccidentBuilder(accidentId)
                    .withLongitude(Float.valueOf(record.get("Longitude")))
                    .withLatitude(Float.valueOf(record.get("Latitude")))
                    .withPoliceForce(policeForce.get(record.get("Police_Force")))
                    .withAccidentSeverity(accidentSeverity.get(record.get("Accident_Severity")))
                    .withNumberOfVehicles(Integer.valueOf(record.get("Number_of_Vehicles")))
                    .withNumberOfCasualties(Integer.valueOf(record.get("Number_of_Casualties")))
                    .withDate(LocalDate.parse(record.get("Date"), DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .withTime(LocalTime.parse(record.get("Time"), DateTimeFormatter.ofPattern("H:mm")))
                    .withDistrictAuthority(districtAuthorities.get(Integer.valueOf(record.get("Local_Authority_(District)"))))
                    .withLightConditions(lightConditions.get(Integer.valueOf(record.get("Light_Conditions"))))
                    .withWeatherConditions(weatherConditions.get(Integer.valueOf(record.get("Weather_Conditions"))))
                    .withRoadSurfaceConditions(roadSurface.get(Integer.valueOf(record.get("Road_Surface_Conditions"))))
                    .build();
            return roadAccident;
        } catch (DateTimeParseException timeException) {
            System.out.println("Some data quality issue with one of records - screw it, have plenty more");
            return null;
        }
    }

}
