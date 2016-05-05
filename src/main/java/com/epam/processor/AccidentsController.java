package com.epam.processor;

import com.epam.data.RoadAccident;

/**
 * Created by Tkachi on 5/5/2016.
 */
public interface AccidentsController {

    RoadAccident findOne(String accidentId);

    void save(RoadAccident roadAccident);

    void delete(RoadAccident roadAccident);

    Iterable<RoadAccident> getAllMorningAccidents();
}
