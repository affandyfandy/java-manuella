## Assignment 5

#### 1.a ArrayList vs LinkedList

##### ArrayList
ArrayList uses a dynamic array to store the elements. Manipulation in ArrayList is slow because it used array, when any element is removed, the other element must be shifted. ArrayList can act as List only. The default capacity is 10, but it is resizable.
Usage:
- ArrayList is better for storing and accessing data.
- Suitable when frequent access to elements by index is required (get and set operations), and when adding or removing elements at the end of the list (add and remove operations) is common.

```java
List<String> fruit = new ArrayList<String>();
  al.add("Apple");
  al.add("Mango");    
  al.add("Banana");    
  al.add("Melon");   
```

##### LinkedList
LinkedList uses a doubly linked list to store the elements. Manipulation with LinkedList is faster because it uses a doubly linked list, so no shifting is required when any element is removed. LinkedList class can act as a list and queue.
Usage:
- LinkedList is better for manipulating data.
- Suitable when frequent insertion or deletion of elements at arbitrary positions within the list is required, as it provides efficient add and remove operations for such scenarios.

#### 1.b HashSet vs TreeSet vs LinkedHashSet

##### HashSet
1. HashSet does not maintain any order of elements. Elements are stored in a hash table.
2. Allow null element.
3. Provides average O(1) time complexity for add, remove, and contains operations. Performance can degrade if hash collisions occur frequently.
4. Not synchronized by default, need to use Collections.synchronizedSet().
5. Fail-fast iterators (throw ConcurrentModificationException if collection is modified structurally during iteration).

Usage:
- When order of elements is not important, and we need constant-time performance for add, remove, and contains operations.

##### Treeset
1. Maintains elements in sorted order (natural ordering or by comparator provided at construction time).
2. Allow null element.
3. Provides O(log n) time complexity for add, remove, and contains operations due to the balanced tree structure.
4. Not synchronized by default, need to use Collections.synchronizedSet().
5. Fail-fast iterators (throw ConcurrentModificationException if collection is modified structurally during iteration).

Usage:
- When we need elements to be sorted in natural order or a custom order defined by a comparator.
- When we need operations like floor, ceiling, lower, and higher (available in NavigableSet interface).

##### LinkedHashSet
1. Maintains insertion order of elements. Combines a hash table with a linked list running through all entries, providing predictable iteration order (insertion-order).
2. Does not allow null elements. Throws NullPointerException if null is attempted to be inserted.
3. Provides average O(1) time complexity for add, remove, and contains operations, with additional cost of maintaining insertion order.
4. Not synchronized by default, need to use Collections.synchronizedSet().
5. Fail-fast iterators.

Usage:
- When we need predictable iteration order with fast access times.

#### 4. Shallow copy of a HashMap instance
Shallow copy creates a duplicate of the data structure (object), but it does not duplicate the actual objects themselves. Instead, it copies references to the same objects. If the objects contain references to other objects, the shallow copy will still refer to the same referenced objects. It's useful for quickly duplicating object structures without deep copying all nested objects, but careful consideration is needed to manage shared state effectively.

#### 6. CopyOnWriteArrayList
CopyOnWriteArrayList is implementation of List in Java that provides thread-safe iteration and does not throw ConcurrentModificationException even when the list is modified during iteration. This is achieved by making a fresh copy of the underlying array whenever the list is modified (add, set, remove operations).

#### 7. ConcurrencyHashMap
ConcurrentHashMap in Java is a thread-safe implementation of the Map interface. It uses using a combination of partitioned locks and fine-grained locking mechanisms to achieve thread safety and efficient concurrency.

#### 8. equals() and hashcode()
##### equals()
The equals() method is used to compare the equality of two objects. It is defined in the Object class and can be overridden by any class to provide custom equality logic. If we override equals(), we must also override hashCode().

The criteria of equals method that must be followed is:
- reflexive: an object must equal itself
- symmetric: x.equals(y) must return the same result as y.equals(x)
- transitive: if x.equals(y) and y.equals(z), then also x.equals(z)
- consistent: the value of .equals() should change only if a property that is contained in .equals() changes (no randomness allowed)

The default implementation of equals only checks for reference equality
```java
public boolean equals(Object obj) {
    return (this == obj);
}
```

We can override it by custom implementation, for example:
```java
public class Person {
    private String name;
    private int age;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Person person = (Person) obj;
        return this.age == person.age && this.name.equals(person.name);
    }
}
```

##### hashCode()
The .hashCode() method returns an integer representing the current instance of the class.

The criteria hashCode() must be followed is:
- internal consistency: the value of hashCode() may only change if a property that is in equals() changes
- equals consistency: objects that are equal to each other must return the same hashCode
- collisions: unequal objects may have the same hashCode

The default implementation of hashCode returns a distinct integer for each object
```java
public native int hashCode();
```

We can override it by custom implementation. A typical way to override hashCode is using the Objects.hash utility method, which generates a hash code based on the fields used in equals, for example:
```java
import java.util.Objects;

public class Person {
    private String name;
    private int age;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Person person = (Person) obj;
        return age == person.age && name.equals(person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
```

#### 9. Add employee to HashSet
To ensure that the HashSet recognizes duplicate Employee objects based on their employeeID, we need to override the equals() and hashCode() methods in the Employee class. This way, two Employee objects with the same employeeID will be considered equal, and the HashSet will not allow duplicates.

#### 10. The issue in the code
```java
public static void demo1() {
    List<String> data = new ArrayList<>();
    data.add("Joe");
    data.add("Helen");
    data.add("Test");
    data.add("Rien");
    data.add("Ruby");
    for (String d : data) {
        if (d.equals("Test")) {
            data.remove(d);
        }
    }
}
```
The issue with the provided code is that it attempts to modify the data list while iterating over it using an enhanced for loop. This will cause a ConcurrentModificationException to be thrown because the iterator detects that the list is being structurally modified during iteration. When we use an enhanced for loop (or any iterator) to traverse a list, the iterator expects the list to remain unchanged.

Several ways to fix it:
- Use an Explicit Iterator: This allows you to remove elements safely during iteration.
```java
public static void demo1() {
    List<String> data = new ArrayList<>();
    data.add("Joe");
    data.add("Helen");
    data.add("Test");
    data.add("Rien");
    data.add("Ruby");

    Iterator<String> iterator = data.iterator();
    while (iterator.hasNext()) {
        String d = iterator.next();
        if (d.equals("Test")) {
            iterator.remove();
        }
    }

    System.out.println(data);
}
```
- Use a Different Collection: Create a new collection to store elements to be removed and remove them after the iteration.
```java
public static void demo1() {
    List<String> data = new ArrayList<>();
    ddata.add("Joe");
    data.add("Helen");
    data.add("Test");
    data.add("Rien");
    data.add("Ruby");

    List<String> toRemove = new ArrayList<>();
    for (String d : data) {
        if (d.equals("Test")) {
            toRemove.add(d);
        }
    }
    data.removeAll(toRemove);

    System.out.println(data);
}

```

- Use removeIf Method: Java 8 introduced the removeIf method, which is a cleaner way to remove elements based on a condition.
```java
public static void demo1() {
    List<String> data = new ArrayList<>();
    data.add("Joe");
    data.add("Helen");
    data.add("Test");
    data.add("Rien");
    data.add("Ruby");

    data.removeIf(d -> d.equals("Test"));

    System.out.println(data);
}
```

#### 11. ConcurrencyMomdificationException
If multiple threads access and modify a collection like ArrayList concurrently, without synchronization, the following issues may arise:
- Inconsistent State: One thread may read or write to the collection while another thread is modifying it, leading to an inconsistent or corrupted state.
- ConcurrentModificationException: This exception is specifically thrown by the iterator when it detects that the collection has been modified after the iterator was created, and this modification is not performed through the iterator itself.

Several way to handle this:
- Synchronization: Use synchronized blocks or methods to ensure that only one thread can modify the collection at a time.
- Concurrent Collections: Use thread-safe collections from the java.util.concurrent package, like CopyOnWriteArrayList, ConcurrentHashMap, etc.
- Explicit Locks: Use ReentrantLock or other explicit locking mechanisms to control access to the collection.