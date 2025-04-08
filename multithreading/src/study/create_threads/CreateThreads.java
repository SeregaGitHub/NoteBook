package study.create_threads;

public class CreateThreads {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> System.out.println("Start MultiThreading !!!"));
        thread.start();
        thread.setPriority(Thread.MAX_PRIORITY);

        MyThread1 myThread1 = new MyThread1();
        Thread thread2 = new Thread(new MyThread2());
        myThread1.start();
        thread2.start();
    }
}

class MyThread1 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i <= 1000; i++) {
            System.out.println(i);
        }
    }
}

class MyThread2 implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i <= 1000; i++) {
            System.out.println(i);
        }
    }
}