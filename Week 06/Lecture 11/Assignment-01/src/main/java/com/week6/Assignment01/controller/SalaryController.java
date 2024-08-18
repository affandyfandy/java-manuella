package com.week6.Assignment01.controller;

import com.week6.Assignment01.model.Salary;
import com.week6.Assignment01.model.SalaryId;
import com.week6.Assignment01.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/salaries")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @PostMapping
    public ResponseEntity<Salary> createSalary(@RequestBody Salary salary) {
        return new ResponseEntity<>(salaryService.saveSalary(salary), HttpStatus.CREATED);
    }

    @PutMapping("/{empNo}/{fromDate}")
    public ResponseEntity<Salary> updateSalary(@PathVariable int empNo, @PathVariable Date fromDate, @RequestBody Salary salaryDetails) {
        SalaryId salaryId = new SalaryId(empNo, fromDate);
        Optional<Salary> salary = salaryService.getSalary(salaryId);
        if (salary.isPresent()) {
            Salary updatedSalary = salary.get();
            updatedSalary.setSalary(salaryDetails.getSalary());
            updatedSalary.setToDate(salaryDetails.getToDate());
            return new ResponseEntity<>(salaryService.saveSalary(updatedSalary), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
