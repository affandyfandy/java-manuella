package com.week6.Assignment01.service;

import com.week6.Assignment01.model.Employee;
import com.week6.Assignment01.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Page<Employee> getAllEmployees(Pageable pageable){
        return employeeRepository.findAll(pageable);
    }

    public Optional<Employee> getEmployeeById(int id){
        return employeeRepository.findById(id);
    }

    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(int id){
        employeeRepository.deleteById(id);
    }

    public List<Employee> search(String search) {
        EmployeeSpecificationsBuilder builder = new EmployeeSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<Employee> spec = builder.build();
        return employeeRepository.findAll(spec);
    }
}
