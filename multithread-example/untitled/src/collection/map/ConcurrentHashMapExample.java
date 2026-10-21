package collection.map;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentHashMapExample {

// Это выбор по умолчанию для подавляющего большинства многопоточных задач с интенсивным чтением и обновлением.

// В отличие от HashMap, ConcurrentHashMap не допускает использования null в качестве ключа или значения !!!

    private final ConcurrentMap<String, Integer> cache = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {

        ConcurrentHashMapExample example = new ConcurrentHashMapExample();

        Runnable task = () -> {
            System.out.println(Thread.currentThread().getName()
                    + " -> " + example.expensiveComputation("hello"));
        };

        // Оба потока запросят "hello" одновременно — вычисление произойдёт один раз
        Thread t1 = new Thread(task, "T1");
        Thread t2 = new Thread(task, "T2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        ///////////////////////////////////////////////////////////////////////////////////////

        ConcurrentMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        // Итерация безопасна: ConcurrentModificationException не будет,
        // даже если другой поток изменит карту.
        for (var entry : map.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
            // можно даже писать в карту во время итерации:
            map.putIfAbsent("d", 4);
        }
    }

    public int expensiveComputation(String key) {
        // computeIfAbsent атомарно: если ключа нет — вычислит и положит,
        // если есть — вернёт существующее значение.
        return cache.computeIfAbsent(key, k -> {
            System.out.println("Computing for " + k + " by " + Thread.currentThread().getName());
            try {
                Thread.sleep(500); // имитация дорогого вычисления
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return k.length();
        });
    }
}
