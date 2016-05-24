package com.epam;

import com.epam.db.HsqlInit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.sql.Connection;

/**
 * Created by rahul.mujnani on 5/21/2016.
 */
//@SpringBootApplication
@Configuration
@ImportResource("classpath*:/spring-config.xml")
public class AccidentTrackingMainApplication {

    static HsqlInit hsqlInit;
    static Connection connection;
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication();
        ApplicationContext ctx = SpringApplication.run(AccidentTrackingMainApplication.class, args);
        //initDB(ctx);
    }

    private static void initDB(ApplicationContext ctx){
        hsqlInit = new HsqlInit();
        connection = hsqlInit.initDatabase();
    }

}
