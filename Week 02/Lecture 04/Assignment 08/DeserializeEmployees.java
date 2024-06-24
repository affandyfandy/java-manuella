import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.List;

public class DeserializeEmployees {
    public static void main(String[] args) {
        try (FileInputStream fis = new FileInputStream("employees.ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            List<Employee> employees = (List<Employee>) ois.readObject();
            System.out.println("Employees have been deserialized from employees.ser");
            for (Employee emp : employees) {
                System.out.println(emp);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
