package collection.map;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class SynchronizedMapExample {
    public static void main(String[] args) throws InterruptedException {

        // простая, но низкопроизводительная обёртка

        Map<String, Integer> map = Collections.synchronizedMap(
                new LinkedHashMap<>()
        );

        Runnable writer = () -> {
            for (int i = 0; i < 5; i++) {
                map.put("key" + i, i);
            }
        };

        Thread t1 = new Thread(writer);
        Thread t2 = new Thread(writer);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        // ВАЖНО: итерация требует ручной синхронизации на самом map!
        synchronized (map) {
            map.forEach((k, v) -> System.out.println(k + " = " + v));
        }
    }
}
