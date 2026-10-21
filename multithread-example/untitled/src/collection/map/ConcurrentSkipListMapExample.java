package collection.map;

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapExample {

    // аналог TreeMap

    private final ConcurrentNavigableMap<Integer, String> board =
            new ConcurrentSkipListMap<>(java.util.Comparator.reverseOrder());

    public static void main(String[] args) throws InterruptedException {

        ConcurrentSkipListMapExample lb = new ConcurrentSkipListMapExample();

        Runnable task1 = () -> { lb.submit("Alice", 100); lb.submit("Bob", 300); };
        Runnable task2 = () -> { lb.submit("Carol", 200); lb.submit("Dave", 50); };

        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        lb.printTop(3);
        // 1. Bob — 300
        // 2. Carol — 200
        // 3. Alice — 100
    }

    public void submit(String player, int score) {
        board.put(score, player);
    }

    public void printTop(int n) {
        System.out.println("=== TOP " + n + " ===");
        int i = 0;
        for (var entry : board.entrySet()) {
            if (i++ >= n) break;
            System.out.println(i + ". " + entry.getValue() + " — " + entry.getKey());
        }
    }
}
