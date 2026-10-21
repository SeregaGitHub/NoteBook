package collection.list;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListExample {
    public static void main(String[] args) throws InterruptedException {

        // // Если нужен именно список (List) и где много чтений, но мало записей

        List<String> listeners = new CopyOnWriteArrayList<>();

        Runnable reader = () -> {
            for (String listener : listeners) {
                System.out.println("Read: " + listener);
            }
        };

        Runnable writer = () -> {
            listeners.add("listener-1");
            listeners.add("listener-2");
        };

        Thread t1 = new Thread(writer);
        Thread t2 = new Thread(reader);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Final: " + listeners);
    }
}
