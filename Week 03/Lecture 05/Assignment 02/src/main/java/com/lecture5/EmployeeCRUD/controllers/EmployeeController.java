package com.lecture5.EmployeeCRUD.controllers;

import com.lecture5.EmployeeCRUD.models.Employee;
import com.lecture5.EmployeeCRUD.repository.EmployeeRepository;
import com.lecture5.EmployeeCRUD.utils.DateUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/employee")
@AllArgsConstructor
public class EmployeeController {
    @Autowired
    private final EmployeeRepository employeeRepository;

    @GetMapping
    public ResponseEntity<List<Employee>> listAllEmployee(){
        List<Employee> listEmployee = employeeRepository.findAll();
        if (listEmployee.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listEmployee);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Employee> findEmployee(@PathVariable("id") String id){
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if (employeeOpt.isPresent()){
            return ResponseEntity.ok(employeeOpt.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Employee> saveContact(@RequestBody Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(savedEmployee);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Employee> updateContact(@PathVariable(value = "id") String id,
                                                  @RequestBody Employee employeeForm) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if(employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            employee.setName(employeeForm.getName());
            employee.setDob(employeeForm.getDob());
            employee.setAddress(employeeForm.getAddress());
            employee.setDepartment(employeeForm.getDepartment());

            Employee updatedContact = employeeRepository.save(employee);
            return ResponseEntity.ok(updatedContact);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Employee> deleteContact(@PathVariable(value = "id") String id) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if(employeeOpt.isPresent()) {
            employeeRepository.delete(employeeOpt.get());
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
                        }
                        else{
                            Employee e = new Employee(id, name, dob, address, department);
                            return e;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
                employeeRepository.saveAll(employees);
            return ResponseEntity.ok("File uploaded without warning\n");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while processing the file: " + e.getMessage());
        }
    }

    @GetMapping(value = "/department")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@RequestParam(value = "department") String department) {
        List<Employee> employees = employeeRepository.findByDepartment(department);
        if (employees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employees);
    }
}
