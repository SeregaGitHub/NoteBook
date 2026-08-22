package simple_thread;

public class ThreadExample {

    public static void main(String[] args) {

        MyThread myThread1 = new MyThread("A", 15);
        MyThread myThread2 = new MyThread("B", 15);

        myThread1.start();
        myThread2.start();
    }
}

class MyThread extends Thread {

    private final String name;
    private final Integer range;

    public MyThread(String name, Integer range) {
        this.name = name;
        this.range = range;
    }

    @Override
    public void run() {
        int count = 0;
        while (count < range) {
            System.out.println(name + " : " + ++count);
        }
    }
}