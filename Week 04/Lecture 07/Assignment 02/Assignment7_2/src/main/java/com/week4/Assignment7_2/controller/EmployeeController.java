package com.week4.Assignment7_2.controller;

import com.week4.Assignment7_2.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/notify")
    public String notifyEmployee(@RequestParam String email, @RequestParam String content) {
        employeeService.notifyEmployee(email, content);
        return "Notification sent to " + email;
    }
}
