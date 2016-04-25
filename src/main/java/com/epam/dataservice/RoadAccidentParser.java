package com.epam.dataservice;

import com.epam.data.RoadAccident;
import com.epam.data.RoadAccidentBuilder;
import org.apache.commons.csv.CSVRecord;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Created by Tanmoy on 4/25/2016.
 */
public class RoadAccidentParser {

    public RoadAccident parseRecord(CSVRecord record) {
        try {
            String accidentId = record.get("Accident_Index");
            RoadAccident roadAccident = new RoadAccidentBuilder(accidentId)
                    .withLongitude(Float.valueOf(record.get("Longitude")))
                    .withLatitude(Float.valueOf(record.get("Latitude")))
                    .withPoliceForce(StaticData.getPoliceForce(record.get("Police_Force")))
                    .withAccidentSeverity(StaticData.getAccidentSeverity(record.get("Accident_Severity")))
                    .withNumberOfVehicles(Integer.valueOf(record.get("Number_of_Vehicles")))
                    .withNumberOfCasualties(Integer.valueOf(record.get("Number_of_Casualties")))
                    .withDate(LocalDate.parse(record.get("Date"), DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .withTime(LocalTime.parse(record.get("Time"), DateTimeFormatter.ofPattern("H:mm")))
                    .withDistrictAuthority(StaticData.getDistrictAuthority(record.get("Local_Authority_(District)")))
                    .withLightConditions(StaticData.getLightConditions(record.get("Light_Conditions")))
                    .withWeatherConditions(StaticData.getWeatherConditions(record.get("Weather_Conditions")))
                    .withRoadSurfaceConditions(StaticData.getRoadSurface(record.get("Road_Surface_Conditions")))
                    .build();
            return roadAccident;
        } catch (DateTimeParseException timeException) {
            //System.out.println("Some data quality issue with one of records - screw it, have plenty more");
            return null;
        }
    }
}
