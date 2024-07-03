package com.example.SimpleApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimpleAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(SimpleAppApplication.class, args);
		System.out.println("This SpringBoot project is running...");
	}
}