import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Employee {
    private int employeeID;
    private String name;
    private String department;

    public Employee(int employeeID, String name, String department) {
        this.employeeID = employeeID;
        this.name = name;
        this.department = department;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeID=" + employeeID +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                '}';
    }

    public static void main(String[] args) {
        List<Employee> employees = List.of(
                new Employee(101, "Anne", "IT"),
                new Employee(103, "Doe", "HR"),
                new Employee(102, "Brian", "Finance"),
                new Employee(104, "Rebecca", "Finance")
        );

        Map<Integer, Employee> employeeMap = employees.stream()
                .sorted(Comparator.comparing(Employee::getEmployeeID))
                .collect(Collectors.toMap(Employee::getEmployeeID, emp -> emp));

        employeeMap.forEach((key, value) -> System.out.println(key + " => " + value));
    }
}