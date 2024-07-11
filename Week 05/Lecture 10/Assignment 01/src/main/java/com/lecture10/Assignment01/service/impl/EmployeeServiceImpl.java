package com.lecture10.Assignment01.service.impl;

import com.lecture10.Assignment01.dto.EmployeeDTO;
import com.lecture10.Assignment01.exception.BusinessException;
import com.lecture10.Assignment01.exception.ResourceNotFoundException;
import com.lecture10.Assignment01.mapper.EmployeeMapper;
import com.lecture10.Assignment01.data.models.Employee;
import com.lecture10.Assignment01.data.repository.EmployeeRepository;
import com.lecture10.Assignment01.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper = EmployeeMapper.INSTANCE;

    @Override
    public List<EmployeeDTO> listAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(employeeMapper::toEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EmployeeDTO> findEmployeeById(UUID id) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if (employeeOpt.isEmpty()) {
            throw new ResourceNotFoundException("Employee not found for this id : " + id);
        }
        return employeeOpt.map(employeeMapper::toEmployeeDTO);
    }

    @Override
    public EmployeeDTO saveEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        if (employeeDTO.getEmail() == null || !employeeDTO.getEmail().contains("@")) {
            throw new BusinessException("Invalid email address");
        }
        if (employeeDTO.getPhone() == null || !employeeDTO.getPhone().matches("^(\\+62|08)\\d{8,}$")) {
            throw new BusinessException("Invalid phone number");
        }
        Employee employee = employeeMapper.toEmployee(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.toEmployeeDTO(savedEmployee);
    }

    @Override
    public Optional<EmployeeDTO> updateEmployee(UUID id, EmployeeDTO employeeDTO) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if (employeeOpt.isEmpty()) {
            throw new ResourceNotFoundException("Employee not found for this id : " + id);
        }

        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            employee.setName(employeeDTO.getName());
            employee.setDob(employeeDTO.getDob());
            employee.setAddress(employeeDTO.getAddress());
            employee.setDepartment(employeeDTO.getDepartment());
            employee.setEmail(employeeDTO.getEmail());
            employee.setPhone(employeeDTO.getPhone());

            Employee updatedEmployee = employeeRepository.save(employee);
            return Optional.of(employeeMapper.toEmployeeDTO(updatedEmployee));
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteEmployee(UUID id) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if (employeeOpt.isEmpty()) {
            throw new ResourceNotFoundException("Employee not found for this id : " + id);
        }
        if (employeeOpt.isPresent()) {
            employeeRepository.delete(employeeOpt.get());
            return true;
        }
        return false;
    }

    @Override
    public void saveAllEmployees(List<Employee> employees) {
        employeeRepository.saveAll(employees);
    }

    @Override
    public List<EmployeeDTO> getEmployeesByDepartment(String department) {
        List<Employee> employees = employeeRepository.findByDepartment(department);
        return employees.stream()
                .map(employeeMapper::toEmployeeDTO)
                .collect(Collectors.toList());
    }
}