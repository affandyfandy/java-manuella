import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SerializeEmployees {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "John Doe", 50000));
        employees.add(new Employee(2, "Jane Smith", 60000));
        employees.add(new Employee(3, "Mike Johnson", 70000));

        try (FileOutputStream fos = new FileOutputStream("employees.ser");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(employees);
            System.out.println("Employees have been serialized to employees.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
