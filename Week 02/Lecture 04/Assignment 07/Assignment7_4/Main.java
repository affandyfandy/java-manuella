import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
            List<Person> people = new ArrayList<>();
            people.add(new Person("Alice", 30, 50000));
            people.add(new Person("Bob", 25, 60000));
            people.add(new Person("Charlie", 35, 55000));
    
            try {
                // Convert list to map with "name" as the key
                Map<Object, Person> nameMap = ObjectUtils.convertListToMap(people, "name");
                System.out.println("Map with name as key:");
                for (Map.Entry<Object, Person> entry : nameMap.entrySet()) {
                    System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
                }
    
                // Convert list to map with "age" as the key
                Map<Object, Person> ageMap = ObjectUtils.convertListToMap(people, "age");
                System.out.println("\nMap with age as key:");
                for (Map.Entry<Object, Person> entry : ageMap.entrySet()) {
                    System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
                }
    
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
}
