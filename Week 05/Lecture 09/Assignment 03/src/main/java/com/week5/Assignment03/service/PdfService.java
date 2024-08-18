package com.week5.Assignment03.service;

import com.itextpdf.html2pdf.HtmlConverter;
import com.week5.Assignment03.model.Employee;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PdfService {

    private final TemplateEngine templateEngine;

    public PdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public ByteArrayInputStream generateEmployeePdf(List<Employee> employees, String userName,
                                                    String highestSalaryEmployee, Double maxSalary,
                                                    String lowestSalaryEmployee, Double minSalary,
                                                    Long totalEmployees, Double averageSalary) throws IOException {
        Context context = new Context();
        context.setVariable("employees", employees);
        context.setVariable("user", userName);
        context.setVariable("date", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        context.setVariable("nameHighSal", highestSalaryEmployee);
        context.setVariable("maxSalary", maxSalary);
        context.setVariable("nameLowSal", lowestSalaryEmployee);
        context.setVariable("minSalary", minSalary);
        context.setVariable("record", totalEmployees);
        context.setVariable("aveSalary", averageSalary);

        String html = templateEngine.process("pdf/pdf-template", context);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HtmlConverter.convertToPdf(html, outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
}
