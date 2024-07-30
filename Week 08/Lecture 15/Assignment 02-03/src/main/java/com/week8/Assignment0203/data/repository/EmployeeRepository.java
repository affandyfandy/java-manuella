package com.week8.Assignment0203.data.repository;

import com.week8.Assignment0203.data.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
