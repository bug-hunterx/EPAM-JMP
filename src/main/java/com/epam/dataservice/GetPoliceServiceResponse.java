package com.epam.dataservice;

import com.epam.data.RoadAccident;

/**
 * Created by rahul.mujnani on 5/7/2016.
 */
public class GetPoliceServiceResponse implements  Runnable {
    private RoadAccident roadAccident;
    private PoliceForceService policeForceService;

    public GetPoliceServiceResponse(RoadAccident roadAccident) {
        this.roadAccident=roadAccident;
        this.policeForceService = new PoliceForceService();
    }

    @Override
    public void run() {
        roadAccident.setForceContact(policeForceService.getContactNo(roadAccident.getPoliceForce()));
    }
}
