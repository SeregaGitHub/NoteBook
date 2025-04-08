package study.volatile_and_synchronized;

public class MySynchronized {
    public static void main(String[] args) throws InterruptedException {
        MyRunnable runnable = new MyRunnable();
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        Thread thread3 = new Thread(runnable);
        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();
        System.out.println("Result = " + Counter.count);
    }
}

class Counter {
    static int count = 0;
}

class MyRunnable implements Runnable {
    // public void increment() {            // 3 4 2 6 7 1 8 9 5 Result = 9
    public synchronized void increment() {  // 1 2 3 4 5 6 7 8 9 Result = 9.  Только один поток работает с переменной !!!
        Counter.count++;
        System.out.print(Counter.count + " ");
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            increment();
        }
    }
}
