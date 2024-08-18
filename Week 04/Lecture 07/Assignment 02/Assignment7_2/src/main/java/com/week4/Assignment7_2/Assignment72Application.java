package com.week4.Assignment7_2;

import com.week4.Assignment7_2.service.EmployeeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class Assignment72Application {
	public static void main(String[] args) {
		SpringApplication.run(Assignment72Application.class, args);
		ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

		EmployeeService consEmployeeService = context.getBean(EmployeeService.class);
		consEmployeeService.notifyEmployee("employee@gmail.com", "Hello");
	}
}
