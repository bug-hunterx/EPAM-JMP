package com.epam.h3;

import java.util.ArrayList;
import java.util.List;

public class HomeWork3 {

	public static void main(String[] args) {
		List<String> pathList = new ArrayList<>();
		pathList.add("src/main/resources/DfTRoadSafety_Accidents_2009");
		
		ReportGenerator.fillData(pathList);
		ReportGenerator.generator();
	}

}
