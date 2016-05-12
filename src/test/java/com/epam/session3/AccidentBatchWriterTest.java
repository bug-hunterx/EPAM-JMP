package com.epam.session3;

import com.epam.data.RoadAccident;
import com.epam.data.RoadAccidentBuilder;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccidentBatchWriterTest {

    private AccidentBatchWriter writer;

    private BlockingQueue<List<RoadAccident>> inboundQueue;

    @Mock
    private FileWriter fileWriter;

    @Before
    public void init() throws IOException {
        this.inboundQueue = new ArrayBlockingQueue<List<RoadAccident>>(5);

        writer = new AccidentBatchWriter(inboundQueue, "target/test_output_file.csv");
        writer.outputWriter = fileWriter;
    }

    @Test
    public void whenRoadAccidentComesThenShouldBeWriteToFile() throws InterruptedException, IOException {
        RoadAccident dummyRoadAccident = new RoadAccidentBuilder("mockAccidentId").build();
        inboundQueue.put(ImmutableList.of(dummyRoadAccident));
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        Thread writerThread = new Thread(writer);
        writerThread.start();

        Thread.sleep(1000);

        verify(fileWriter, times(2)).write(argumentCaptor.capture());
        List<String> capturedValues = argumentCaptor.getAllValues();
        assertThat(capturedValues).containsExactly(dummyRoadAccident.toString(), "\n");

        writerThread.interrupt();
    }
}
