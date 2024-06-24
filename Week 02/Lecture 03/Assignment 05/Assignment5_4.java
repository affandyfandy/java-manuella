import java.util.HashMap;

public class Assignment5_4 {
    public static void main(String[] args) {
        HashMap<String, Integer> originalMap = new HashMap<>();
        originalMap.put("A", 1);
        originalMap.put("B", 2);
        originalMap.put("C", 3);

        HashMap<String, Integer> shallowCopy = (HashMap<String, Integer>) originalMap.clone();

        originalMap.put("D", 4);

        System.out.println("Original HashMap: " + originalMap);
        System.out.println("Shallow Copy HashMap: " + shallowCopy);
    }
}
