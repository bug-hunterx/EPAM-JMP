package com.epam.db;

import org.hsqldb.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Olga on 04.05.2016.
 */
public class HsqlInit {
    private static Server hsqlServer = null;
    private static Connection connection = null;

    public void initTablesFromFiles(Connection connection){
        try {
            String workingDir = System.getProperty("user.dir");

            connection.prepareStatement("CREATE TEXT TABLE accident_severity(\n" +
                    "   code  INTEGER  NOT NULL PRIMARY KEY \n" +
                    "  ,label VARCHAR(70) NOT NULL\n" +
                    ");").execute();
            connection.prepareStatement("SET TABLE accident_severity SOURCE \"" + workingDir +"\\src\\main\\resources\\accident_severity.csv;ignore_first=true\"").execute();

            connection.prepareStatement("CREATE TEXT TABLE district_authority(\n" +
                    "   code  INTEGER  NOT NULL PRIMARY KEY \n" +
                    "  ,label VARCHAR(70) NOT NULL\n" +
                    ");").execute();
            connection.prepareStatement("SET TABLE district_authority SOURCE \"" + workingDir +"\\src\\main\\resources\\district_authority.csv;ignore_first=true\"").execute();

            connection.prepareStatement("CREATE TEXT TABLE light_condition(\n" +
                    "   code  INTEGER  NOT NULL PRIMARY KEY \n" +
                    "  ,label VARCHAR(70) NOT NULL\n" +
                    ");").execute();
            connection.prepareStatement("SET TABLE light_condition SOURCE \"" + workingDir +"\\src\\main\\resources\\light_conditions.csv;ignore_first=true\"").execute();

            connection.prepareStatement("CREATE TEXT TABLE police_force(\n" +
                    "   code  INTEGER  NOT NULL PRIMARY KEY \n" +
                    "  ,label VARCHAR(70) NOT NULL\n" +
                    ");").execute();
            connection.prepareStatement("SET TABLE police_force SOURCE \"" + workingDir +"\\src\\main\\resources\\police_force.csv;ignore_first=true\"").execute();

            connection.prepareStatement("CREATE TEXT TABLE road_surface(\n" +
                    "   code  INTEGER  NOT NULL PRIMARY KEY \n" +
                    "  ,label VARCHAR(70) NOT NULL\n" +
                    ");").execute();
            connection.prepareStatement("SET TABLE road_surface SOURCE \"" + workingDir +"\\src\\main\\resources\\road_surface.csv;ignore_first=true\"").execute();

            connection.prepareStatement("CREATE TEXT TABLE weather_conditions(\n" +
                    "   code  INTEGER  NOT NULL PRIMARY KEY \n" +
                    "  ,label VARCHAR(70) NOT NULL\n" +
                    ");").execute();
            connection.prepareStatement("SET TABLE weather_conditions SOURCE \"" + workingDir +"\\src\\main\\resources\\weather_conditions.csv;ignore_first=true\"").execute();

            connection.prepareStatement("CREATE TEXT TABLE accidents(\n" +
                    "   Accident_Index             VARCHAR(20) NOT NULL PRIMARY KEY\n" +
                    "  ,Longitude                  NUMERIC(10,20) NOT NULL\n" +
                    "  ,Latitude                   NUMERIC(10,20) NOT NULL\n" +
                    "  ,Police_Force               INTEGER  NOT NULL\n" +
                    "  ,Accident_Severity          INTEGER  NOT NULL\n" +
                    "  ,Number_of_Vehicles         INTEGER  NOT NULL\n" +
                    "  ,Number_of_Casualties       INTEGER  NOT NULL\n" +
                    "  ,Date                       DATE  NOT NULL\n" +
                    "  ,Day_of_Week                INTEGER  NOT NULL\n" +
                    "  ,Time                       VARCHAR(15) NOT NULL\n" +
                    "  ,Local_Authority_District   INTEGER  NOT NULL\n" +
                    "  ,Light_Conditions           INTEGER  NOT NULL\n" +
                    "  ,Weather_Conditions         INTEGER  NOT NULL\n" +
                    "  ,Road_Surface_Conditions    INTEGER  NOT NULL\n" +
                    ");").execute();
            connection.prepareStatement("SET TABLE accidents SOURCE \"" + workingDir +"\\src\\main\\resources\\DfTRoadSafety_Accidents_2009.csv;ignore_first=true\"").execute();

            // query from the db
            ResultSet rs = connection.prepareStatement("select code, label  from accident_severity;").executeQuery();
            while(rs.next()) {
                System.out.println(String.format("Severity_Id: %1d, Severity: %1s", rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static Connection initDatabase(){
        System.setProperty("textdb.allow_full_path", "true");
        hsqlServer = new Server();
        hsqlServer.setLogWriter(null);
        hsqlServer.setSilent(true);
        hsqlServer.setDatabaseName(0, "jmp");
        hsqlServer.setDatabasePath(0, "file:jmpdb");

        hsqlServer.start();

        // making a connection
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/jmp", "sa", ""); // can through sql exception
        } catch (SQLException e2) {
            e2.printStackTrace();
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        }
        return connection;
       // end of stub code for in/out stub
    }

    public void stopDatabase(){
        hsqlServer.stop();
        hsqlServer = null;
    }
}
