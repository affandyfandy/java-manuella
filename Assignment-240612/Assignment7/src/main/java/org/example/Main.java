package org.example;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AppManager manager = AppManager.getInstance();
    public static void printMenu(String[] options){
        for (String option : options){
            System.out.println(option);
        }
    }

    public static List<Employee> filter(){
        System.out.print("Enter id to filter (or leave blank): ");
        String idFilter= scanner.nextLine();
        System.out.print("Enter name to filter (or leave blank): ");
        String nameFilter = scanner.nextLine();
        System.out.print("Enter year of birth to filter (or leave blank): ");
        String yearInp = scanner.nextLine();
        Integer yearFilter = yearInp.isEmpty() ? null : Integer.parseInt(yearInp);
        System.out.print("Enter department to filter (or leave blank): ");
        String departmentFilter = scanner.nextLine();

        List<Employee> filteredEmployees = manager.filterEmployee(
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

    public static void main(String[] args) {
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
                        manager.importData(rootPath + "\\" + path);
                        System.out.println("Data imported.");
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

                    manager.addEmployee(id, name, dob, address, department);
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
                        manager.exportToCsv(rootPath + "\\" + exportPath, employeeList);
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