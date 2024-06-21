import java.util.List;
import java.util.ArrayList;


public class Assignment7_2 {
    // Unbounded wildcard (?)
    // Can be used when we don't care about the type parameter, can represent any type
    public static void printList(List<?> list) {
        for (Object elem : list) {
            System.out.println(elem);
        }
    }

    // Bounded wildcard with an upper bound (? extends Type)
    // This wildcard can represent any type that is a subclass of Type
    public static double sumOfNumbers(List<? extends Number> list) {
        double sum = 0.0;
        for (Number num : list) {
            sum += num.doubleValue();
        }
        return sum;
    }

    // Bounded wildcard with a lower bound (? super Type)
    // This wildcard can represent any type that is a superclass of Type
    public static void addNumbers123(List<? super Integer> list) {
        list.add(1);
        list.add(2);
        list.add(3);
    }

    public static void main(String[] args) {
        // Unbounded Wildcard
        List<String> stringList = List.of("One", "Two", "Three");
        List<Integer> intList = List.of(1, 2, 3);

        System.out.println("Unbounded Wildcard:");
        printList(stringList);
        printList(intList);

        // Upper Bounded Wildcard
        List<Double> doubleList = List.of(1.1, 2.2, 3.3);

        System.out.println("\nUpper Bounded Wildcard:");
        System.out.println("Sum of integers: " + sumOfNumbers(intList));
        System.out.println("Sum of doubles: " + sumOfNumbers(doubleList));

        // Lower Bounded Wildcard
        List<Number> numberList = new ArrayList<>();

        System.out.println("\nLower Bounded Wildcard:");
        addNumbers123(numberList);
        printList(numberList);
    }
}
