package volatile_example;

public class VolatileExample {

    // private static boolean running = true;
                                                // Without volatile thread will not see: running = false; !!!
    private static volatile boolean running = true;

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            System.out.println("thread start");
            long x = 0L;
            while (running) {
                for (int i = 0; i < Integer.MAX_VALUE; i++) {
                    x++;
                }
            }
            System.out.println("thread finish");
            System.out.println("X = " + x);
        });

        thread.start();

        Thread.sleep(2000);

        running = false;

        System.out.println("Main finish");
    }
}
