package com.epam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

//@SpringBootApplication
@EnableAutoConfiguration
@ImportResource("classpath:spring/spring-config.xml")
public class AccidentsManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccidentsManagementApplication.class, args);
	}
}
