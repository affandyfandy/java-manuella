package com.lecture8.EmployeeCRUD.controllers;

import com.lecture8.EmployeeCRUD.dao.EmployeeDAO;
import com.lecture8.EmployeeCRUD.models.Employee;
import com.lecture8.EmployeeCRUD.utils.DateUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/employee")
@AllArgsConstructor
public class EmployeeController {

    @Autowired
    private final EmployeeDAO employeeDAO;

    @GetMapping
    public ResponseEntity<List<Employee>> listAllEmployee() {
        List<Employee> listEmployee = employeeDAO.findAll();
        if (listEmployee.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listEmployee);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Employee> findEmployee(@PathVariable("id") String id) {
        Employee employee = employeeDAO.findById(id);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
        employeeDAO.save(employee);
        return ResponseEntity.ok(employee);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") String id,
                                                   @RequestBody Employee employeeForm) {
        Employee employee = employeeDAO.findById(id);
        if (employee != null) {
            employee.setName(employeeForm.getName());
            employee.setDob(employeeForm.getDob());
            employee.setAddress(employeeForm.getAddress());
            employee.setDepartment(employeeForm.getDepartment());

            employeeDAO.update(employee);
            return ResponseEntity.ok(employee);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable(value = "id") String id) {
        Employee employee = employeeDAO.findById(id);
        if (employee != null) {
            employeeDAO.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/upload-csv")
    public ResponseEntity<String> uploadCSVFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            List<Employee> employees = reader.lines()
                    .skip(1)
                    .map(line -> {
                        String[] fields = line.split(",");
                        String id = fields[0].trim();
                        String name = fields[1].trim();
                        LocalDate dob = DateUtils.parseData(fields[2].trim());
                        String address = fields[3].trim();
                        String department = fields[4].trim();

                        if (id.isEmpty() || dob == null) {
                            return null;
                        } else {
                            return new Employee(id, name, dob, address, department);
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            employeeDAO.saveAll(employees);
            return ResponseEntity.ok("File uploaded without warning\n");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while processing the file: " + e.getMessage());
        }
    }

    @GetMapping(value = "/department")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@RequestParam(value = "department") String department) {
        List<Employee> employees = employeeDAO.findByDepartment(department);
        if (employees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employees);
    }

    @PostMapping(value = "/transfer-engineering-employees")
    public ResponseEntity<String> transferEngineeringEmployees() {
        try {
            employeeDAO.transferEmployeesToNewDatabase();
            return ResponseEntity.ok("Engineering employees transferred successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to transfer engineering employees: " + e.getMessage());
        }
    }

    @PostMapping(value = "/failed-transfer-engineering-employees")
    public ResponseEntity<String> transferEngineeringEmployeesFail() {
        try {
            employeeDAO.transferEmployeesToNewDatabaseFail();
            return ResponseEntity.ok("Engineering transferred successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to transfer engineering employees: " + e.getMessage());
        }
    }
}
