import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Person> people = new ArrayList<>();
        people.add(new Person("Alice", 30, 50000));
        people.add(new Person("Bob", 25, 60000));
        people.add(new Person("Charlie", 35, 55000));
        people.add(new Person("David", 28, 70000));
        people.add(new Person("Eve", 22, 45000));

        int pageSize = 2;
        int pageNumber = 1;

        PagedData<Person> pagedData = new PagedData<>(people, pageSize, pageNumber);
        
        System.out.println("Page 1:");
        System.out.println(pagedData);
        
        pageNumber = 2;
        pagedData = new PagedData<>(people, pageSize, pageNumber);

        System.out.println("\nPage 2:");
        System.out.println(pagedData);

        pageNumber = 3;
        pagedData = new PagedData<>(people, pageSize, pageNumber);

        System.out.println("\nPage 3:");
        System.out.println(pagedData);
    }
}
