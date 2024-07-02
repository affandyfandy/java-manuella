package com.week4.Assignment7_2.service;

import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Override
    public void sendEmail(String to, String content) {
        System.out.println("Email to " + to);
        System.out.println("Content: " + content);

    }
}