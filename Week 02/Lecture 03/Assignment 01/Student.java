public class Student{
    private String name;
    private Integer age;

    public Student(String name, Integer age){
        this.name = name;
        this.age = age;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Integer getAge(){
        return this.age;
    }

    public void setAge(Integer age){
        this.age = age;
    }

    public void isLearning(Subject[] subjects){
        System.out.print("Is learning: ");
        for(Subject s: subjects){
            System.out.print(s.getName() + " ");
        }
    }
}