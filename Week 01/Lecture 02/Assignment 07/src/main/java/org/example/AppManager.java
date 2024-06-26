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
import java.util.Scanner;
import java.util.stream.Collectors;

public class AppManager {
    private static AppManager instance;
    private final List<Employee> employees;
    private static final Scanner scanner = new Scanner(System.in);

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

    public void importDataOwnImplementation(String path) throws IOException{
        List<String> lines = FileUtils.readFile(path);

        for (String line: lines){
            String[] datas = line.split(",");
            String id = datas[0];
            String name = datas[1];
            LocalDate dob = DateUtils.parseData(datas[2]);
            String address = datas[3];
            String department = datas[4];
            employees.add(new Employee(id, name, dob, address, department));
        }
        System.out.println(employees.size() + " lines of data.");
    }

    public void importData(String path) throws IOException {
        if (path.endsWith(".xlsx")) {
            importDataXlsx(path);
            return;
        }

        // Using OpenCSV
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
                .filter(e -> (year == null || (e.getDob() != null && e.getDob().getYear() == year)))
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

    public void printMenu(String[] options){
        for (String option : options){
            System.out.println(option);
        }
    }

    public List<Employee> filter(){
        System.out.print("Enter id to filter (or leave blank): ");
        String idFilter= scanner.nextLine();
        System.out.print("Enter name to filter (or leave blank): ");
        String nameFilter = scanner.nextLine();
        System.out.print("Enter year of birth to filter (or leave blank): ");
        String yearInp = scanner.nextLine();
        Integer yearFilter = yearInp.isEmpty() ? null : Integer.parseInt(yearInp);
        System.out.print("Enter department to filter (or leave blank): ");
        String departmentFilter = scanner.nextLine();

        List<Employee> filteredEmployees = filterEmployee(
                idFilter,
                nameFilter,
                yearFilter,
                departmentFilter
        );

        System.out.println(filteredEmployees.size());
        System.out.println("Filtered employees:");
        String format = "| %-10s | %-20s | %-15s | %-30s | %-15s |%n";
        System.out.format("+------------+----------------------+-----------------+--------------------------------+-----------------+%n");
        System.out.format("| ID         | Name                 | Date of Birth   | Address                        | Department      |%n");
        System.out.format("+------------+----------------------+-----------------+--------------------------------+-----------------+%n");
        for (Employee employee: filteredEmployees){
            System.out.format(format, employee.getId(), employee.getName(), employee.getDob(), employee.getAddress(), employee.getDepartment());
            System.out.format("+------------+----------------------+-----------------+--------------------------------+-----------------+%n");
        }

        return filteredEmployees;
    }

    public void run() {
        String rootPath = System.getProperty("user.dir");
        rootPath += "\\src\\main\\java\\document";
        String[] options = {"0- break",
                "1- Select file",
                "2- Add new employee",
                "3- Filter by",
                "4- Filter and export"
        };

        while(true){
            FileUtils.printRootPath();
            printMenu(options);
            System.out.print("Enter your input: ");
            int input = scanner.nextInt();
            scanner.nextLine();

            switch(input){
                case 0:
                    System.out.println("Exiting...");
                    return;
                case 1:
                    System.out.print("----------- Select File -----------\n");
                    System.out.print("Enter file path to import data: ");
                    String path = scanner.nextLine();
                    try{
                        System.out.println(rootPath + "\\" + path);
                        if (path.endsWith(".csv")){
                            System.out.print("Choose open method: \n");
                            System.out.print("1. OpenCSV\n");
                            System.out.print("2. CSV reader implementation\n");
                            System.out.print("Choose: ");
                            int method = scanner.nextInt();
                            scanner.nextLine();
                            if (method == 1){
                                importData(rootPath + "\\" + path);
                            }
                            else if (method == 2){
                                importDataOwnImplementation(rootPath + "\\" + path);
                            }
                            else{
                                System.out.println("Method invalid.");
                                break;
                            }
                        }
                        else{
                            importData(rootPath + "\\" + path);
                        }
                    } catch (IOException e){
                        System.out.println("Failed to import: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.print("----------- Add new employee -----------\n");
                    System.out.print("Enter employee id: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter employee name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter employee address: ");
                    String address = scanner.nextLine();
                    System.out.print("Enter employee date of birth (dd/MM/yyyy): ");
                    String dobInp = scanner.nextLine();
                    LocalDate dob = DateUtils.parseData(dobInp);
                    if (dob == null) {
                        System.out.println("Invalid date format.");
                        break;
                    }
                    System.out.print("Enter employee department: ");
                    String department = scanner.nextLine();

                    addEmployee(id, name, dob, address, department);
                    System.out.println("Employee added.");
                    break;
                case 3:
                    System.out.print("----------- Filter by -----------\n");
                    filter();
                    break;
                case 4:
                    System.out.print("----------- Filter and export -----------\n");
                    List<Employee> employeeList = filter();
                    System.out.println("Enter file path: ");
                    String exportPath = scanner.nextLine();
                    try{
                        exportToCsv(rootPath + "\\" + exportPath, employeeList);
                    } catch (IOException e){
                        System.out.println("Failed to export: " + e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
