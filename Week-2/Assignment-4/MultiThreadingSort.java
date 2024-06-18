import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class MultiThreadingSort {

    public static void main(String[] args) {
        int[] arr1 = new int[100];
        int[] arr2 = new int[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = (int) (Math.random() * arr1.length);
        }
        System.out.println("Array before sorted: " + Arrays.toString(arr1));
        System.arraycopy(arr1, 0, arr2, 0, arr1.length);
        int threads = 4;

        // Multithreading
        MultiThreadingSort sorter = new MultiThreadingSort();
        CountDownLatch latch = new CountDownLatch(1);

        sorter.multiThreadSort(threads, arr1, 0, arr1.length, latch);
        
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Arrays.sort
        Arrays.sort(arr2);

        System.out.println("Array after sorted: " + Arrays.toString(arr1));

        if (Arrays.equals(arr1, arr2)) {
            System.out.println("Array after sorted and Arrays.sort is the same.");
        } else {
            System.out.println("Array after sorted and Arrays.sort is not the same.");
        }
    }

    public void multiThreadSort(int threads, int[] arr, int start, int stop, CountDownLatch latch) {
        if (start < stop - 1) {
            if (threads > 1) {
                int midpoint = quickSort(arr, start, stop);
                CountDownLatch newLatch = new CountDownLatch(2);

                Thread leftThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        multiThreadSort(threads - 1, arr, start, midpoint, newLatch);
                    }
                });

                Thread rightThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        multiThreadSort(threads - 1, arr, midpoint, stop, newLatch);
                    }
                });

                leftThread.start();
                rightThread.start();

                try {
                    newLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                Arrays.sort(arr, start, stop);
            }
        }

        latch.countDown();
    }

    public int quickSort(int[] arr, int start, int stop) {
        int pivot = arr[start];
        int left = start + 1;
        int right = stop - 1;

        while (left <= right) {
            while (left <= right && arr[left] <= pivot) {
                left++;
            }
            while (left <= right && arr[right] > pivot) {
                right--;
            }
            if (left < right) {
                swap(arr, left, right);
            }
        }
        swap(arr, start, right);
        return right;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
