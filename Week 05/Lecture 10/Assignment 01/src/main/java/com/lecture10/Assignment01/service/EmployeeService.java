package com.lecture10.Assignment01.service;

import com.lecture10.Assignment01.dto.EmployeeDTO;
import com.lecture10.Assignment01.data.models.Employee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeService {
    List<EmployeeDTO> listAllEmployees();
    Optional<EmployeeDTO> findEmployeeById(UUID id);
    EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);
    Optional<EmployeeDTO> updateEmployee(UUID id, EmployeeDTO employeeDTO);
    boolean deleteEmployee(UUID id);
    void saveAllEmployees(List<Employee> employees);
    List<EmployeeDTO> getEmployeesByDepartment(String department);
}
