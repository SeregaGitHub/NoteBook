package study.volatile_and_synchronized;

public class SynchronizedBlock {
    public static void main(String[] args) throws InterruptedException {
        MyRunnable2 runnable = new MyRunnable2();
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        Thread thread3 = new Thread(runnable);
        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();
        System.out.println("Result = " + Counter2.count);
        System.out.println("Zero = " + MyRunnable2.zero);
    }
}

class Counter2 {
    static int count = 0;
}

class MyRunnable2 implements Runnable {
    static int zero = 0;

    private void someMethod() {
        System.out.print(" ! ");
    }

    public synchronized void doWork() {
        someMethod();            // Эта часть метода doWork() не синхронизирована
        synchronized (this) {    // Эта часть метода doWork() синхронизирована
            Counter2.count++;
            System.out.print(Counter2.count + " ");
        }
        synchronized (MyRunnable2.class) {  // статика синхронизируется через имя класса, а не через this
            zero++;
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            doWork();
        }
    }
}