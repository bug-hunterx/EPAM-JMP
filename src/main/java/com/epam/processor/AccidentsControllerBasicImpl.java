package com.epam.processor;

import com.epam.data.RoadAccident;
import com.epam.data.RoadAccidentBuilder;
import com.epam.processor.AccidentsController;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;

/**
 * This is the worst implementation of AccidentController. You task is to fix it with any framework you want.
 */
public class AccidentsControllerBasicImpl implements AccidentsController {

    @Autowired
    private Connection connection;

    @Override
    public RoadAccident findOne(String accidentId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select *  from accidents where Accident_Index = ?;");
            preparedStatement.setString(1, accidentId);
            ResultSet accidentResultSet = preparedStatement.executeQuery();

            if(accidentResultSet.next()) {

                return new RoadAccidentBuilder(accidentId)
                        .withLongitude(accidentResultSet.getFloat("Longitude"))
                        .withLatitude(accidentResultSet.getFloat("Latitude"))
                        //and go on... You got the point - it sucks without JPA/Hibernate/Data - any proper framework
                        .build();
            }
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
