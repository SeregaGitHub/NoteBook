package collection.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SynchronizedListExample {
    public static void main(String[] args) throws InterruptedException {

        // Если нужен список с интенсивными чтениями и записями

        // List<String> syncList = Collections.synchronizedList(new ArrayList<>());

        List<String> original = new ArrayList<>();
        original.add("A");
        original.add("B");

        List<String> syncList = Collections.synchronizedList(original);

        syncList.add("C");

        System.out.println(syncList); // [A, B, C]
        System.out.println(original);  // [A, B, C] — изменения видны в исходном списке

        ////////////////////////////////////////////////////////////////////////////////
/*
      Отдельные методы add, get, remove синхронизированы, но итерация — нет.
    При итерации нужно вручную синхронизироваться на самом списке
 */
        List<String> list = Collections.synchronizedList(new ArrayList<>());

        list.add("A");
        list.add("B");
        list.add("C");

        Thread writer = new Thread(() -> {
            list.add("D");
            list.add("E");
        });

        Thread reader = new Thread(() -> {
            // Правильная потокобезопасная итерация
            synchronized (list) {
                for (String item : list) {
                    System.out.println(item);
                }
            }
        });

        writer.start();
        reader.start();

        writer.join();
        reader.join();

        System.out.println("Final: " + list);
    }
}
