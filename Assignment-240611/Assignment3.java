public class Assignment3 {
    public static void main(String[] args) {
        int[] array = {1, 4, 3, -6, 5, 4};
        int[] result = getSecondLargestIndex(array);
        System.out.println("Output: [" + result[0] + ", " + result[1] + "]");
    }

    public static int[] getSecondLargestIndex(int[] array) {
        if (array == null || array.length < 2) {
            throw new IllegalArgumentException("Array must contain at least two elements.");
        }

        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;
        int largestIndex = -1;
        int secondLargestIndex = -1;

        for (int i = 0; i < array.length; i++) {
            if (array[i] > largest) {
                secondLargest = largest;
                secondLargestIndex = largestIndex;
                largest = array[i];
                largestIndex = i;
            } else if (array[i] > secondLargest && array[i] != largest) {
                secondLargest = array[i];
                secondLargestIndex = i;
            }
        }

        return new int[]{secondLargestIndex};
    }
}