package com.epam.dataservice;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;

import com.epam.data.RoadAccident.DAYTIMETYPE;

import org.junit.BeforeClass;
import org.junit.Test;

public class DaytimeTypeServiceTest {
	private static DaytimeTypeService testService;

	@BeforeClass
	public static void init() {
		testService = new DaytimeTypeService();
	}

	@Test
	public void testGetDaytimeType_night_boundCase_1() {
		assertEquals("get normal daytime type expected to be night:",
				testService.getDaytimeType(LocalTime.of(0, 0, 0)),
				DAYTIMETYPE.NIGHT);
	}

	@Test
	public void testGetDaytimeType_night_boundCase_2() {
		assertEquals("get normal daytime type expected to be night:",
				testService.getDaytimeType(LocalTime.of(5, 59, 59)),
				DAYTIMETYPE.NIGHT);
	}

	@Test
	public void testGetDaytimeType_morning_boundCase_1() {
		assertEquals("get normal daytime type expected to be morning:",
				testService.getDaytimeType(LocalTime.of(6, 0, 0)),
				DAYTIMETYPE.MORNING);
	}

	@Test
	public void testGetDaytimeType_morning_boundCase_2() {

		assertEquals("get normal daytime type expected to be morning:",
				testService.getDaytimeType(LocalTime.of(11, 59, 59)),
				DAYTIMETYPE.MORNING);

	}

	@Test
	public void testGetDaytimeType_afternoon_boundCase_1() {
		assertEquals("get normal daytime type expected to be afternoon:",
				testService.getDaytimeType(LocalTime.of(12, 0, 0)),
				DAYTIMETYPE.AFTERNOON);

	}

	@Test
	public void testGetDaytimeType_afternoon_boundCase_2() {
		assertEquals("get normal daytime type expected to be afternoon:",
				testService.getDaytimeType(LocalTime.of(17, 59, 59)),
				DAYTIMETYPE.AFTERNOON);
	}

	@Test
	public void testGetDaytimeType_evening_boundCase_1() {
		assertEquals("get normal daytime type expected to be evening:",
				testService.getDaytimeType(LocalTime.of(18, 0, 0)),
				DAYTIMETYPE.EVENING);
	}

	@Test
	public void testGetDaytimeType_evening_boundCase_2() {
		assertEquals("get normal daytime type expected to be evening:",
				testService.getDaytimeType(LocalTime.of(23, 59, 59)),
				DAYTIMETYPE.EVENING);
	}

	@Test
	public void testGetDaytimeType_null() {
		assertEquals("get null daytime type expected to be null:",
				testService.getDaytimeType(null), null);
	}
}
