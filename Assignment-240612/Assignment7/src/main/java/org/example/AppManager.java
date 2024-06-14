package org.example;

import com.opencsv.CSVReader;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AppManager {
    private static AppManager instance;
    private final List<Employee> employees;

    private AppManager(){
        employees = new ArrayList<>();
    }

    public static AppManager getInstance(){
        if (instance == null){
            instance = new AppManager();
        }
        return instance;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toLocalDate().toString();
                } else {
                    return String.valueOf((int) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    public void importData(String path) throws IOException {
        if (path.endsWith(".xlsx")) {
            importDataXlsx(path);
            return;
        }
        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            List<String[]> lines = reader.readAll();
            for (int i = 1; i < lines.size(); i++) { // Skip header row
                String[] datas = lines.get(i);
                String id = datas[0];
                String name = datas[1];
                LocalDate dob = DateUtils.parseData(datas[2]);
                String address = datas[3];
                String department = datas[4];
                employees.add(new Employee(id, name, dob, address, department));
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(employees.size() + " lines of data.");
    }

    public void importDataXlsx(String path) throws IOException{
        try (FileInputStream file = new FileInputStream(path);
             Workbook workbook = new XSSFWorkbook(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                String id = getCellValue(row.getCell(0));
                String name = getCellValue(row.getCell(1));
                LocalDate dob = DateUtils.parseData(getCellValue(row.getCell(2)));
                String address = getCellValue(row.getCell(3));
                String department = getCellValue(row.getCell(4));
                employees.add(new Employee(id, name, dob, address, department));
            }
        }
        System.out.println(employees.size() + " lines of data.");
    }

    public void addEmployee(String id, String name, LocalDate dob, String address, String department){
        employees.add(new Employee(id, name, dob, address, department));
    }

    public List<Employee> filterEmployee(String id, String name, Integer year, String department){
        return employees.stream()
                .filter(e -> (name == null || name.isEmpty() || e.getName().contains(name)))
                .filter(e -> (id == null || id.isEmpty() || e.getId().equals(id)))
                .filter(e -> (year == null || e.getDob().getYear() == year))
                .filter(e -> (department == null || department.isEmpty() || e.getDepartment().equals(department)))
                .collect(Collectors.toList());
    }

    public void exportToCsv(String path, List<Employee> employeeList) throws IOException {
        List<String> lines = new ArrayList<>();
        // Add header
        lines.add("ID,Name,DateOfBirth,Address,Department");

        // Add employee data
        lines.addAll(employeeList.stream()
                .sorted(Comparator.comparing(Employee::getDob, Comparator.nullsLast(Comparator.naturalOrder())))
                .map(Employee::toString)
                .toList());

        FileUtils.writeFile(path, lines);
    }
}
