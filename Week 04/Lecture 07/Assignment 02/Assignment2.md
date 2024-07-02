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
1. @Configuration: Defines a configuration class.
Explanation: This annotation indicates that a class declares one or more @Bean methods and can be processed by the Spring container to generate bean definitions and service requests at runtime.
```java
@Configuration
public class AppConfig {
    @Bean
    public MyService myService() {
        return new MyServiceImpl();
    }
}
```
2. @Bean: Declares a bean to be managed by Spring.
Explanation: This annotation is used to declare a bean, which will be managed by the Spring container. It is typically used within a @Configuration class.
```java
@Configuration
public class AppConfig {
    @Bean
    public MyService myService() {
        return new MyServiceImpl();
    }
}
```
3. @ComponentScan: Configures package scanning.
Explanation: This annotation tells Spring to scan the specified package(s) for components (e.g., @Component, @Service, @Repository, @Controller).
```java
@Configuration
@ComponentScan(basePackages = "com.example")
public class AppConfig {
}
```
4. @Component: Marks a class as a Spring component.
Explanation: This annotation is used to indicate that a class is a Spring component. Such classes are considered as candidates for auto-detection when using annotation-based configuration and classpath scanning.
```java
@Component
public class MyComponent {
    // Component logic
}
```
5. @Service: Marks a service-layer class.
Explanation: This annotation is a specialization of @Component. It indicates that an annotated class is a service, which is a concept from domain-driven design (DDD).
```java
@Service
public class MyService {
    // Service logic
}
```
6. 
Sure! Let's go through each of these Spring annotations with examples and explanations.

1. @Configuration: Defines a configuration class.
Explanation: This annotation indicates that a class declares one or more @Bean methods and can be processed by the Spring container to generate bean definitions and service requests at runtime.

Example:
```java
@Configuration
public class AppConfig {
    @Bean
    public MyService myService() {
        return new MyServiceImpl();
    }
}
```
2. @Bean: Declares a bean to be managed by Spring.
Explanation: This annotation is used to declare a bean, which will be managed by the Spring container. It is typically used within a @Configuration class.

Example:
```java
@Configuration
public class AppConfig {
    @Bean
    public MyService myService() {
        return new MyServiceImpl();
    }
}
```
3. @ComponentScan: Configures package scanning.
Explanation: This annotation tells Spring to scan the specified package(s) for components (e.g., @Component, @Service, @Repository, @Controller).

Example:
```java
@Configuration
@ComponentScan(basePackages = "com.example")
public class AppConfig {
}
```
4. @Component: Marks a class as a Spring component.
Explanation: This annotation is used to indicate that a class is a Spring component. Such classes are considered as candidates for auto-detection when using annotation-based configuration and classpath scanning.

Example:

```java
@Component
public class MyComponent {
    // Component logic
}
```
5. @Service: Marks a service-layer class.
Explanation: This annotation is a specialization of @Component. It indicates that an annotated class is a service, which is a concept from domain-driven design (DDD).

Example:
```java
@Service
public class MyService {
    // Service logic
}
```

6. @Autowired: Injects dependencies.
Explanation: This annotation is used for automatic dependency injection. It can be applied to constructors, methods, and fields.
```java
@Component
public class MyComponent {
    private final MyService myService;

    @Autowired
    public MyComponent(MyService myService) {
        this.myService = myService;
    }
}
```