package com.week6.Assignment01.service;

import com.week6.Assignment01.model.Salary;
import com.week6.Assignment01.model.SalaryId;
import com.week6.Assignment01.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SalaryService {
    @Autowired
    private SalaryRepository salaryRepository;

    public Salary saveSalary(Salary salary) {
        return salaryRepository.save(salary);
    }

    public Optional<Salary> getSalary(SalaryId id) {
        return salaryRepository.findById(id);
    }
}
