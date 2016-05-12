package com.epam.processor;

import com.epam.data.RoadAccident;
import com.epam.dataservice.AccidentBatchLoaderRunnable;
import com.epam.dataservice.AccidentBatchProcessorRunnable;
import com.epam.dataservice.AccidentBatchWriterRunnable;
import com.epam.report.DataReportGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Nick on 27.04.2016.
 */
public class AccidentConcurrentParser {







    public static void main(String args[]) throws InterruptedException{
        List<String> inputFileNames = new ArrayList<>();

        inputFileNames.add("src/main/resources/DfTRoadSafety_Accidents_2009.csv");
        inputFileNames.add("src/main/resources/DfTRoadSafety_Accidents_2010.csv");
        inputFileNames.add("src/main/resources/DfTRoadSafety_Accidents_2011.csv");
        inputFileNames.add("src/main/resources/DfTRoadSafety_Accidents_2012.csv");

        String outFilePath1 = "src/main/resources/out1.csv";
        String outFilePath2 = "src/main/resources/out2.csv";

        DataReportGenerator dataReportGenerator = new DataReportGenerator(inputFileNames, outFilePath1, outFilePath2);
        dataReportGenerator.generateReport();
    }
}
