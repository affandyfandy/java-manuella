import java.util.List;

public class Assignment6_4 {
    public static void main(String[] args) {
        List<String> strings = List.of("Red", "Green", "Blue", "Pink", "Brown");
        String letterToCount = "G";

        long count = strings.stream()
                            .filter(s -> s.startsWith(letterToCount))
                            .count();

        System.out.println("Number of strings starting with '" + letterToCount + "': " + count);
    }
}
