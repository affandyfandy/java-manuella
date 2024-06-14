import java.util.HashMap;

public class Assignment4 {
    public static void main(String[] args) {
        int[] array = {1, 2, 3, -6, 5, 4, 0};
        int[] result = getZeroSumSubarrayIndices(array);
        if (result != null) {
            System.out.println("Output: [" + result[0] + ", " + result[1] + "]");
        } else {
            System.out.println("No subarray with zero sum found.");
        }
    }

    public static int[] getZeroSumSubarrayIndices(int[] array) {
        HashMap<Integer, Integer> sumMap = new HashMap<>();
        int sum = 0;

        for (int i = 0; i < array.length; i++) {
            sum += array[i];

            if (sum == 0) {
                return new int[]{0, i};
            }

            if (sumMap.containsKey(sum)) {
                return new int[]{sumMap.get(sum) + 1, i};
            }

            sumMap.put(sum, i);
        }

        return null;
    }
}
