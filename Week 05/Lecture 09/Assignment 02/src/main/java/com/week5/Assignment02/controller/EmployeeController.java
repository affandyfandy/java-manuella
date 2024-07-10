package com.week5.Assignment02.controller;

import com.opencsv.CSVReader;
import com.week5.Assignment02.model.Employee;
import com.week5.Assignment02.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileReader;
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

    @GetMapping("/list")
    public String listEmployees(Model theModel){
        List<Employee> employees = employeeService.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
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
    public String showFormForUpdate(@RequestParam("employeeId") Long id, Model theModel){
        Employee theEmployee = employeeService.findById(id);
        theModel.addAttribute("employee", theEmployee);
        return "employees/employee-form";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("employeeId") Long id){
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

                String name = line[1];
                LocalDate dob = LocalDate.parse(line[2], formatter);
                String address = line[3];
                String department = line[4];
                Double salary = Double.parseDouble(line[5]);

                Employee employee = new Employee();
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
}
