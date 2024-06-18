public class Teacher {
    private String name;
    private Integer age;
    private Subject subject;

    public Teacher(String name, Integer age){
        this.name = name;
        this.age = age;
    }

    public Teacher(Subject subject){
        this.subject = subject;
    }

    public Teacher(String name, Integer age, Subject subject){
        this.name = name;
        this.age = age;
        this.subject = subject;
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

    public Subject getSubject(){
        return this.subject;
    }

    public void setSubject(Subject subject){
        this.subject = subject;
    }

    public void isTeaching(){
        System.out.println("Is teaching...");
    }

    public void info(){
        System.out.println("Teacher " + this.name + " teaching " + this.subject.getName() + " for Class " + this.subject.getClassId());
    }
}