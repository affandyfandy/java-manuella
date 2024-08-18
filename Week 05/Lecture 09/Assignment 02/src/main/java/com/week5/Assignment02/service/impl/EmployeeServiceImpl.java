package com.week5.Assignment02.service.impl;

import com.week5.Assignment02.model.Employee;
import com.week5.Assignment02.repository.EmployeeRepository;
import com.week5.Assignment02.service.EmployeeService;
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
    public Employee findById(Long id){
        return employeeRepository.findById(id).orElseThrow();
    }

    @Override
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void deleteById(Long id){
        employeeRepository.deleteById(id);
    }

    @Override
    public void saveAll(List<Employee> employees){
        employeeRepository.saveAll(employees);
    }
}
