package com.epam.data;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey on 25.04.2016.
 */
public class AccidentsDataWriter {
    private CSVPrinter printer;

    public AccidentsDataWriter(String filepath) throws IOException {
        this.printer = new CSVPrinter(new FileWriter(filepath, false), CSVFormat.DEFAULT);
    }

    public void print(List<RoadAccident> accidents) throws IOException {
        for(RoadAccident accident : accidents) {
            printer.printRecord(getRowContents(accident));
        }
        printer.flush();
    }

    private List<String> getRowContents(RoadAccident accident) {
        List<String> rowContents = new ArrayList<>();
        rowContents.add(accident.getAccidentId());
        rowContents.add(accident.getPoliceForce());
        rowContents.add(accident.getForceContact());
        rowContents.add(accident.getDate().toString());
        rowContents.add(accident.getTime().toString());
        rowContents.add(accident.getTimeOfDay().toString());

        return rowContents;
    }

    public void close() throws IOException {
        printer.close();
    }
}
