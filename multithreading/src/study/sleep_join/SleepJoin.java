package study.sleep_join;

public class SleepJoin {
    public static void main(String[] args) throws InterruptedException {
        MyThread myThread = new MyThread();
        Thread myRunnable = new Thread(new MyRunnable());
        System.out.println(myThread.getState());
        myThread.start();
        myRunnable.start();
        System.out.println(myThread.getState());

        // myThread.join();           // код ниже будет выполнен только после окончания потока "myThread"
        myRunnable.join(2000);  // код ниже будет выполнен после окончания потока "myRunnable" или через 2 сек.
        System.out.println("END");
    }
}

class MyThread extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            try {
                Thread.sleep(1000);  // задержка потока на 1 секунду
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " : " + i);
        }
    }
}

class MyRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            try {
                Thread.sleep(1000);  // задержка потока на 1 секунду
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " : " + i);
        }
    }
}
