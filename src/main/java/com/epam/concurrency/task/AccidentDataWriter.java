package com.epam.concurrency.task;

import com.epam.data.RoadAccident;
import com.epam.data.RoadAccidentBuilder;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanmoy on 6/17/2016.
 */
public class AccidentDataWriter {

    private String dataFileName;
    private CSVFormat outputDataFormat = CSVFormat.EXCEL.withHeader();
    private CSVPrinter csvFilePrinter;
    private boolean isHeaderWritten;
    private static final Object [] FILE_HEADER = {"Accident_Index","Longitude","Latitude","Police_Force","Accident_Severity","Number_of_Vehicles","Number_of_Casualties","Date","Time","Local_Authority_(District)","LightCondition","WeatherCondition","RoadSafeCondition","Police_Force_Contact"};
    private Logger log = LoggerFactory.getLogger(AccidentDataWriter.class);

    public void init(String dataFileName){
        this.dataFileName = dataFileName;
        try {
            isHeaderWritten = false;
            FileWriter fileWriter = new FileWriter(dataFileName);
            csvFilePrinter = new CSVPrinter(fileWriter, outputDataFormat);
        } catch (IOException e) {
            log.error("Failed to create file writer for file {}", dataFileName, e);
            throw new RuntimeException("Failed to create file writer for file " + dataFileName, e);
        }
    }
    public void writeAccidentData(List<RoadAccidentDetails> accidentDetailsList){
        try {
            if (!isHeaderWritten){
                csvFilePrinter.printRecord(FILE_HEADER);
                isHeaderWritten = true;
            }
            for (RoadAccidentDetails accidentDetails : accidentDetailsList){
                csvFilePrinter.printRecord(getCsvRecord(accidentDetails));
            }
            Util.sleep();
            csvFilePrinter.flush();
        } catch (IOException e) {
            log.error("Failed to write accidentDetails to file {}", dataFileName);
            throw new RuntimeException("Failed to write accidentDetails to file " + dataFileName, e);
        }
    }

    private List<String> getCsvRecord(RoadAccidentDetails roadAccidentDetails){
        List<String> record = new ArrayList<>();
        record.add(roadAccidentDetails.getAccidentId());
        record.add(String.valueOf(roadAccidentDetails.getLongitude()));
        record.add(String.valueOf(roadAccidentDetails.getLatitude()));
        record.add(roadAccidentDetails.getPoliceForce());
        record.add(roadAccidentDetails.getAccidentSeverity());
        record.add(String.valueOf(roadAccidentDetails.getNumberOfVehicles()));
        record.add(String.valueOf(roadAccidentDetails.getNumberOfCasualties()));
        record.add(roadAccidentDetails.getDate().toString());
        record.add(roadAccidentDetails.getTime().toString());
        record.add(roadAccidentDetails.getDistrictAuthority());
        record.add(roadAccidentDetails.getLightConditions());
        record.add(roadAccidentDetails.getWeatherConditions());
        record.add(roadAccidentDetails.getRoadSurfaceConditions());
        record.add(roadAccidentDetails.getPoliceForceContact());
        return record;
    }

    public void close(){
        try {
            csvFilePrinter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AccidentDataWriter accidentDataWriter = new AccidentDataWriter();
        accidentDataWriter.init("target/output/DfTRoadSafety_Accidents_consolidated.csv");
        RoadAccidentBuilder roadAccidentBuilder =  new RoadAccidentBuilder("200901BS70001");
        RoadAccident roadAccident = roadAccidentBuilder.withAccidentSeverity("1").build();

    }
}
