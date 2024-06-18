import java.util.*;

public class Assignment5_2 {
    public static void main(String[] args) {
        ArrayList<Integer> l = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5));
        int i = 8;

        try{
            int element = l.get(i);
            System.out.println("Element: " + element);
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("Index is out of bounds.");
        }
    }
}
