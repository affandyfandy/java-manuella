## Assignment 5
Implementation of thread-safe singleton

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