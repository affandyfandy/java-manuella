package com.week5.Assignment03.service;

import com.week5.Assignment03.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();
    Employee findById(String id);
    void save(Employee employee);
    void deleteById(String id);
    void saveAll(List<Employee> employees);
    String getNameOfHighestPaidEmployee();
    String getNameOfLowestPaidEmployee();
    Long getTotalEmployees();
    Double getAverageSalary();
    Double getHighestSalary();
    Double getLowestSalary();
}
