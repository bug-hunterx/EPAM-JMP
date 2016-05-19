package com.epam.mentoring.restapi;

import com.epam.mentoring.restapi.modal.Department;
import com.epam.mentoring.restapi.repository.DepartmentRepository;
import com.epam.mentoring.restapi.web.SecurityFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoRestApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication();
		ApplicationContext ctx =
				SpringApplication.run(DemoRestApplication.class, args);
		initDB(ctx);
	}

	private static void initDB(ApplicationContext ctx){
		DepartmentRepository deptRepository= (DepartmentRepository) ctx.getBean(DepartmentRepository.class);
		deptRepository.save(new Department("HR", "Human Resource"));
		deptRepository.save(new Department("DEV", "Development"));
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
