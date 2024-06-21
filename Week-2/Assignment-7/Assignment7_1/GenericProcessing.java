import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GenericProcessing {
    public static <T, R> List<T> removeDuplicates(List<T> list, Function<? super T, ? extends R> keyExtractor) {
        Set<R> seen = new HashSet<>();
        return list.stream()
                .filter(item -> seen.add(keyExtractor.apply(item)))
                .collect(Collectors.toList());
    }

    public static void printList(List<? extends Object> list) {
        list.forEach(item -> System.out.println(item));
    }

    public static void main(String[] args){
        // Employee example
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Brian", 20, 5000));
        employees.add(new Employee(2, "Doe", 22, 3000));
        employees.add(new Employee(3, "Angel", 21, 4000));
        employees.add(new Employee(4, "Vera", 20, 6000));
        employees.add(new Employee(5, "Brian", 23, 7000)); // Duplicate by name
        employees.add(new Employee(5, "Charles", 23, 7000)); // Duplicate by ID

        // Remove duplicates by name
        List<Employee> uniqueByName = removeDuplicates(employees, Employee::getName);
        System.out.println("Unique employees by name:");
        printList(uniqueByName);

        // Remove duplicates by ID
        List<Employee> uniqueById = removeDuplicates(employees, Employee::getId);
        System.out.println("Unique employees by ID:");
        printList(uniqueById);

        // Product example
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Laptop", 1000.0));
        products.add(new Product(2, "Phone", 500.0));
        products.add(new Product(3, "Tablet", 700.0));
        products.add(new Product(4, "Laptop", 1500.0)); // Duplicate by name
        products.add(new Product(4, "Mouse Pad", 1500.0)); // Duplicate by ID

        // Remove duplicates by name
        List<Product> uniqueProductsByName = removeDuplicates(products, Product::getName);
        System.out.println("Unique products by name:");
        printList(uniqueProductsByName);

        // Remove duplicates by ID
        List<Product> uniqueProductsById = removeDuplicates(products, Product::getId);
        System.out.println("Unique products by ID:");
        printList(uniqueProductsById);
    }
}
