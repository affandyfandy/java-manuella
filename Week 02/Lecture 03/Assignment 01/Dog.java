public class Dog{
    private String color;
    private String name;
    private String breed;
    
    public Dog(String color, String name, String breed){
        this.color = color;
        this.name = name;
        this.breed = breed;
    }

    public String getColor(){
        return this.color;
    }

    public void setColor(String color){
        this.color = color;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getBreed(){
        return this.breed;
    }

    public void setBreed(String breed){
        this.breed = breed;
    }

    public void waggingTail(){
        System.out.println("Wagging tail...");
    }

    public void barking(){
        System.out.println("Barking... Woof Woof!");
    }

    public void eating(String food){
        System.out.println("Eating " + food + ", nom nom...");
    }
}