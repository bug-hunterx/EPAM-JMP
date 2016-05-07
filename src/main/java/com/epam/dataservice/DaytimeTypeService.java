package com.epam.dataservice;

import java.time.LocalTime;

import com.epam.data.RoadAccident.DAYTIMETYPE;

public class DaytimeTypeService {
	public DAYTIMETYPE getDaytimeType(LocalTime time) {
		DAYTIMETYPE timeosDay = null;
		if (time != null) {
			int hour = time.getHour();
			if (hour >= 6 && hour < 12) {
				timeosDay = DAYTIMETYPE.MORNING;
			} else if (hour >= 12 && hour < 18) {
				timeosDay = DAYTIMETYPE.AFTERNOON;
			} else if (hour >= 18 && hour < 24) {
				timeosDay = DAYTIMETYPE.EVENING;
			} else {
				timeosDay = DAYTIMETYPE.NIGHT;
			}
		}
		return timeosDay;
	}
}
