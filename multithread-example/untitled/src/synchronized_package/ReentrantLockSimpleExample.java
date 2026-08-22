package synchronized_package;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockSimpleExample {

    private static Integer count = 0;

    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                incrementCountByReentrantLock(); // Synchronized by the ReentrantLock in method
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                incrementCountByReentrantLock();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("count = " + count);
    }

    private static void incrementCountByReentrantLock() {
        lock.lock();
        count++;
        lock.unlock();
    }
}
