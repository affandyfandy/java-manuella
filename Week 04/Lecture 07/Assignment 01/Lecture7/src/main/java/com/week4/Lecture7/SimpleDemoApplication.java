package com.week4.Lecture7;

import com.week4.Lecture7.entity.Employee;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class SimpleDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleDemoApplication.class, args);

		ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		Employee employee = context.getBean(Employee.class);
		employee.working();
	}
}
