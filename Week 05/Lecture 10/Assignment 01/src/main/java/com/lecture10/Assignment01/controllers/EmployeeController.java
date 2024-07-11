package com.lecture10.Assignment01.controllers;

import com.lecture10.Assignment01.dto.EmployeeDTO;
import com.lecture10.Assignment01.data.models.Employee;
import com.lecture10.Assignment01.service.EmployeeService;
import com.lecture10.Assignment01.util.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/employee")
@AllArgsConstructor
public class EmployeeController {
    @Autowired
    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> listAllEmployees() {
        List<EmployeeDTO> listEmployeeDTO = employeeService.listAllEmployees();
        if (listEmployeeDTO.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listEmployeeDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EmployeeDTO> findEmployee(@PathVariable("id") UUID id) {
        Optional<EmployeeDTO> employeeOpt = employeeService.findEmployeeById(id);
        return employeeOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO savedEmployeeDTO = employeeService.saveEmployee(employeeDTO);
        return ResponseEntity.ok(savedEmployeeDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable(value = "id") UUID id,
                                                      @RequestBody EmployeeDTO employeeDTO) {
        Optional<EmployeeDTO> updatedEmployeeOpt = employeeService.updateEmployee(id, employeeDTO);
        return updatedEmployeeOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable(value = "id") UUID id) {
        if (employeeService.deleteEmployee(id)) {
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
                        UUID id = UUID.fromString(fields[0].trim());
                        String name = fields[1].trim();
                        LocalDate dob = DateUtil.parseData(fields[2].trim());
                        String address = fields[3].trim();
                        String department = fields[4].trim();
                        String email = fields[5].trim();
                        String phone = fields[6].trim();

                        return new Employee(id, name, dob, address, department, email, phone);
                    })
                    .collect(Collectors.toList());
            employeeService.saveAllEmployees(employees);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        }
    }

    @GetMapping(value = "/department")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByDepartment(@RequestParam(value = "department") String department) {
        List<EmployeeDTO> employeesDTO = employeeService.getEmployeesByDepartment(department);
        if (employeesDTO.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employeesDTO);
    }
}