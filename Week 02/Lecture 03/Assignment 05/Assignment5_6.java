import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class Assignment5_6 {

    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

        list.add("Melon");
        list.add("Banana");
        list.add("Apple");

        Thread thread = new Thread(() -> {
            Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                String fruit = iterator.next();
                System.out.println(fruit);

                if (fruit.equals("Banana")) {
                    list.add("Grape");
                }
            }
        });

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final contents of the list: " + list);
    }
}
