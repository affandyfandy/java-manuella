public class Subject {
    private Integer classId;
    private String name;

    public Subject(String name){
        this.name = name;
    }

    public Subject(String name, Integer classId){
        this.classId = classId;
        this.name = name;
    }

    public Integer getClassId(){
        return this.classId;
    }

    public void setClassId(Integer classId){
        this.classId = classId;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }
}