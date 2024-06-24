public class Main {
    public static void main(String[] args){
        // DOG CLASS TESTING
        Dog d = new Dog("brown", "Ted", "Golden Retriever");

        d.waggingTail();
        d.barking();
        d.eating("meat");

        // TEACHER CLASS TESTING
        Subject mathSubj = new Subject("Math");
        mathSubj.setClassId(1);
        Subject engSubj = new Subject("English");
        engSubj.setClassId(2);

        Teacher didiTeacher = new Teacher("Didi", 30);
        didiTeacher.setSubject(engSubj);
        Teacher mathTeacher = new Teacher(mathSubj);
        mathTeacher.setName("Anton");
        mathTeacher.setAge(29);
        didiTeacher.info();
        mathTeacher.info();

        Student hanaStudent = new Student("Hana", 15);
        Subject[] subjects = new Subject[2];
        subjects[0] = mathSubj;
        subjects[1] = engSubj;
        hanaStudent.isLearning(subjects);
    }
}
