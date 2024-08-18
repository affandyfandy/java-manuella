package com.week6.Assignment01.repository;

import com.week6.Assignment01.model.Salary;
import com.week6.Assignment01.model.SalaryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, SalaryId> {
}
