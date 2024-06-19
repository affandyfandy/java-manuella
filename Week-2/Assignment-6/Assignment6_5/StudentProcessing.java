import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentProcessing {
    public List<Student> sortedName(List<Student> students){
        return students.stream()
                .sorted(Comparator.comparing(Student::getName))
                .collect(Collectors.toList());
    }

    public Student maxAge(List<Student> students){
        Optional<Student> studentWithMaxAge = students.stream()
                .max(Comparator.comparingInt(Student::getAge));
        return studentWithMaxAge.get();
    }

    public boolean nameContain(String x, List<Student> students){
        return students.stream()
                .anyMatch(student -> student.getName().contains(x));
    }

    public static void main(String[] args) {
        StudentProcessing studentProcessing = new StudentProcessing();
        List<Student> students = new ArrayList<>();
        students.add(new Student("Brian", 20, "A"));
        students.add(new Student("Doe", 22, "B"));
        students.add(new Student("Angel", 21, "C"));
        students.add(new Student("Vera", 19, "D"));

        // SORTED BY NAME
        List<Student> sortedByName = studentProcessing.sortedName(students);
        System.out.println("Students sorted by name:");
        sortedByName.forEach(student -> System.out.println(student.getName()));

        // MAX AGE
        Student maxAgeStudent = studentProcessing.maxAge(students);
        System.out.println("Student with max age: " + maxAgeStudent.getName() + ", age: " + maxAgeStudent.getAge());

        // NAME CONTAIN X
        String keyword = "X";
        boolean anyMatch = studentProcessing.nameContain(keyword, students);

        if (anyMatch) {
            System.out.println("There is a student whose name contains the keyword '" + keyword + "'.");
        } else {
            System.out.println("No student names contain the keyword '" + keyword + "'.");
        }
    }
}
