import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Employee employee = (Employee) obj;
        return employeeID == employee.employeeID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeID);
    }

    public static void main(String[] args) {
        Set<Employee> employees = new HashSet<>();
        employees.add(new Employee(101, "Anne", "IT"));
        employees.add(new Employee(103, "Doe", "HR"));
        employees.add(new Employee(102, "Brian", "Finance"));
        employees.add(new Employee(104, "Rebecca", "Finance"));
        employees.add(new Employee(101, "Anne Duplicate", "IT")); // Duplicate based on employeeID

        for (Employee emp : employees) {
            System.out.println(emp);
        }
    }
}
