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