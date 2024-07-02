package com.week4.Lecture7;

import com.week4.Lecture7.entity.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public EmployeeWork employeeWork(){
        return new EmployeeWork();
    }

    // Constructor injection
//    @Bean
//    public Employee employee(){
//        return new Employee(1, "Alice", 30, employeeWork());
//    }

    // Setter and field injection
//    @Bean
//    public Employee employee(){
//        Employee employee = new Employee();
//        employee.setId(1);
//        employee.setName("Alice");
//        employee.setAge(30);
//        employee.setEmployeeWork(employeeWork());
//        return employee;
//    }
}
