package com.epam.dataservice;

import com.epam.data.RoadAccident;
import com.epam.data.TimeOfDay;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by rahul.mujnani on 5/3/2016.
 */
public class AccidentEnricher implements Runnable{
    private BlockingQueue<CopyOnWriteArrayList<RoadAccident>> accidentDataInputQueue;
    private BlockingQueue<CopyOnWriteArrayList<RoadAccident>> dayTimeQueue;
    private BlockingQueue<CopyOnWriteArrayList<RoadAccident>> nightTimeQueue;

    CopyOnWriteArrayList<RoadAccident> daytimeRoadAccidents = new CopyOnWriteArrayList<RoadAccident>();
    CopyOnWriteArrayList<RoadAccident> nighttimeRoadAccidents = new CopyOnWriteArrayList<RoadAccident>();
    ExecutorService executorService = Executors.newFixedThreadPool(1);

    public AccidentEnricher(BlockingQueue<CopyOnWriteArrayList<RoadAccident>> accidentDataInputQueue,
                            BlockingQueue<CopyOnWriteArrayList<RoadAccident>> dayTimeQueue,
                            BlockingQueue<CopyOnWriteArrayList<RoadAccident>> nightTimeQueue) {

        this.accidentDataInputQueue = accidentDataInputQueue;
        this.dayTimeQueue = dayTimeQueue;
        this.nightTimeQueue = nightTimeQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {

                System.out.println("Enricher  Thread [" + Thread.currentThread().getId() + "] Started  ");
                List<RoadAccident> accidentRecordsPastYears = accidentDataInputQueue.take();
                System.out.println(" Enriching Records of Size " + accidentRecordsPastYears.size());

                for (RoadAccident roadAccident : accidentDataInputQueue.take()) {
                    System.out.println("Enriching Road Accident record with Id: " + roadAccident.getAccidentId());
                    Future<Integer> policeServiceContact = (Future<Integer>) executorService.submit
                            (new GetPoliceServiceResponse(roadAccident));
                    if (policeServiceContact.get() == null)
                        System.out.println("Police Service terminated successfully");

                    roadAccident.setTimeOfDay(TimeOfDay.getTimeOfDay(roadAccident.getTime()));
                    if (roadAccident.getTimeOfDay().equals(TimeOfDay.TimeCategory.MORNING.toString())
                            || roadAccident.getTimeOfDay().equals(TimeOfDay.TimeCategory.AFTERNOON.toString())) {
                        daytimeRoadAccidents.add(roadAccident);
                    } else {
                        if ((roadAccident.getTimeOfDay()).equals(TimeOfDay.TimeCategory.EVENING.toString())
                                || roadAccident.getTimeOfDay().equals(TimeOfDay.TimeCategory.NIGHT.toString())) {
                            nighttimeRoadAccidents.add(roadAccident);
                        }
                    }
                }
                System.out.println("Trying to add data to daytime Queue");
                dayTimeQueue.put(daytimeRoadAccidents);
                System.out.println("Trying to add data to nighttime Queue");
                nightTimeQueue.put(nighttimeRoadAccidents);
                System.out.println("Enricher  Thread [" + Thread.currentThread().getId() + "] completed  ");

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
