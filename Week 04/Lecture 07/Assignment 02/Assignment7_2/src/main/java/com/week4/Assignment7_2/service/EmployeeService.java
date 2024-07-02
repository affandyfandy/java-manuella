package com.week4.Assignment7_2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Constructor
//@Service
//public class EmployeeService {
//    private final EmailService emailService;
//
//    @Autowired
//    public EmployeeService(EmailService emailService) {
//        this.emailService = emailService;
//    }
//
//    public void notifyEmployee(String email, String content) {
//        emailService.sendEmail(email, content);
//    }
//}

// Setter
@Service
public class EmployeeService {
    private EmailService emailService;

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void notifyEmployee(String email, String content) {
        emailService.sendEmail(email, content);
    }
}

// Field
//@Service
//public class EmployeeService {
//    @Autowired
//    private EmailService emailService;
//
//    public void notifyEmployee(String email, String content) {
//        emailService.sendEmail(email, content);
//    }
//}