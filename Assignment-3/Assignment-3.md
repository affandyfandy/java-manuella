## Assignment 3

#### 1. Try-with-resource
Try-with-resources statement is used to automatically open and close resources that are opened within the try block. This feature was introduced to simplify the handling of resources such as streams, connections, and other types that implement the AutoCloseable or Closeable interfaces.

```java
Copy code
try (ResourceType1 resource1 = new ResourceType1(); ResourceType2 resource2 = new ResourceType2()) {
    // Use the resources
} catch (ExceptionType ex) {
    // Exception handling
}
```

- Resource Initialization: Resources that need to be closed at the end of the block are declared within parentheses after the try keyword. These resources must implement the AutoCloseable or Closeable interface.

- Automatic Closing: After the try block completes (either normally or because of an exception), Java automatically closes the declared resources in the reverse order of their declaration. This closure happens before any catch or finally block is executed.

- Exception Handling: If exceptions occur within the try block, and if exceptions are also thrown during the closing of resources, the latter exceptions are suppressed. These suppressed exceptions can be retrieved using the getSuppressed() method of the thrown exception.

```java
import java.io.*;

public class TryWithResourcesExample {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("example.txt"))) {
            String line = reader.readLine();
            System.out.println(line);
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
    }
}

```

Benefit:
- Conciseness: try-with-resources eliminates the need for explicit finally blocks to close resources.
- Safety: It ensures that resources are always closed properly, even if an exception is thrown during processing.
- Readability: By explicitly declaring and initializing resources in one place (within the try block), the code becomes more readable and maintainable.

#### 2. Throw vs throws
##### throw
The throw keyword is used to explicitly throw an exception. It is used within a method to indicate that an exceptional condition has occurred, which disrupts the normal flow of the program. When throw is used, the exception object is specified.

```java
public class MyException extends Exception {
    public MyException(String message) {
        super(message);
    }
}

public class ThrowExample {
    public static void validateScore(int score) throws MyException {
        if (score < 0) {
            throw new MyException("Score is not valid");
        } else {
            System.out.println("Score is valid.");
        }
    }

    public static void main(String[] args) {
        try {
            validateScore(-2);
        } catch (MyException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
    }
}
```

##### throws
The throws clause is used in method declarations to indicate that the method may throw one or more types of exceptions. It specifies the exceptions that the method can throw but does not handle them within the method itself, rather, it indicates that the caller of the method should handle those exceptions.

```java
import java.io.*;

public class ThrowsExample {
    public static void readFile(String fileName) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    }

    public static void main(String[] args) {
        try {
            readFile("nonexistent.txt");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}

```