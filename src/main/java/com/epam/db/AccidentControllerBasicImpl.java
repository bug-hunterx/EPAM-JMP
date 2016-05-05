package com.epam.db;

import com.epam.data.RoadAccident;
import com.epam.data.RoadAccidentBuilder;
import com.epam.processor.AccidentsController;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;

/**
 * Created by Tkachi on 5/5/2016.
 */
public class AccidentControllerBasicImpl implements AccidentsController {

    @Autowired
    private Connection connection;

    @Override
    public RoadAccident findOne(String accidentId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select *  from accidents where Accident_Index = ?;");
            preparedStatement.setString(0, accidentId);
            ResultSet accidentResultSet = preparedStatement.executeQuery();

            return new RoadAccidentBuilder(accidentId)
                    .withLongitude(accidentResultSet.getFloat("Longitude"))
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(RoadAccident roadAccident) {

    }

    @Override
    public void delete(RoadAccident roadAccident) {

    }

    @Override
    public Iterable<RoadAccident> getAllMorningAccidents() {
        return null;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
