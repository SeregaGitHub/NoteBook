package simple_thread;

public class JoinExample {
    public static void main(String[] args) throws InterruptedException {

        MyThreadJoin threadJoin1 = new MyThreadJoin("A", 25);
        MyThreadJoin threadJoin2 = new MyThreadJoin("B", 25);

        threadJoin1.start();
        threadJoin2.start();
                             // join() - block common thread (main)
        threadJoin1.join();  // "Common - Finish " - will be only when - threadJoin1 end

        System.out.println("========== Common - Finish ==========");
    }
}

class MyThreadJoin extends Thread {

    private final String name;
    private final Integer range;

    public MyThreadJoin(String name, Integer range) {
        this.name = name;
        this.range = range;
    }

    @Override
    public void run() {
        int count = 0;
        System.out.println("MyThreadJoin - Start");
        while (count < range) {
            System.out.println(name + " : " + ++count);
        }
        System.out.println("MyThreadJoin - Finish");
    }
}