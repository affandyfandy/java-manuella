package com.week5.Assignment03.controller;

import com.opencsv.CSVReader;
import com.week5.Assignment03.model.Employee;
import com.week5.Assignment03.service.EmployeeService;
import com.week5.Assignment03.service.PdfService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final PdfService pdfService;

    @GetMapping("/list")
    public String listEmployees(Model theModel){
        List<Employee> employees = employeeService.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        employees.forEach(employee -> {
            String formattedDate = employee.getDateOfBirth().format(formatter);
            employee.setFormattedDateOfBirth(formattedDate);
        });
        theModel.addAttribute("employees", employees);
        return "employees/list-employees";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel){
        Employee theEmployee = new Employee();
        theModel.addAttribute("employee", theEmployee);
        return "employees/employee-form";
    }

    @PostMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") String id, Model theModel){
        Employee theEmployee = employeeService.findById(id);
        theModel.addAttribute("employee", theEmployee);
        return "employees/employee-form";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("employeeId") String id){
        employeeService.deleteById(id);
        return "redirect:/employees/list";
    }

    @GetMapping("/showUploadForm")
    public String showUploadForm() {
        return "employees/upload-form";
    }

    @PostMapping("/upload")
    public String uploadCSV(@RequestParam("file") MultipartFile file){
        if (file.isEmpty()){
            return "redirect:/employees/list";
        }

        List<Employee> employees = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");

        try (Reader reader = new InputStreamReader(file.getInputStream());
        CSVReader csvReader = new CSVReader(reader)){
            String[] line;
            boolean isHeader = true;

            while ((line = csvReader.readNext()) != null){
                if (isHeader){
                    isHeader = false;
                    continue;
                }

                String id = line[0];
                String name = line[1];
                LocalDate dob = LocalDate.parse(line[2], formatter);
                String address = line[3];
                String department = line[4];
                Double salary = Double.parseDouble(line[5]);

                Employee employee = new Employee();
                employee.setId(id);
                employee.setName(name);
                employee.setDateOfBirth(dob);
                employee.setAddress(address);
                employee.setDepartment(department);
                employee.setSalary(salary);

                employees.add(employee);
            }

            employeeService.saveAll(employees);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/employees/list";
    }

    @GetMapping("/generatePdf")
    public ResponseEntity<InputStreamResource> generatePdf() throws IOException {
        String highestSalaryEmployee = employeeService.getNameOfHighestPaidEmployee();
        Double maxSalary = employeeService.getHighestSalary();
        String lowestSalaryEmployee = employeeService.getNameOfLowestPaidEmployee();
        Double minSalary = employeeService.getLowestSalary();
        Long totalEmployees = employeeService.getTotalEmployees();
        Double averageSalary = employeeService.getAverageSalary();
        List<Employee> employees = employeeService.findAll();
        ByteArrayInputStream bis = pdfService.generateEmployeePdf(employees, "User",
                highestSalaryEmployee, maxSalary, lowestSalaryEmployee, minSalary,
                totalEmployees, averageSalary);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=employees.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
