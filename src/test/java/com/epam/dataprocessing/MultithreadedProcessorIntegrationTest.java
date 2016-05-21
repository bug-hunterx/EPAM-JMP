package com.epam.dataprocessing;

import com.epam.dataprocessing.MultithreadedProcessor;
import com.epam.it.IntegrationTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Alexey on 03.05.2016.
 */
@Category(IntegrationTest.class)
public class MultithreadedProcessorIntegrationTest {

    @Test
    public void should_write_two_files() throws IOException, InterruptedException {
        MultithreadedProcessor.process();
        File day = new File("day.csv");
        File night = new File("night.csv");

        assertThat(day.exists(), is(true));
        assertThat(night.exists(), is(true));
    }
}
