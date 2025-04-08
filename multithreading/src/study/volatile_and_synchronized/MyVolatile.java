package study.volatile_and_synchronized;

public class MyVolatile extends Thread {
    // boolean b = true;        // без volatile, потоки "thread" и "main" скопировали переменную "b" - у каждого своё значение.
    volatile boolean b = true;  // с volatile, один поток может менять значение "b" для другого потока - у каждого одно значение.

    @Override
    public void run() {
        long count = 0;
        while (b) {
            count++;
        }
        System.out.println("Count = " + count);
    }

    public static void main(String[] args) throws InterruptedException {
        MyVolatile thread = new MyVolatile();
        thread.start();
        Thread.sleep(3000);
        System.out.println("Time to wake up !!!");
        thread.b = false;
        thread.join();
        System.out.println("END");
    }
}
