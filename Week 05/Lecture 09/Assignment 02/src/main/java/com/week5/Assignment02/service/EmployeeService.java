package com.week5.Assignment02.service;

import com.week5.Assignment02.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();
    Employee findById(Long id);
    void save(Employee employee);
    void deleteById(Long id);
    void saveAll(List<Employee> employees);
}
