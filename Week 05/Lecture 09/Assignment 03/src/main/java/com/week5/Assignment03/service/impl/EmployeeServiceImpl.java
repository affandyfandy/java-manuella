package com.week5.Assignment03.service.impl;

import com.week5.Assignment03.model.Employee;
import com.week5.Assignment03.repository.EmployeeRepository;
import com.week5.Assignment03.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(String id){
        return employeeRepository.findById(id).orElseThrow();
    }

    @Override
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void deleteById(String id){
        employeeRepository.deleteById(id);
    }

    @Override
    public void saveAll(List<Employee> employees){
        employeeRepository.saveAll(employees);
    }

    @Override
    public String getNameOfHighestPaidEmployee() {
        Employee highestPaidEmployee = employeeRepository.findFirstByOrderBySalaryDesc();
        return highestPaidEmployee != null ? highestPaidEmployee.getName() : "N/A";
    }

    @Override
    public String getNameOfLowestPaidEmployee() {
        Employee lowestPaidEmployee = employeeRepository.findFirstByOrderBySalaryAsc();
        return lowestPaidEmployee != null ? lowestPaidEmployee.getName() : "N/A";
    }

    @Override
    public Long getTotalEmployees() {
        return employeeRepository.count();
    }

    @Override
    public Double getAverageSalary() {
        return employeeRepository.calculateAverageSalary();
    }

    @Override
    public Double getHighestSalary() {
        return employeeRepository.findMaxSalary();
    }

    @Override
    public Double getLowestSalary() {
        return employeeRepository.findMinSalary();
    }
}
