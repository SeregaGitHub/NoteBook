package simple_thread;

public class RunnableExample {

    public static void main(String[] args) {

        MyRunnable myRunnable1 = new MyRunnable("A", 15);
        MyRunnable myRunnable2 = new MyRunnable("B", 15);

        Thread thread1 = new Thread(myRunnable1);
        Thread thread2 = new Thread(myRunnable2);

        thread1.start();
        thread2.start();
    }
}

class MyRunnable implements Runnable {

    private final String name;
    private final Integer range;

    public MyRunnable(String name, Integer range) {
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