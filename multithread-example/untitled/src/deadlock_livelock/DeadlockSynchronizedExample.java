package deadlock_livelock;

public class DeadlockSynchronizedExample {
    public static void main(String[] args) throws InterruptedException {

        Object lock1 = new Object();
        Object lock2 = new Object();

        Thread thread1 = new Thread(() -> {
            System.out.println("start thread1");

            synchronized (lock1) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (lock2) {
                    System.out.println("synchronized (lock2)");
                }
            }
            System.out.println("finish thread1");
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("start thread2");

            synchronized (lock2) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (lock1) {
                    System.out.println("synchronized (lock1)");
                }
            }
            System.out.println("finish thread2");
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("End DeadlockSynchronizedExample");
    }
}
