import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Assignment6_1 {
    public static void main(String[] args) {
        List<String> fruit = Arrays.asList("Apple", "Mango", "Banana", "Grape", "Melon", "Pineapple", "Strawberry");

        fruit.parallelStream()
             .map(String::toUpperCase)
             .forEach(System.out::println);

        Stream<Integer> numStream = IntStream.rangeClosed(1, 50).boxed();

        List<Integer> evenNum = numStream.parallel()
                                            .filter(n -> n%2 == 0)
                                            .collect(Collectors.toList());
        System.out.println("Even Numbers: " + evenNum);
    }
}
