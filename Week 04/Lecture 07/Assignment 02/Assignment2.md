## Assignment 02

#### Comparing injections
1. Constructor Injection
Advantages:
- Immutability
Ensures that dependencies are set at the time of object creation and cannot be changed afterward, promoting immutability.
- Testability
Easier to test as all dependencies are provided upfront, enabling straightforward creation of test instances with mocks or stubs.

Disadvantages:
- Complexity
Can lead to complex constructors with many parameters if the class has many dependencies, reducing readability and maintainability.
- Verbosity:
May require additional boilerplate code, such as constructors, which can be verbose if many dependencies exist.

2. Field Injection
Advantages:
- Simplicity
Simplifies the code by directly injecting dependencies into fields, avoiding the need for constructors or setter methods.
- Readability
Reduces boilerplate code, making the class easier to read and maintain.
Disadvantages:
- Testability
Makes unit testing more difficult as dependencies are not easily visible or changeable. Reflection might be required to set up tests.
- Immutability
Fields can be changed after object creation, making it harder to maintain immutability.
- Hidden Dependencies
Dependencies are not explicitly visible, which can make the class harder to understand and maintain.

3. Setter Injection
Advantages:
- Optional Dependencies
Allows setting optional dependencies. Dependencies can be set or changed after object creation.
- Readability
Makes dependencies visible through setter methods, enhancing clarity and documentation.
- Flexibility
Provides flexibility in modifying dependencies at runtime or through configuration.

Disadvantages:
- Immutability
Makes it difficult to enforce immutability as dependencies can be changed after the object is created.
- Partially Initialized Objects
There is a risk of creating partially initialized objects if some setters are not called, leading to potential runtime errors.
- Testability
While better than field injection, it still requires additional setup in tests to call setters and inject dependencies.

#### 3. Circular dependency injection
Circular dependency injection occurs when two or more beans (or components) depend on each other, creating a cycle. In other words, Bean A depends on Bean B, and Bean B depends on Bean A. This situation can lead to issues in dependency resolution and bean creation within the Spring IoC container.
Example:
Class A depends on Class B
```java
public class A {
    private B b;

    @Autowired
    public A(B b) {
        this.b = b;
    }

    // Other methods
}
```
Class B depends on Class A
```java
public class B {
    private A a;

    @Autowired
    public B(A a) {
        this.a = a;
    }

    // Other methods
}
```
- Spring starts to create A and sees that A depends on B.
- It then starts to create B and sees that B depends on A.
- It goes back to creating A, and the cycle continues indefinitely.

To handle it, Spring provides mechanisms to handle circular dependencies, but the best approach is to design the application to avoid them in the first place. Some strategies to handle and avoid circular dependencies:
1. Setter Injection
Using setter injection instead of constructor injection can help break the cycle because Spring can create beans and then set their dependencies afterward.
```java
public class A {
    private B b;

    @Autowired
    public void setB(B b) {
        this.b = b;
    }
}

public class B {
    private A a;

    @Autowired
    public void setA(A a) {
        this.a = a;
    }
}
```
2. @Lazy Annotation
The @Lazy annotation can be used to defer the initialization of a bean until it is needed.
```java
public class A {
    private B b;

    @Autowired
    public A(@Lazy B b) {
        this.b = b;
    }
}

public class B {
    private A a;

    @Autowired
    public B(@Lazy A a) {
        this.a = a;
    }
}
```
3. Redesigning Dependencies
Refactor the design to remove the circular dependency. For instance, introduce a third bean that both A and B depend on, breaking the cycle.
```java
public class A {
    private CommonDependency commonDependency;

    @Autowired
    public A(CommonDependency commonDependency) {
        this.commonDependency = commonDependency;
    }
}

public class B {
    private CommonDependency commonDependency;

    @Autowired
    public B(CommonDependency commonDependency) {
        this.commonDependency = commonDependency;
    }
}

public class CommonDependency {
    // Shared functionality
}
```
4. ApplicationContextAware Interface
Implementing ApplicationContextAware and manually fetching the dependencies from the application context can be used as a last resort.
```java
public class A implements ApplicationContextAware {
    private B b;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.b = applicationContext.getBean(B.class);
    }
}

public class B implements ApplicationContextAware {
    private A a;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.a = applicationContext.getBean(A.class);
    }
}
```

#### Annotations
1. @Configuration
Indicates that the class declares one or more @Bean methods and can be processed by the Spring container to generate bean definitions and service requests.
```java
@Configuration
public class AppConfig {
    @Bean
    public MyService myService() {
        return new MyServiceImpl();
    }
}
```

2. @Bean
Indicates that a method produces a bean to be managed by the Spring container.
```java
@Bean
public MyService myService() {
    return new MyServiceImpl();
}
```

3. @ComponentScan
Configures component scanning directives for use with @Configuration classes.
```java
@Configuration
@ComponentScan(basePackages = "com.example")
public class AppConfig {
}
```

4. @Component
Indicates that an annotated class is a component. Such classes are considered as candidates for auto-detection when using annotation-based configuration and classpath scanning.
```java
@Component
public class MyComponent {
}
```

5. @Service
Specialization of @Component for service layer classes.
```java
@Service
public class MyService {
}
```

6. @Repository
Specialization of @Component for DAO (Data Access Object) classes.
```java
@Repository
public class MyRepository {
}
```

7. @Autowired
Marks a constructor, field, setter method, or config method as to be autowired by Spring's dependency injection facilities.
```java
@Autowired
private MyService myService;
```

8. @Scope
Configures the scope of a bean (e.g., singleton, prototype).
```java
@Bean
@Scope("prototype")
public MyService myService() {
    return new MyServiceImpl();
}
```

9. @Qualifier
Specifies which bean to inject when multiple candidates are present.
```java
@Autowired
@Qualifier("specificService")
private MyService myService;
```

10. @PropertySource, @Value
@PropertySource is used to specify the property file to be loaded, and @Value is used to inject property values into beans.
```java
@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {
    @Value("${my.property}")
    private String myProperty;
}
```

11. @PreDestroy, @PostConstruct
@PostConstruct is used on a method that needs to be executed after dependency injection is done, and @PreDestroy is used on methods to be executed before the bean is destroyed.
```java
@Component
public class MyComponent {
    @PostConstruct
    public void init() {
        // Initialization code
    }

    @PreDestroy
    public void destroy() {
        // Cleanup code
    }
}
```

Example in project:
```java
// AppConfig.java
package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan(basePackages = "com.example")
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean
    @Scope("prototype")
    public MyService myService() {
        return new MyService();
    }
}

// MyService.java
package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyService {

    @Autowired
    private MyRepository myRepository;

    public void performService() {
        System.out.println("Service is being performed.");
        myRepository.saveData("Example Data");
        String data = myRepository.fetchData(1);
        System.out.println("Fetched Data: " + data);
    }
}

// MyRepository.java
package com.example;

import org.springframework.stereotype.Repository;

@Repository
public class MyRepository {

    // This is a simulated database using an in-memory store
    private Map<Integer, String> database = new HashMap<>();

    public void saveData(String data) {
        int id = database.size() + 1;
        database.put(id, data);
        System.out.println("Data saved with ID: " + id);
    }

    public String fetchData(int id) {
        return database.get(id);
    }
}

// MyComponent.java
package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class MyComponent {

    @Autowired
    @Qualifier("myService")
    private MyService myService;

    @Value("${my.property}")
    private String myProperty;

    @PostConstruct
    public void init() {
        System.out.println("MyComponent initialized with property: " + myProperty);
        myService.performService();
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("MyComponent cleanup");
    }
}

// SimpleDemoApplication.java
package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimpleDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SimpleDemoApplication.class, args);
    }
}
```