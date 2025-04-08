package study.synchronized_features;

/*
    CountDownLatch - несколько потоков параллельно выполняют какое-то количество задач,
                     и необходимо дождаться завершения всех задач, чтобы двигаться дальше.
*/

import java.util.concurrent.CountDownLatch;

public class CountDownLatchStudy {
    static CountDownLatch countDownLatch = new CountDownLatch(3);

    private static void marketStaffIsOnPlace() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("Market staff came to work.");
        countDownLatch.countDown();
        System.out.println("countDownLatch = " + countDownLatch.getCount());
    }

    private static void everythingIsReady() throws InterruptedException {
        Thread.sleep(3000);
        System.out.println("Everything is ready. Let's open the market !!!");
        countDownLatch.countDown();
        System.out.println("countDownLatch = " + countDownLatch.getCount());
    }

    private static void openMarket() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println("Market is opened.");
        countDownLatch.countDown();
        System.out.println("countDownLatch = " + countDownLatch.getCount());
    }

    public static void main(String[] args) throws InterruptedException {

        new Friend("Bob", countDownLatch);
        new Friend("Tim", countDownLatch);
        new Friend("Jack", countDownLatch);
        new Friend("Nat", countDownLatch);
        new Friend("Jane", countDownLatch);

        marketStaffIsOnPlace();
        everythingIsReady();
        openMarket();
    }
}

class Friend extends Thread {
    String name;
    CountDownLatch countDownLatch;

    public Friend(String name, CountDownLatch countDownLatch) {
        this.name = name;
        this.countDownLatch = countDownLatch;

        this.start();
    }

    @Override
    public void run() {
        try {
            countDownLatch.await();  // Точка синхронизации. Ожидаем выполнения всех потоков.
            System.out.println(name + " started shopping.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}