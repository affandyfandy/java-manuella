## Assignment 03

#### Inject prototype into singleton
1. Using @Lookup Method Injection
The @Lookup annotation tells Spring to override a method and return a new instance of the specified bean when called.
```java
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PrototypeBean {
    // Prototype bean logic
}

@Component
public class SingletonBean {
    // Other singleton bean logic

    @Lookup
    public PrototypeBean getPrototypeBean() {
        // Spring will override this method
        return null;
    }

    public void usePrototypeBean() {
        PrototypeBean prototypeBean = getPrototypeBean();
        // Use prototypeBean
    }
}
```
2. Using ObjectFactory or Provider
You can use Spring's ObjectFactory or javax.inject.Provider to lazily retrieve a new instance of the prototype bean whenever needed.
Using ObjectFactory:
```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Scope;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PrototypeBean {
    // Prototype bean logic
}

@Component
public class SingletonBean {
    private final ObjectFactory<PrototypeBean> prototypeBeanFactory;

    @Autowired
    public SingletonBean(ObjectFactory<PrototypeBean> prototypeBeanFactory) {
        this.prototypeBeanFactory = prototypeBeanFactory;
    }

    public void usePrototypeBean() {
        PrototypeBean prototypeBean = prototypeBeanFactory.getObject();
        // Use prototypeBean
    }
}
```
Using Provider:
```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Provider;

@Component
@Scope("prototype")
public class PrototypeBean {
    // Prototype bean logic
}

@Component
public class SingletonBean {
    private final Provider<PrototypeBean> prototypeBeanProvider;

    @Autowired
    public SingletonBean(Provider<PrototypeBean> prototypeBeanProvider) {
        this.prototypeBeanProvider = prototypeBeanProvider;
    }

    public void usePrototypeBean() {
        PrototypeBean prototypeBean = prototypeBeanProvider.get();
        // Use prototypeBean
    }
}
```
3. Using ApplicationContext
You can also use the ApplicationContext to manually retrieve the prototype bean.
```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PrototypeBean {
    // Prototype bean logic
}

@Component
public class SingletonBean {
    private final ApplicationContext applicationContext;

    @Autowired
    public SingletonBean(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void usePrototypeBean() {
        PrototypeBean prototypeBean = applicationContext.getBean(PrototypeBean.class);
        // Use prototypeBean
    }
}
```

#### Difference between BeanFactory and ApplicationContext
##### BeanFactory
1. Lazy Initialization
Beans are created lazily, i.e., they are instantiated only when they are requested for the first time. This can lead to faster startup times if not all beans are required at startup.
2. Basic Dependency Injection
Provides basic dependency injection capabilities and management of bean lifecycle.
3. Minimalistic
Contains only the essential features necessary for dependency injection. Does not support advanced features such as event propagation, declarative mechanisms to create a bean, and certain types of aspect-oriented programming (AOP).
4. No Built-in Support for Annotation-based Configuration
Lacks direct support for annotation-based configurations, which are often used in modern Spring applications.
```java
BeanFactory factory = new XmlBeanFactory(new ClassPathResource("beans.xml"));
MyBean myBean = (MyBean) factory.getBean("myBean");
```

##### Application Context
Eager Initialization:

By default, all singleton beans are pre-instantiated when the container is started. This can lead to longer startup times but ensures that any configuration or dependency issues are caught early.
1. Advanced Features
Provides advanced features such as event propagation, declarative mechanisms to create a bean, and integration with Spring's AOP capabilities.
2. Internationalization
Supports internationalization (i18n) for resolving text messages.
3. Automatic Bean Post-Processing
Supports automatic registration of BeanPostProcessor and BeanFactoryPostProcessor beans, which can be used to modify the bean's configuration metadata and the beans themselves before and after initialization.
4. Built-in Support for Annotation-based Configuration
Directly supports annotation-based configuration using annotations like @Component, @Service, @Repository, @Autowired, etc.
5. Resource Handling
Provides a standard way to load file resources.
6. Event Handling
Supports event publication. Application events can be used to communicate among beans.
``` java
ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
MyBean myBean = (MyBean) context.getBean("myBean");
```