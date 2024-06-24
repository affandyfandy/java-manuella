import java.util.HashSet;
import java.util.Set;

public class Lab3 {

    private static final Set<Character> vowels = new HashSet<>(Set.of('a', 'e', 'i', 'o', 'u'));

    public static void checkForVowels(String input) throws CustomException {
        boolean foundVowel = false;
        
        for (char ch : input.toLowerCase().toCharArray()) {
            if (vowels.contains(ch)) {
                foundVowel = true;
                break;
            }
        }

        if (!foundVowel) {
            throw new CustomException("Input string does not contain any vowels.");
        }
    }

    public static void main(String[] args) {
        String input1 = "Hello";
        String input2 = "Qdhfkgjwkgj";

        try {
            checkForVowels(input1);
            System.out.println(input1 + " contains vowels.");
        } catch (CustomException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            checkForVowels(input2);
            System.out.println(input2 + " contains vowels.");
        } catch (CustomException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
