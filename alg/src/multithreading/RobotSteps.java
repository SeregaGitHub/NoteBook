package multithreading;

import java.util.concurrent.Semaphore;

public class RobotSteps {
    public static void main(String[] args) throws InterruptedException {

        var firstSemaphore = new Semaphore(1);   // 1 - чтобы нога №1 пошла первой
        var secondSemaphore = new Semaphore(0);

        var thread1 = new Thread(new Foot(1, firstSemaphore, secondSemaphore));
        var thread2 = new Thread(new Foot(2, secondSemaphore, firstSemaphore));

        thread1.start();
        thread2.start();

        Thread.sleep(10000);

        thread1.interrupt();
        thread2.interrupt();

        thread1.join();
        thread2.join();
    }

    private static class Foot implements Runnable {

        private final int number;
        private final Semaphore current;    // свой семафор
        private final Semaphore next;       // чужой семафор

        public Foot(int number, Semaphore current, Semaphore next) {
            this.number = number;
            this.current = current;
            this.next = next;
        }

        public void makeStep() {
            System.out.println("Make step by leg: " + number);
        }

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    current.acquire();            // 1) ждём свою очередь
                    makeStep();                   // 2) шаг
                    Thread.sleep(1000);     // 3) пауза
                    next.release();               // 4) ход другой ноге
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
