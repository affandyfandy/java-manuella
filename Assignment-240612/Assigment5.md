## Assignment 5
Implementation of thread-safe singleton

A thread-safe singleton in Java ensures that only one instance of a class is created across multiple threads, typically achieved using lazy initialization with double-checked locking or by initializing the instance statically. Synchronization mechanisms like synchronized methods or volatile variables are employed to manage concurrent access during instance creation, preventing race conditions where multiple threads might attempt to create the singleton simultaneously. This guarantees consistent and reliable access to the singleton instance throughout the application.

Method 1
```java
public class Singleton {
    private static Singleton instance;

    private Singleton() {}

    public synchronized Singleton getInstance() {
        if (instance == null) {
            if (instance == null) {
                instance = new Singleton();
            }
        }
        return instance;
    }
}
```
- The constructor is made private so there will be no instantiation outside the class.
- The method getInstance will synchronized first before return the instance to ensure only one thread at one time can access this method.
- The method getInstance will return the instance of the Singleton. It will be initialized only if the getInstance called by checking if the instance is null (still not initialized yet) or not.


Method 2
```java
public class Singleton {
    private static volatile Singleton instance;

    private Singleton() {}

    public Singleton getInstance() {
        if (instance == null) {
            synchronized(this){
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
- The method getInstance will return the instance of the Singleton. It will be initialized only if the getInstance called by checking if the instance is null (still not initialized yet) or not. The initialization synchronized the thread after checking if the instance is null and check again to increase performance.