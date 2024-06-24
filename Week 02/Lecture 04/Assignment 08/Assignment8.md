## Assignment 8

#### 1. About serialVersionUID
serialVersionUID is a unique identifier for Serializable classes. It is used during the deserialization process to ensure that a loaded class corresponds exactly to a serialized object. If the class has been modified in a way that affects serialization (e.g., adding or removing fields), the serialVersionUID helps detect these changes and prevent compatibility issues.

```java
private static final long serialVersionUID = 1L;
```

If a class does not explicitly declare a serialVersionUID, the Java runtime will generate one automatically based on various aspects of the class, such as its name, implemented interfaces, and members. However, this automatic generation can lead to unexpected results if the class is modified.

Example of declaring explicit serialVersionID:
```java
import java.io.Serializable;

public class MyClass implements Serializable {
    private static final long serialVersionUID = 123456789L;

    private int id;
    private String name;

    // Getters and setters
}
```