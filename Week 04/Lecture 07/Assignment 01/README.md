## Overview
This is a Spring Boot application designed to demonstrate basic concepts of dependency injection and bean management in Spring. The application defines an Employee class and an EmployeeWork class. The Employee class is injected with an instance of EmployeeWork to perform a simple task.

## Structure
```bash
com.week4.Lecture7
├── SimpleDemoApplication.java
├── ApplicationConfig.java
├── EmployeeWork.java
└── entity
    └── Employee.java
```

#### SimpleDemoApplication
This is the main class of the Spring Boot application. It runs the application and demonstrates how to retrieve a bean from the application context.

#### Employee
This class represents an employee entity with properties such as id, name, age, and an instance of EmployeeWork. It includes methods to set and get these properties and a method to perform work.

#### ApplicationConfig
This class is a configuration class that defines the beans for the Spring application context. It includes a bean for EmployeeWork and an example of how to create an Employee bean using constructor and setter injection (commented out).

#### EmployeeWork
This class represents the work that an employee performs. It includes a single method to print a message indicating that the employee is working.

## How to test
In the ApplicationConfig class, there are two commented sections demonstrating how to create Employee beans using constructor injection and field (setter) injection. Comment out each injection to test.

#### Constructor Injection
Constructor injection involves passing the dependencies as parameters to the constructor of the class. This ensures that the Employee object is always created with its dependencies fully initialized. It's a preferred method for mandatory dependencies.
- Uncomment the constructor injection employee bean definition.
- Comment out the setter injection employee bean definition.
- Run the application.

#### Field (Setter) Injection
Field (setter) injection uses setter methods to inject dependencies after the object is constructed. This method allows for optional dependencies and is useful when you want to configure dependencies post-construction.
- Uncomment the setter injection employee bean definition.
- Comment out the constructor injection employee bean definition.
- Run the application.