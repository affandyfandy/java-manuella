import java.util.concurrent.ConcurrentHashMap;

public class Assignment5_7 {

    public static void main(String[] args) throws InterruptedException {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();

        map.put(1, "Melon");
        map.put(2, "Banana");
        map.put(3, "Apple");

        Thread writerThread = new Thread(new Writer(map));
        Thread readerThread1 = new Thread(new Reader(map));
        Thread readerThread2 = new Thread(new Reader(map));

        writerThread.start();
        readerThread1.start();
        readerThread2.start();

        writerThread.join();
        readerThread1.join();
        readerThread2.join();

        System.out.println("Final contents of the map: " + map);
    }

    static class Writer implements Runnable {
        private final ConcurrentHashMap<Integer, String> map;

        Writer(ConcurrentHashMap<Integer, String> map) {
            this.map = map;
        }

        @Override
        public void run() {
            map.put(4, "Grape");
            System.out.println("Writer thread added an entry to the map");
        }
    }

    static class Reader implements Runnable {
        private final ConcurrentHashMap<Integer, String> map;

        Reader(ConcurrentHashMap<Integer, String> map) {
            this.map = map;
        }

        @Override
        public void run() {
            for (Integer key : map.keySet()) {
                String value = map.get(key);
                System.out.println("Reader thread read: " + key + " -> " + value);
            }
        }
    }
}
