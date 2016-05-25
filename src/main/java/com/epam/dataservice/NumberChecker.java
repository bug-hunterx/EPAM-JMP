package com.epam.dataservice;

public class NumberChecker {
	private PoliceForceService policeForceService;

	public NumberChecker(PoliceForceService policeForceService) {
		this.policeForceService = policeForceService;
	}

	public boolean isSpecialNumber(String policeForceName) {
		String contactNumber = policeForceService.getContactNo(policeForceName);
		return "ABCDEFGH".equals(contactNumber);
	}
}
