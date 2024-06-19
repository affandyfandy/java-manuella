import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Assignment6_2 {
    public static void main(String[] args) {
        List<String> fruit = Arrays.asList("Apple", "Mango", "Banana", "Grape", "Apple", "Grape", "Grape");

        List<String> noDuplicateFruit = fruit.stream()
                                            .distinct()
                                            .collect(Collectors.toList());
        
        System.out.println(noDuplicateFruit);
    }
}