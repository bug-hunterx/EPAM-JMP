package com.epam.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by Olga on 04.05.2016.
 */
public class HsqlInitTest {

    HsqlInit hsqlInit;
    Connection connection;

    @Before
    public void init(){
        hsqlInit = new HsqlInit();
        connection = hsqlInit.initDatabase();
    }

    @Test
    public void runThisTestToBuildHSQLDBLocally() throws SQLException {

        hsqlInit.initTablesFromFiles(connection);

        // query from the db
        ResultSet rs = connection.prepareStatement("select code, label  from accident_severity;").executeQuery();
        while(rs.next()) {
            System.out.println(String.format("Severity_Id: %1d, Severity: %1s", rs.getInt(1), rs.getString(2)));
        }

        rs = connection.prepareStatement("select *  from accidents limit 5;").executeQuery();

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = rs.getString(i);
                System.out.print(columnValue + " " + rsmd.getColumnName(i));
            }
            System.out.println("");
        }

    }

    @After
    public void shutdown(){
        hsqlInit.stopDatabase();
    }
}