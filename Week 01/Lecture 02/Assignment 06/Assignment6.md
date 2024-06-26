## Assignment 6

Stack
- The stack is used for static memory allocation.
- It stores primitive values (e.g., int, char) and references to objects in heap memory.
- Memory allocation and deallocation on the stack follow a - Last In First Out (LIFO) structure.
- Stack memory is typically faster to access and manage.

Heap
- The heap is used for dynamic memory allocation.
- It stores objects created using the new keyword.
- Objects in heap memory persist until they are explicitly removed by the garbage collector.
- Heap memory is larger than stack memory but can be slower to access.

Update MyClass
```java
public class MyClass {
    public String name;
    public int value;
}
```

Code 1
```java
public class Main {
    public static void main(String[] args) {
        MyClass obj = new MyClass();
        obj.value = 5;
        obj.name = "Original";
        modifyObject(obj);
        System.out.println(obj.name); // Outputs: Modified
        System.out.println(obj.value); // Outputs: 10
    }

    public static void modifyObject(MyClass x) {
        x.name = "Modified";
        x.value = 10;
    }
}
```

```java
MyClass obj = new MyClass();
```
- In stack, method main is added and obj is created and stored on the stack.
- obj points to a new MyClass object on the heap, but the value and name is still null.

```java
obj.value = 5;
obj.name = "Original";
```
- In heap, the fields value and name of the MyClass instance are updated to 5 and "Original".

```java
modifyObject(obj);
```
- In stack, modifyObject method is added, a new reference x is created, pointing to the same MyClass object as obj.

```java
public static void modifyObject(MyClass x) {
    x.name = "Modified";
    x.value = 10;
}
```
- In heap, the fields of obj are modified, after modifyObject finish, the modifyObject popped.

```java
System.out.println(obj.name); // Outputs: Modified
System.out.println(obj.value); // Outputs: 10
```
- name and value will be printed and the main stack will be popped.

Code 2
```java
public class Main {
    public static void main(String[] args) {
        MyClass obj = new MyClass();
        obj.value = 5;
        obj.name = "Original";
        changeReference(obj);
        System.out.println(obj.value); // Outputs: 5
        System.out.println(obj.name); // Outputs: Original
    }

    public static void changeReference(MyClass x) {
        x = new MyClass();
        x.value = 10;
        x.name = "Modified";
    }
}
```
```java
MyClass obj = new MyClass();
```
- In stack, method main is added and obj is created and stored on the stack.
- obj points to a new MyClass object on the heap, but the value and name is still null.

```java
obj.value = 5;
obj.name = "Original";
```
- In heap, the fields value and name of the MyClass instance are updated to 5 and "Original".

```java
changeReference(obj);
```
- In stack, changeReference method is added, a new reference x is created, pointing to the same MyClass object as obj.

```java
public static void changeReference(MyClass x) {
    x = new MyClass();
    x.name = "Modified";
    x.value = 10;
}
```
- In stack, the x will be reassigned to point to a new MyClass object on the heap and the new object will have different name and value. After changeReference finish, the changereference popped.

```java
System.out.println(obj.value); // Outputs 5
System.out.println(obj.name); // Outputs: Original
```
- In stack, obj is still references the first MyClass object. In heap, the original MyClass object remains unchanged, while the new object created inside changeReference is discarded after the method returns.