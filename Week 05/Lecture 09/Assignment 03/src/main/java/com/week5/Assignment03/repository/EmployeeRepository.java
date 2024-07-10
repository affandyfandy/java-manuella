package com.week5.Assignment03.repository;

import com.week5.Assignment03.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee,String> {
    Employee findFirstByOrderBySalaryDesc();

    Employee findFirstByOrderBySalaryAsc();

    @Query("SELECT AVG(e.salary) FROM Employee e")
    Double calculateAverageSalary();

    @Query("SELECT MAX(e.salary) FROM Employee e")
    Double findMaxSalary();

    @Query("SELECT MIN(e.salary) FROM Employee e")
    Double findMinSalary();
}
