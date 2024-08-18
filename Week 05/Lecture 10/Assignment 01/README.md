## Assignment 01: Employee Management System

### Overview
This project is a simple web application built using Spring Boot for managing employee records. It provides basic CRUD functionalities (Create, Read, Update, Delete) for employees stored in a PostgreSQL database.

### Technologies Used
- Spring Boot: Framework for creating and running Spring-based applications.
- PostgreSQL: Open-source relational database management system.

### Features
- List Employees: View a list of all employees with options to update or delete each record.
- Add Employee: Add a new employee to the database.
- Update Employee: Modify existing employee details.
- Delete Employee: Remove an employee from the database.

### Project Structure
```cmd
└───main
    ├───java
    │   └───com
    │       └───lecture10
    │           └───Assignment01
    │               │   EmployeeCrudApplication.java
    │               │
    │               ├───controllers
    │               │       EmployeeController.java
    │               │
    │               ├───data
    │               │   ├───models
    │               │   │       Employee.java
    │               │   │
    │               │   └───repository
    │               │           EmployeeRepository.java
    │               │
    │               ├───dto
    │               │       EmployeeDTO.java
    │               │
    │               ├───exception
    │               │       BusinessException.java
    │               │       GlobalExceptionHandler.java
    │               │       ResourceNotFoundException.java
    │               │       ValidationException.java
    │               │
    │               ├───mapper
    │               │       EmployeeMapper.java
    │               │
    │               ├───service
    │               │   │   EmployeeService.java
    │               │   │
    │               │   └───impl
    │               │           EmployeeServiceImpl.java
    │               │
    │               └───util
    │                       DateUtil.java
    │
    └───resources
            application.properties
```

### Running the Application
1. Create PostgreSQL database named lecture10_1, configure the username and password in the `application.properties`
2. Execute `mvn spring-boot:run` in the project directory
3. The application will be accessible at `http://localhost:8080`

### API List
1. List all Employees GET (/api/employees): Retrieves a list of all employees stored in the database. Return JSON array containing employee details including id, name, dateOfBirth, address, department, email, and phone.
![img_1.png](img/img_1.png)
2. Get Employee by ID GET (/api/employees/{id}): Retrieves details of a specific employee based on the id path parameter. Return JSON object containing employee details for the specified id.
![img_3.png](img/img_3.png)
![img_2.png](img/img_2.png)
3. Add Employee POST (/api/employees): Adds a new employee to the database, response body containing JSON object containing name, dateOfBirth, address, department, email, and phone of the new employee. Return JSON object confirming the addition of the new employee with assigned id.
![img_4.png](img/img_4.png)
![img_10.png](img/img_10.png)
![img_11.png](img/img_11.png)
4. Update Employee PUT (/api/employees/{id}): Updates details of an existing employee identified by the id path parameter, request body containing JSON object containing updated name, dateOfBirth, address, department, email, and phone fields. Return JSON object confirming the successful update of employee details.
![img_12.png](img/img_12.png)
![img_13.png](img/img_13.png)
5. Delete Employee DELETE (/api/employees/{id}): Deletes an employee from the database based on the id path parameter. Return JSON object confirming the deletion of the employee.
![img_7.png](img/img_7.png)
![img_6.png](img/img_6.png)
6. Upload CSV File POST (/api/upload-csv): Accepts a CSV file containing employee data, parses it, and saves the employees to the database, request Body containing Form-data with file parameter containing the CSV file. Return JSON object indicating the success or failure of the file upload process.
![img.png](img/img.png)
![img_8.png](img/img_8.png)
