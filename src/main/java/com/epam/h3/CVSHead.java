package com.epam.h3;

import java.util.ArrayList;
import java.util.List;

public class CVSHead {
	
	private static List<String> TITLE_LIST = new ArrayList<>();
	
	static{
		TITLE_LIST.add("Accident_Index");
		TITLE_LIST.add("Index");
		TITLE_LIST.add("Latitude");
		TITLE_LIST.add("Police_Force");
		TITLE_LIST.add("Accident_Severity");
		TITLE_LIST.add("Number_of_Vehicles");
		TITLE_LIST.add("Number_of_Casualties");
		TITLE_LIST.add("Date");
		TITLE_LIST.add("Day_of_Week");
		TITLE_LIST.add("Time");
		TITLE_LIST.add("Local_Authority_(District)");
		TITLE_LIST.add("Light_Conditions");
		TITLE_LIST.add("Weather_Conditions");
		TITLE_LIST.add("Road_Surface_Conditions");
		TITLE_LIST.add("ForceContact");
		TITLE_LIST.add("Type");
	}
	
	public static List<String> getTitleList(){
		synchronized (TITLE_LIST) {
			return TITLE_LIST;
		}
	}
}
