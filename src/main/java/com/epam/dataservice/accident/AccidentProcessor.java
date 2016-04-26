package com.epam.dataservice.accident;

import java.util.concurrent.BlockingQueue;

import com.epam.data.RoadAccident;
import com.epam.dataservice.PoliceForceService;

public class AccidentProcessor implements Runnable {

	public static String TIME_MORNING = "MORNING";
	public static String TIME_AFTERNOON = "AFTERNOON";
	public static String TIME_EVENING = "EVENING";
	public static String TIME_NIGHT = "NIGHT";

	private BlockingQueue<RoadAccident> readerQueue;
	private BlockingQueue<RoadAccident> daytimeQueue;
	private BlockingQueue<RoadAccident> nighttimeQueue;
	private PoliceForceService policeForceService;

	public AccidentProcessor(BlockingQueue<RoadAccident> readerQueue, BlockingQueue<RoadAccident> daytimeQueue,
			BlockingQueue<RoadAccident> nighttimeQueue) {
		this.readerQueue = readerQueue;
		this.daytimeQueue = daytimeQueue;
		this.nighttimeQueue = nighttimeQueue;
		this.policeForceService = new PoliceForceService();
	}

	@Override
	public void run() {
		try {
			System.out.println("processor running...");
			while (!Thread.currentThread().isInterrupted()) {
				RoadAccident roadAccident = this.readerQueue.take();
				roadAccident.setForceContact(this.policeForceService.getContactNo(roadAccident.getPoliceForce()));

				if (roadAccident.getTime().getHour() >= 18) {
					roadAccident.setTimeosDay(TIME_NIGHT);
					nighttimeQueue.put(roadAccident);
				} else if (roadAccident.getTime().getHour() >= 12) {
					roadAccident.setTimeosDay(TIME_AFTERNOON);
					daytimeQueue.put(roadAccident);
				} else if (roadAccident.getTime().getHour() >= 6) {
					roadAccident.setTimeosDay(TIME_MORNING);
					daytimeQueue.put(roadAccident);
				} else {
					roadAccident.setTimeosDay(TIME_EVENING);
					nighttimeQueue.put(roadAccident);
				}
			}
		} catch (InterruptedException e) {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}