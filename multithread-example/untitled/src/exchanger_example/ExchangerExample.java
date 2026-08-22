package exchanger_example;

import java.util.concurrent.Exchanger;
import java.util.concurrent.atomic.AtomicInteger;

public class ExchangerExample {
    public static void main(String[] args) {

        AtomicInteger count = new AtomicInteger();

        Exchanger<AtomicInteger> exchanger = new Exchanger<>();

        // Change data between Left and Right with help of Exchanger !!!
        Left left = new Left(count, exchanger);
        Right right = new Right(count, exchanger);

        Thread t1 = new Thread(left);
        Thread t2 = new Thread(right);

        t1.start();
        t2.start();
    }
}

class Left implements Runnable {

    private final AtomicInteger count;
    private final Exchanger<AtomicInteger> exchanger;

    public Left(AtomicInteger count, Exchanger<AtomicInteger> exchanger) {
        this.count = count;
        this.exchanger = exchanger;
    }

    @Override
    public void run() {

        while (true) {  // This is for long distance change data
            try {
                AtomicInteger integer = exchanger.exchange(count);
                System.out.println("Left - " + integer.getAndIncrement());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}

class Right implements Runnable {

    private final AtomicInteger count;
    private final Exchanger<AtomicInteger> exchanger;

    public Right(AtomicInteger count, Exchanger<AtomicInteger> exchanger) {
        this.count = count;
        this.exchanger = exchanger;
    }

    @Override
    public void run() {

        while (true) {
            try {
                AtomicInteger integer = exchanger.exchange(count);
                System.out.println("Right - " + integer.getAndIncrement());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}