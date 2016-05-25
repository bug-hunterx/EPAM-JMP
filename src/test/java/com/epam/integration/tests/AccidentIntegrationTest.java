package com.epam.integration.tests;

import com.epam.Main;
import com.epam.data.AccidentsDataLoader;
import com.epam.data.RoadAccident;
import com.epam.processor.DataProcessor;
import com.epam.report.DataReportGenerator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.util.List;

/**
 * Created by rahul.mujnani on 5/05/2016.
 */
@Category(IntegrationTest.class)
public class AccidentIntegrationTest {
        private static final String DAYTIME_ACCIDENTS_CSV = "src/main/resources/DaytimeAccidents.csv";
        private static final String NIGHTTIME_ACCIDENTS_CSV = "src/main/resources/NighttimeAccidents.csv";

 
        @Test
        public void should_Generate_AccidentRecords() {
            //Main.main();
            File dayfile = new File(DAYTIME_ACCIDENTS_CSV);
            Assert.assertTrue(dayfile.exists() && !dayfile.isDirectory());
            File nightfile = new File(NIGHTTIME_ACCIDENTS_CSV);
            Assert.assertTrue(nightfile.exists() && !nightfile.isDirectory());
        }
}




