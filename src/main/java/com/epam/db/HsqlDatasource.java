package com.epam.db;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;

/**
 * Created by Alexey on 13.05.2016.
 */
public class HsqlDatasource extends DriverManagerDataSource {
    public void init() {
        HsqlInit hsqlInit = new HsqlInit();
        Connection connection = hsqlInit.initDatabase();
//        hsqlInit.initTablesFromFiles(connection);
    }
}
