package com.epam.integration.tests;

import com.epam.data.RoadAccident;
import com.epam.dataservice.PoliceForceService;
import com.epam.report.DataReportGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class IntegrationTest {

    private DataReportGenerator dataReportGenerator;

    private static List<String> inputFileNames;

    private final String testInputFileName = "src/main/resources/DfTRoadSafety_Accidents_2009.csv";
    private final String testOutputMorningFileName = "testOut1.csv";
    private final String testOutputEveningFileName = "testOut2.csv";


    @Before
    public void setUp() throws Exception {
        inputFileNames = new ArrayList<>();
        inputFileNames.add(testInputFileName);

        dataReportGenerator = new DataReportGenerator(inputFileNames, testOutputMorningFileName, testOutputEveningFileName);
    }

    @Test
    public void should_find_by_index7(){
        try {
            dataReportGenerator.generateReport();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        File f1 = new File(testOutputMorningFileName);
        File f2 = new File(testOutputEveningFileName);

        Assert.assertTrue("Output file1 exists", f1.exists());
        Assert.assertTrue("Output file2 exists", f2.exists());
        Assert.assertFalse("File1 is not a directory", f1.isDirectory());
        Assert.assertFalse("File2 is not a directory", f2.isDirectory());

    }

}
