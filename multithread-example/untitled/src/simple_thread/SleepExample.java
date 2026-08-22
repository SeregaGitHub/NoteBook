package simple_thread;

public class SleepExample {
    public static void main(String[] args) {

        Thread thread = new Thread(() -> {
            System.out.println("thread - start");
            for (int i = 0; i < 5;) {

                try {
                    Thread.sleep(1000);
                    System.out.println(++i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
            System.out.println("thread - finish");
        });

        thread.start();
    }
}
