package study.synchronized_features;

import java.util.concurrent.Semaphore;

/*
    Semaphore - похож на Lock, но, в отличие от последнего,
                позволяет управлять количеством потоков, которые могут одновременно использовать ресурс.
*/

public class SemaphoreStudy {
    public static void main(String[] args) {
        Semaphore callBox = new Semaphore(2);

        new Person("Bob", callBox);
        new Person("Tim", callBox);
        new Person("Jack", callBox);
        new Person("Nat", callBox);
        new Person("Jane", callBox);

    }
}

class Person extends Thread {
    String name;
    Semaphore callBox;

    public Person(String name, Semaphore callBox) {
        this.name = name;
        this.callBox = callBox;

        this.start();
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " is waiting...");
            callBox.acquire();
            System.out.println(name + " is calling...");
            sleep(2000);
            System.out.println(name + " have just finished call.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            callBox.release();
        }
    }
}