## Assignment 5
Implementation of thread-safe singleton

```java
public class Singleton {
    private static volatile Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

- The constructor is made private so there will be no instantiation outside the class.
- The instance have volatile keyword so all threads can see the change of Singleton instance.
- The method getInstance will return the instance of the Singleton. It will be initialized only if the getInstance called by checking if the instance is null (still not initialized yet) or not. The initialization also synchronized all threads first using synchronized keyword so all thread have the same information about the instance.