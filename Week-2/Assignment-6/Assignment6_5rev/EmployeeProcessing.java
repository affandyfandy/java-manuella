import java.util.List;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Map;

public class EmployeeProcessing {
    public List<Employee> sortedName(List<Employee> employees){
        return employees.stream()
                        .sorted(Comparator.comparing(Employee::getName))
                        .collect(Collectors.toList());
    }

    public Employee maxSalary(List<Employee> employees){
        Optional<Employee> employeeWithMaxSalary = employees.stream()
                .max(Comparator.comparingInt(Employee::getSalary));
        return employeeWithMaxSalary.get();
    }

    public boolean nameContain(String x, List<Employee> employees){
        return employees.stream()
                        .anyMatch(employee -> employee.getName().toLowerCase().contains(x.toLowerCase()));
    }

    public Map<Integer, Employee> toMapById(List<Employee> employees) {
        return employees.stream()
                        .collect(Collectors.toMap(Employee::getId, employee -> employee));
    }

    public static void main(String[] args){
        EmployeeProcessing employeeProcessing = new EmployeeProcessing();
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Brian", 20, 5000));
        employees.add(new Employee(2, "Doe", 22, 3000));
        employees.add(new Employee(3, "Angel", 21, 4000));
        employees.add(new Employee(4, "Vera", 20, 6000));

        // SORTED BY NAME
        List<Employee> sortedByName = employeeProcessing.sortedName(employees);
        System.out.println("Employees sorted by name:");
        sortedByName.forEach(student -> System.out.println(student.getName()));

        // MAX AGE
        Employee maxSalaryEmployee = employeeProcessing.maxSalary(employees);
        System.out.println("Employee with max salary: " + maxSalaryEmployee.getName() + ", salary: " + maxSalaryEmployee.getSalary());

        // NAME CONTAIN X
        String keyword = "D";
        boolean anyMatch = employeeProcessing.nameContain(keyword, employees);

        if (anyMatch) {
            System.out.println("There is an employee whose name contains the keyword '" + keyword + "'.");
        } else {
            System.out.println("No employee names contain the keyword '" + keyword + "'.");
        }

        // CONVERT TO MAP
        Map<Integer, Employee> employeeMap = employeeProcessing.toMapById(employees);
        System.out.println("Employees mapped by ID:");
        employeeMap.forEach((id, employee) -> System.out.println("ID: " + id + ", Name: " + employee.getName()));
    }
}