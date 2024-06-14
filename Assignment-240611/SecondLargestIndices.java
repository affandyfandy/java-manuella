import java.util.ArrayList;
import java.util.List;

public class SecondLargestIndices {

    public static List<Integer> secondLargestIndices(int[] nums) {
        if (nums == null || nums.length < 2) {
            throw new IllegalArgumentException("Input array must have at least two elements");
        }

        int maxIndex = 0;
        int secondMaxIndex = -1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[maxIndex]) {
                secondMaxIndex = maxIndex;
                maxIndex = i;
            } else if (secondMaxIndex == -1 || nums[i] > nums[secondMaxIndex]) {
                if (nums[i] != nums[maxIndex]) {
                    secondMaxIndex = i;
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == nums[secondMaxIndex]) {
                result.add(i);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[] input = {1, 4, 3, -6, 5, 4};
        List<Integer> result = secondLargestIndices(input);
        System.out.println("Input: " + java.util.Arrays.toString(input));
        System.out.println("Indices of the second largest number: " + result);
    }
}
