## Assignment 6

#### 1. Parallel stream
Parallel streams is used to make multiple threads to speed up operations on large collections. Parallel streams can be useful when working with large data sets and performing operations that can be divided into independent tasks.

Usage:
- Large Data Sets: When working with large collections of data that need to be processed, parallel streams can significantly reduce the processing time.

- Independent Tasks: The tasks being performed on the elements of the collection should be independent and not depend on the state of other elements.

- Stateless Operations: Operations performed on each element should not change the state that might affect other elements. Avoid using shared mutable state.

- Performance Testing: Always measure the performance improvements as sometimes the overhead of managing parallelism might not lead to a performance gain, especially for small datasets or simple operations.

Key points:
- Thread-Safety: Have to make sure that the operations performed are thread-safe. Using shared state can lead to concurrency issues.
- Overhead: Parallelism adds overhead. For small data sets, the cost of parallelization might outweigh the benefits.
- ForkJoinPool: Parallel streams use the common ForkJoinPool by default. This might interfere with other parallel tasks in the application.
- Order: If the order of processing is important, it is needed to be cautious as parallel streams might not preserve the order unless specifically instructed.

Example printing list
```java
import java.util.Arrays;
import java.util.List;

public class ParallelStreamExample {
    public static void main(String[] args) {
        List<String> fruit = Arrays.asList("Apple", "Mango", "Banana", "Grape", "Melon", "Pineapple", "Strawberry");

        fruit.parallelStream()
             .map(String::toUpperCase)
             .forEach(System.out::println);
    }
}

```
Example filtering number to new list
```java
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParallelStreamFilter {
    public static void main(String[] args) {
        Stream<Integer> numStream = IntStream.rangeClosed(1, 50).boxed();

        List<Integer> evenNum = numStream.parallelStream()
                                            .filter(n -> n%2 == 0)
                                            .collect(Collectors.toList());
        System.out.println("Even Numbers: " + evenNum);
    }
}
```