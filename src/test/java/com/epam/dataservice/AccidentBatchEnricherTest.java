package com.epam.dataservice;

import com.epam.data.RoadAccident;
import com.epam.data.RoadAccidentBuilder;
import com.epam.data.TimeOfDay;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccidentBatchEnricherTest {

    private AccidentBatchEnricher enricher;
    @Mock
    private PoliceForceService policeForceService;
    private BlockingQueue<List<RoadAccident>> inboundQueue;
    private BlockingQueue<List<RoadAccident>> daytimeOutboundQueue;
    private BlockingQueue<List<RoadAccident>> nighttimeOutboundQueue;

    @Before
    public void init(){
        when(policeForceService.getContactNo(anyString())).thenAnswer(mockContactNo());
        this.inboundQueue = new ArrayBlockingQueue<List<RoadAccident>>(5);
        this.daytimeOutboundQueue = new ArrayBlockingQueue<List<RoadAccident>>(5);
        this.nighttimeOutboundQueue = new ArrayBlockingQueue<List<RoadAccident>>(5);

        enricher = new AccidentBatchEnricher(policeForceService, inboundQueue, daytimeOutboundQueue, nighttimeOutboundQueue);
    }

    @Test
    public void whenRoadAccidentComesWithPoliceForceThenContactInformationShouldBeEnriched() throws InterruptedException {
        RoadAccident dummyRoadAccident = createRoadAccident("mockPoliceForce", 0);
        inboundQueue.put(ImmutableList.of(dummyRoadAccident));

        Thread enricherThread = new Thread(enricher);
        enricherThread.start();

        List<RoadAccident> outputRoadAccidents = nighttimeOutboundQueue.take();
        assertThat(outputRoadAccidents.size()).isEqualTo(1);
        RoadAccident outputRoadAccident = outputRoadAccidents.get(0);
        assertThat(outputRoadAccident.getForceContact()).isEqualTo("Contact of mockPoliceForce");

        enricherThread.interrupt();
    }

    @Test
    public void whenRoadAccidentComesWithTimeIsMorningHourThenDayTimeShouldBeEnrichedAndDeliverToDayOutboundQueue() throws InterruptedException {
        RoadAccident dummyRoadAccident = createRoadAccident("mockPoliceForce", 8);
        inboundQueue.put(ImmutableList.of(dummyRoadAccident));

        Thread enricherThread = new Thread(enricher);
        enricherThread.start();

        List<RoadAccident> outputRoadAccidents = daytimeOutboundQueue.take();
        assertThat(outputRoadAccidents.size()).isEqualTo(1);
        RoadAccident outputRoadAccident = outputRoadAccidents.get(0);
        assertThat(outputRoadAccident.getDayTime()).isEqualTo(TimeOfDay.MORNING);

        enricherThread.interrupt();
    }

    @Test
    public void whenRoadAccidentComesWithTimeIsAfternoonHourThenDayTimeShouldBeEnrichedAndDeliverToDayOutboundQueue() throws InterruptedException {
        RoadAccident dummyRoadAccident = createRoadAccident("mockPoliceForce", 14);
        inboundQueue.put(ImmutableList.of(dummyRoadAccident));

        Thread enricherThread = new Thread(enricher);
        enricherThread.start();

        List<RoadAccident> outputRoadAccidents = daytimeOutboundQueue.take();
        assertThat(outputRoadAccidents.size()).isEqualTo(1);
        RoadAccident outputRoadAccident = outputRoadAccidents.get(0);
        assertThat(outputRoadAccident.getDayTime()).isEqualTo(TimeOfDay.AFTERNOON);

        enricherThread.interrupt();
    }

    @Test
    public void whenRoadAccidentComesWithTimeIsEveningHourThenDayTimeShouldBeEnrichedAndDeliverToNightOutboundQueue() throws InterruptedException {
        RoadAccident dummyRoadAccident = createRoadAccident("mockPoliceForce", 20);
        inboundQueue.put(ImmutableList.of(dummyRoadAccident));

        Thread enricherThread = new Thread(enricher);
        enricherThread.start();

        List<RoadAccident> outputRoadAccidents = nighttimeOutboundQueue.take();
        assertThat(outputRoadAccidents.size()).isEqualTo(1);
        RoadAccident outputRoadAccident = outputRoadAccidents.get(0);
        assertThat(outputRoadAccident.getDayTime()).isEqualTo(TimeOfDay.EVENING);

        enricherThread.interrupt();
    }

    @Test
    public void whenRoadAccidentComesWithTimeIsNightHourThenDayTimeShouldBeEnrichedAndDeliverToNightOutboundQueue() throws InterruptedException {
        RoadAccident dummyRoadAccident = createRoadAccident("mockPoliceForce", 2);
        inboundQueue.put(ImmutableList.of(dummyRoadAccident));

        Thread enricherThread = new Thread(enricher);
        enricherThread.start();

        List<RoadAccident> outputRoadAccidents = nighttimeOutboundQueue.take();
        assertThat(outputRoadAccidents.size()).isEqualTo(1);
        RoadAccident outputRoadAccident = outputRoadAccidents.get(0);
        assertThat(outputRoadAccident.getDayTime()).isEqualTo(TimeOfDay.NIGHT);

        enricherThread.interrupt();
    }

    private RoadAccident createRoadAccident(String policeForce, int hourOfAccidentTime){
        RoadAccident accident = new RoadAccidentBuilder("mockAccidentId")
                .withPoliceForce(policeForce)
                .withTime(LocalTime.of(hourOfAccidentTime, 0))
                .build();
        return accident;
    }

    private Answer<String> mockContactNo(){
        return new Answer<String>(){
            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return "Contact of " + invocationOnMock.getArguments()[0];
            }
        };
    }
}
