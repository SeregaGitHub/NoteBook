package thread_order;

import java.util.concurrent.Semaphore;

public class SemaphoreOrder {
    public static void main(String[] args) {

        MySemaphore mySemaphore = new MySemaphore();

        Thread t1 = new Thread(mySemaphore::printSecond);
        Thread t2 = new Thread(mySemaphore::printFirst);
        Thread t3 = new Thread(mySemaphore::printThird);

        t1.setName("T1");
        t2.setName("T2");
        t3.setName("T3");

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("End of SemaphoreOrder");
    }
}

class MySemaphore {

    private final Semaphore afterFirst = new Semaphore(0);
    private final Semaphore afterSecond = new Semaphore(0);

    public void printFirst() {
        System.out.println("printFirst");
        afterFirst.release();
    }

    public void printSecond() {
        try {
            afterFirst.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("printSecond");
        afterSecond.release();
    }

    public void printThird() {
        try {
            afterSecond.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("printThird");
    }
}