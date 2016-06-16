package com.epam.concurrency.task;

import com.epam.data.RoadAccident;
import com.epam.dataservice.PoliceForceExternalDataService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanmoy on 6/16/2016.
 */
public class AccidentDataEnricher {

    private PoliceForceExternalDataService policeForceService = new PoliceForceExternalDataService();
    private static final int POLICE_FORCE_SERVICE_CALL_TIMEOUT_MS = 1000 * 30;

    public List<RoadAccidentDetails> enrichRoadAccidentData(List<RoadAccident> roadAccidents){
        List<RoadAccidentDetails> roadAccidentDetailsList = new ArrayList<>(roadAccidents.size());
        for(RoadAccident roadAccident : roadAccidents){
            roadAccidentDetailsList.add(enrichRoadAccidentDataItem(roadAccident));
        }
        Util.sleep();
        return roadAccidentDetailsList;
    }

    public RoadAccidentDetails enrichRoadAccidentDataItem(RoadAccident roadAccident){
        RoadAccidentDetails roadAccidentDetails = new RoadAccidentDetails(roadAccident);
        enrichPoliceForceContactSynchronously(roadAccidentDetails);
        /**
         * above call might get blocked causing the application to get stuck
         *
         * solve this problem by accessing the the PoliceForceExternalDataService asynchronously
         * with a timeout of 30 S
         *
         * use method "enrichPoliceForceContactAsynchronously" instead
         */
        return  roadAccidentDetails;
    }

    private void enrichPoliceForceContactSynchronously(RoadAccidentDetails roadAccidentDetails){
        String policeForceContact = policeForceService.getContactNo(roadAccidentDetails.getPoliceForce());
        roadAccidentDetails.setPoliceForceContact(policeForceContact);
    }

    private void enrichPoliceForceContactAsynchronously(RoadAccidentDetails roadAccidentDetails){
        //use POLICE_FORCE_SERVICE_CALL_TIMEOUT_MS
    }
}
