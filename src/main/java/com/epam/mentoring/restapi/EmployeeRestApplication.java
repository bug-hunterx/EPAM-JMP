package com.epam.mentoring.restapi;

import java.time.LocalDate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.epam.mentoring.restapi.modal.Employee;
import com.epam.mentoring.restapi.repository.EmployeeRepository;
import com.epam.mentoring.restapi.web.SecurityFilter;

@SpringBootApplication
public class EmployeeRestApplication {
	public static void main(String[] args) {
 		SpringApplication springApplication = new SpringApplication();
 		ApplicationContext ctx =
 				SpringApplication.run(EmployeeRestApplication.class, args);
 		initDB(ctx);
 	}
 
 	private static void initDB(ApplicationContext ctx){
 		EmployeeRepository deptRepository= (EmployeeRepository) ctx.getBean(EmployeeRepository.class);
 		
 		deptRepository.save(new Employee("E1","1",1l,LocalDate.now()));
 		deptRepository.save(new Employee("E2","2",2l, LocalDate.now()));
 	}
 
 
 	@Bean
 	public FilterRegistrationBean filterRegistrationBean() {
 		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
 		SecurityFilter securityFilter = new SecurityFilter();
 		registrationBean.setFilter(securityFilter);
 		registrationBean.addUrlPatterns("/api/*");
 
 		return registrationBean;
 	}
}
