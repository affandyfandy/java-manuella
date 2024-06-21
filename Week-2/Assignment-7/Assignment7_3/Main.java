import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        List<Person> people = new ArrayList<>();
        people.add(new Person("Alice", 30, 50000));
        people.add(new Person("Bob", 25, 60000));
        people.add(new Person("Charlie", 35, 55000));

        try {
            System.out.println("Sorting by age:");
            ObjectUtils.sortByField(people, "age");
            for (Person person : people) {
                System.out.println(person);
            }

            System.out.println("\nPerson with max salary:");
            Person maxSalaryPerson = ObjectUtils.findMaxByField(people, "salary");
            System.out.println(maxSalaryPerson);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
